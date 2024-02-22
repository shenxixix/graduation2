<template>
    <div>
        <Dialog
            v-model="visible"
            :title="title"
            
            :padding="0"
            :footer="!isView"
            @confirm="confirm"
            :confirmLoading="confirmLoading">
            <div class="p-6 bg-gray-50">
                <el-form :model="formData" ref="queryRef" label-position="top">
                    <div class="p-4 bg-white rounded leading-10">
                        <el-form-item label="角色名" prop="roleName" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-input v-model="formData.roleName" placeholder="请输入" clearable :readonly="isView" />
                        </el-form-item>
                        <el-form-item label="备注" prop="remarks">
                            <el-input
                                v-model="formData.remarks"
                                :rows="4"
                                type="textarea"
                                placeholder="请输入"
                                :readonly="isView"
                            />
                        </el-form-item>
                        <el-form-item label="权限配置" prop="menuIds" :rules="[ { required: true, validator: menuCheck, trigger: 'blur' } ]">
                            <div class="w-full flex flex-col">
                                <div class="w-full flex items-center">
                                    <el-checkbox v-model="expandAll" @change="doExpandAll($event, treeData)" label="展开/折叠" size="large" />
                                    <el-checkbox v-model="checkedAll" :disabled="isView" @change="doCheckAll" label="全选/全不选" size="large" />
                                </div>
                                <div class="w-full p-4 rounded border border-dashed">
                                    <el-tree
                                        ref="refTree"
                                        :data="treeData"
                                        @check-change="checkChange"
                                        show-checkbox
                                        node-key="permission"
                                        :default-checked-keys="formData.menuIds"
                                    />
                                </div>
                            </div>
                        </el-form-item>
                    </div>
                </el-form>
            </div>
        </Dialog>
    </div>
</template>

<script setup>
import { addOrUpdateRole, findRoleInfo, queryAllMenuList } from '@/api/role';

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
    modelValue: {
        type: Boolean,
        default: false
    },
    keyId: {
        type: [String, Number],
        default: ''
    },
    isView: {
        type: Boolean,
        default: false
    },
});

// 菜单权限
const treeData = ref();
queryAllMenuList().then(res => {
console.log(res,'ressssssss');
    treeData.value = getTree(res.menuList);
});
function getTree(list) {
    return list.map(ele => {
        let children;
        if (ele.list) {
            children = getTree(ele.list);
        }
        return {
            ...ele,
            label: ele.menuName,
            permission: +ele.menuId,
            children,
            disabled: props.isView
        }
    })
}

// 表单
const confirmLoading = ref(false);
const formData = ref({
    roleName: '',
    remarks: '',
    menuIds: [],
});
const title = computed(() => {
    let res;
    if (props.keyId && props.isView) {
        res = '查看角色'
    } else if (props.keyId) {
        res = '修改角色'
    } else {
        res = '添加角色'
    }
    return res;
});
const visible = ref(false);

watch(() => props.modelValue, val => {
    visible.value = val;
    if (props.keyId) {
        getInfo();
    }
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});

// 全选
const checkedAll = ref(false);
function doCheckAll(val) {
    let keyNodes = val ? treeData.value : [];
    proxy.$refs.refTree.setCheckedNodes(keyNodes);
}

// 所有权限值
const allIds = computed(() => getAllIds(treeData.value));
function getAllIds(list, res = []) {
    list.forEach(ele => {
        res.push(+ele.permission);
        if (ele.children) {
            getAllIds(ele.children, res)
        }
    });
    return res;
}

// 节点勾选状态变化时，设置全选/全不选
function checkChange() {
    const checkedKeys = proxy.$refs.refTree.getCheckedKeys();
    checkedAll.value = allIds.value.every(ele => checkedKeys.includes(+ele));
}

// 详情
function getInfo() {
    findRoleInfo(props.keyId).then(res => {
        formData.value = {
            ...res.list,
            roleName: res.roleName,
            remarks: res.remarks,
            menuIds: res.menuIds.map(ele => +ele)
        };
        console.log('formData:', formData.value.menuIds);
    });
}

// 展开折叠
const expandAll = ref(false);
function doExpandAll(expanded, list) {
    list.forEach(item => {
        const node = proxy.$refs.refTree.getNode(item.menuId);
        node.expanded = expanded;
        if (item.children && item.children.length) {
            doExpandAll(expanded, item.children)
        }
    })
}

function menuCheck(rule, value, callback) {
    const menuIds = proxy.$refs.refTree.getCheckedKeys();
    if (!menuIds || !menuIds.length) {
        callback(new Error("不能为空"))
    } else {
        callback();
    }
}

// 提交表单
function confirm() {
    proxy.$refs.queryRef.validate(valid => {
        if (!valid) {
            return;
        }
        const checkedKeys = proxy.$refs.refTree.getCheckedKeys();
        const halfCheckedKeys = proxy.$refs.refTree.getHalfCheckedKeys();
        formData.value.menuIds = checkedKeys.concat(halfCheckedKeys);
        confirmLoading.value = true;
        addOrUpdateRole({...formData.value,roleId:props.keyId}).then(() => {
            proxy.$message.success('操作成功');
            visible.value = false;
            emit('update');
        }).finally(() => {
            confirmLoading.value = false;
        })
    });
}
</script>

<style lang="scss" scoped>
</style>