<template>
    <el-dialog
        ref="dialogs"
        v-model="dialogVisible"
        :width="dialogWidth"
        :modal="props.modal"
        :style="{ height: dialogHeight }"
        top="0"
        :append-to-body="props.appendToBody"
        :close-on-click-modal="props.closeOnModal"
        :close-on-press-escape="props.closeOnEsc"
        class="dialogs"
        :show-close="false">
        <template #header>
            <!-- 标题 -->
            <div class="dialogs-title">{{props.title}}</div>
            <!-- 关闭按钮 -->
            <div class="dialogs-close" v-if="props.closeShow" @click="close">
                <img :src="closeIcon" alt="">
            </div>
        </template>
        <template v-if="!props.destroyOnClose || (props.destroyOnClose && dialogVisible)">
            <!-- 默认插槽 -->
            <div class="dialogs-contant" :style="`padding: ${props.padding}px;height:100%;`">
                <slot></slot>
            </div>
        </template>
        <template #footer>
            <!-- footer插槽 -->
            <div class="dialogs-footer" v-if="proxy.$slots.footer">
                <slot name="footer"></slot>
            </div>
            <div class="dialogs-footer" v-else-if="props.footerShow">
                <el-button @click="close" v-if="props.cancelShow">{{props.cancelText}}</el-button>
                <el-button type="primary" :loading="props.confirmLoading" @click="confirm" v-if="props.confirmShow">{{props.confirmText}}</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import closeIcon from '@/assets/images/icon_close1@2x.png';

const props = defineProps({
    // 弹窗开关
    modelValue: {
        type: Boolean,
        default: false
    },
    // 内边距
    padding: {
        type: [Number,String],
        default: 30
    },
    // 是否显示关闭按钮
    closeShow: {
        type: Boolean,
        default: true
    },
    // 是否显示底部栏
    footerShow: {
        type: Boolean,
        default: true
    },
    // 是否显示确认按钮
    confirmShow: {
        type: Boolean,
        default: true
    },
    // 确认按钮loading
    confirmLoading: {
        type: Boolean,
        default: false
    },
    // 确认按钮文字
    confirmText: {
        type: String,
        default: '确定'
    },
    // 是否显示取消按钮
    cancelShow: {
        type: Boolean,
        default: true
    },
    // 取消按钮文字
    cancelText: {
        type: String,
        default: '取消'
    },
    // 弹窗标题
    title: {
        type: String,
        default: '标题'
    },
    // 弹窗大小: small, middle, big，也支持固定px和百分比
    width: {
        type: String,
        default: 'middle'
    },
    // 弹窗高度
    height: {
        type: String,
        default: 'auto'
    },
    // 是否需要遮罩层
    modal: {
        type: Boolean,
        default: true
    },
    // Dialog 自身是否插入至 body 元素上，嵌套dialog需要设置为true
    appendToBody: {
        type: Boolean,
        default: false
    },
    // 是否可以通过点击 modal 关闭 Dialog
    closeOnModal: {
        type: Boolean,
        default: true
    },
    // 是否可以通过按下 ESC 关闭 Dialog
    closeOnEsc: {
        type: Boolean,
        default: true
    },
    // 关闭时是否销毁Dialog中的元素
    destroyOnClose: {
        type: Boolean,
        default: false
    },
});

const { proxy } = getCurrentInstance();
const emit = defineEmits();

const dialogVisible = ref(false);

// 弹窗大小
const dialogWidth = ref('');
switch(props.width) {
    case 'small':
        dialogWidth.value = '320px';
        break;
    case 'middle':
        dialogWidth.value = '750px';
        break;
    case 'big':
        dialogWidth.value = '1000px';
        break;
    default:
        dialogWidth.value = props.width || '750px';
        break;
}

// 弹窗高度
const dialogHeight = ref('');
switch(props.height) {
    case 'max':
        dialogHeight.value = '90vh';
        break;
    default:
        dialogHeight.value = props.height || 'auto';
        break;
}

watch(() => props.modelValue, val => {
    dialogVisible.value = val;
},{ immediate: true });
watch(() => dialogVisible.value, val => {
    emit('update:modelValue', val);
});

function confirm() {
    emit('confirm');
}
function close() {
    emit('close');
    dialogVisible.value = false;
}
</script>


<style lang="scss">
.dialogs {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
    margin: 0;
    border-radius: 4px;
    box-sizing: border-box;
    max-height: 90vh;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    .el-dialog__header {
        margin: 0;
        padding: 0;
    }
    .el-dialog__footer {
        padding: 0;
    }
    .el-dialog__body {
        padding: 0;
        box-sizing: border-box;
        flex: 1;
        overflow-y: auto;
    }
}
</style>
<style lang="scss" scoped>
.dialogs-footer {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding: 0 30px;
    height: 60px;
    box-sizing: border-box;
    background-color: #fff;
    width: 100%;
    :deep(.el-button) {
        min-width: 86px;
    }
}
.dialogs-title {
    color: #333;
    font-weight: bold;
    font-size: 16px;
    display: flex;
    align-items: center;
    padding: 0 30px;
    height: 50px;
    box-sizing: border-box;
    border-bottom: 1px solid #E4E4E4;
}
.dialogs-close {
    width: 14px;
    height: 14px;
    cursor: pointer;
    position: absolute;
    right: 30px;
    top: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 2;
    &:hover {
        opacity: .8;
    }
    img {
        width: 100%;
        height: 100%;
    }
}
</style>
