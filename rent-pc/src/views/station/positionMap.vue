<template>
    <div>
        <Dialog v-model="visible" title="门店地址" :footerShow="false" :closeOnModal="false" :padding="0">
            <div id="map" class="w-full" style="height: calc(90vh - 50px)"></div>
        </Dialog>
    </div>
</template>

<script setup>
import AMapLoader from "@amap/amap-jsapi-loader";
import wgs84togcj02N from '@/utils/wgs';
import positionIcon from '@/assets/images/monitor/icon_dingweizhi@2x.png';

const { proxy } = getCurrentInstance();
const emit = defineEmits();

const props = defineProps({
    modelValue: {
        type: Boolean,
        default: false
    },
    info: {
        type: Object,
        default: () => {
            return {}
        }
    },
});

const stationPics = computed(() => {
    const pics = props.info.stationPics;
    if (pics) {
        return pics.split(',');
    }
    return [];
});

// 地图
let map, AMap, infoWindow;
const infoWindowVisible = ref(false);
onMounted(() => {
    AMapLoader.load({
        key: proxy.amapKey,
        version: "2.0",
        plugins: ["AMap.InfoWindow"]
    }).then(amap => {
        AMap = amap;
        map = new AMap.Map("map", {
            zoom: 12,
        });
        if (!props.info.longitude || !props.info.latitude) return;
        const lnglat = wgs84togcj02N(props.info.longitude, props.info.latitude);
        // 信息窗体
        const content = `<div class="mb-4">${props.info.stationName}</div><div class="flex items-center justify-between"><img style="height: 120px; width: 120px; object-fit: cover;" class="mr-4 rounded" src="${stationPics.value[0]}" /><div class="flex-1"><div>地址：${props.info.location}</div><div class="mt-2">电话：${props.info.phoneNum}</div></div></div>`;
        infoWindow = new AMap.InfoWindow({
            content: content,
            position: lnglat,
            offset: new AMap.Pixel(0, -25),
        });
        infoWindow.on('close', () => {
            infoWindowVisible.value = false;
        });
        // 位置icon
        const icon = new AMap.Icon({
            image: positionIcon,
            size: new AMap.Size(50, 50),
        });
        const marker = new AMap.Marker({
            position: lnglat,
            zIndex: 150,
            offset: new AMap.Pixel(-25, -25),
            icon: icon,
        });
        // 开关信息窗体
        marker.on('click', () => {
            if (infoWindowVisible.value) {
                infoWindow.close();
                infoWindowVisible.value = false;
            } else {
                infoWindow.open(map);
                infoWindowVisible.value = true;
            }
        });
        map.add(marker);
        map.setFitView(null, true);
    });
}),
onBeforeUnmount(() => {
    map.destroy();
});

// 弹窗
const visible = ref(false);
watch(() => props.modelValue, val => {
    visible.value = val;
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});
</script>

<style lang="scss" scoped>
:deep(.amap-marker-label) {
    border: none;
}
:deep(.amap-icon) img {
    width: 100%;
    height: 100%;
}
:deep(.amap-logo) {
    display: none !important;
}
:deep(.amap-copyright) {
    display: none !important;
}
</style>