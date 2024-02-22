<template>
	<div>
		<div @click="triggerClick">
			<el-button v-if="!$slots.default" type="primary">{{ selected.name || '选择地址' }}</el-button>
			<slot></slot>
		</div>
		<Dialog v-model="visible" v-if="visible" title="选择位置点" :footerShow="false" height="max" @close="close">
			<div class="address-box" v-loading="loading">
				<div class="location" @click="goselfLocation">
					<img :src="location" alt srcset />
				</div>
				<div class="select-box">
					<el-input v-model="searchValue" size="large" @input="querySearch" placeholder="搜索其他地点"
						prefix-icon="el-icon-search" clearable></el-input>
					<div class="map-search-content" v-show="searchValue">
						<p v-if="search.tips.length == 0" class="noAddress">未找到匹配的地址</p>
						<div class="search-item flex-center-side" v-for="(item, index) in search.tips" :key="index">
							<div @click="querySearched(index, item)">
								<p class="address over-omit">{{ item.name }}</p>
								<p class="detailed-address over-omit">{{ item.address }}</p>
							</div>
							<el-button type="primary" size="mini" @click="querySearched(index, item, true)">选择</el-button>
							<!-- <div class="search-button selected" @click="querySearched(index, item, true)">选择</div> -->
						</div>
					</div>
				</div>
				<!-- 地图 -->
				<div class="map-container">
					<div ref="refMap" style="width:100%;height: 100%"></div>
				</div>
				<!-- 底部信息栏 -->
				<div class="map-select-container" v-if="selected.name">
					<div class="map-select">
						<p class="address over-omit">{{ selected.name }}</p>
						<p class="detailed-address over-omit">{{ selected.address }}</p>
					</div>
					<el-button type="primary" size="small" class="select-button" @click="bottomSelected">选择</el-button>
				</div>
			</div>
		</Dialog>
	</div>
</template>

<script setup>
import { ref, nextTick } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import positionIcon from "@/assets/images/icon_dingweizhi@2x.png";
import dinwei from '@/assets/images/dinwei.png';
import dingweisel from '@/assets/images/dinweisel.png';


const { proxy } = getCurrentInstance();
const emit = defineEmits();
let location = dinwei;
const geocoder = ref({})
const loading = ref(false)
const checkAll = ref(false)
const searchValue = ref('')
const visible = ref(false);
const map = ref(null)
const mark = ref(null)
let AMap;
const search = ref({
	city: null,
	txt: '',
	tips: [],
	selIndex: null,
})
const mapPlug = ref({
	mapPlaceSearch: null,
	mark: null,
})
const selected = ref({
	name: '',
	address: '',
	lng: '',
	lat: '',
})
const lngLat = ref(null)

const props = defineProps({
	modelValue: {
		type: Object,
		default: {},
	},
	address: {
		type: Object,
		default: {},
	},
});


function triggerClick() {
	visible.value = true
	loading.value = false;
	clearData();
	searchValue.value = null;
	if (props.modelValue && props.modelValue.address) {
		selected.value = JSON.parse(JSON.stringify(props.modelValue))
	}
	nextTick(() => {
		initMap();
	});
}
/**
 * 搜索
 */
function querySearch(keyword) {
	search.value.selIndex = null;
	mapPlug.value.mapPlaceSearch.search(keyword, (status, result) => {
		if (status === 'complete') {
			search.value.tips = result.tips.map(ele => {
				return {
					...ele,
					address:
						typeof ele.address === 'string'
							? ele.address
							: '',
				};
			});
		} else {
			search.value.tips = [];
		}
	});
}
/**
 * 选择搜索地址
 */
function querySearched(index, item, flag) {
	location = dinwei;
	search.value.selIndex = index;
	selected.value.name = item.name;
	selected.value.address = item.address;
	selected.value.lng = item.location.lng;
	selected.value.lat = item.location.lat;
	mapPlug.value.mark.setPosition(item.location);
	mapPlug.value.mark.setMap(map.value);
	map.setCenter(item.location);
	if (flag) {
		const clone = JSON.parse(JSON.stringify(selected.value));
		emit('confirm', clone);
		visible.value = false;
		map.value.destroy();
	}
}
/**
 * 地图下方地址点击选择
 */
function bottomSelected() {
	const clone = JSON.parse(JSON.stringify(selected.value));
	emit('confirm', clone);
	location = dinwei;
	visible.value = false
	map.value.destroy();
}
/**
 * 数据处理
 */
function clearData() {
	search.value = {
		city: null,
		txt: '',
		tips: [],
		selIndex: null,
	};
	location = dinwei;
}
function close() {
	clearData();
	map.value.destroy();
	searchValue.value = null;
	visible.value
}
/**
 * 定位到自己的位置
 */
function goselfLocation() {
	location = dingweisel;
	const lngLat = [lngLat.value.lng, lngLat.value.lat];
	map.value.setCenter(lngLat.value, true);
	mark.value.setPosition(lngLat.value, true);
	getAddress(lngLat.value);
}
/**
 * 获取位置信息-传入经纬度
 */
function getAddress(lngLat) {
	geocoder.value.getAddress(
		[lngLat.lng, lngLat.lat],
		(status, result) => {
			if (status === 'complete' && result.regeocode) {
				const address = result.regeocode.addressComponent;
				if (address.building) {
					selected.value.name = address.building;
				} else {
					selected.value.name =
						address.township +
						address.street +
						address.streetNumber;
				}
				selected.value.address =
					result.regeocode.formattedAddress;
				selected.value.lng = lngLat.lng;
				selected.value.lat = lngLat.lat;
			}
		}
	);
}
function initMap() {
  console.log(selected.value, 789);
  AMapLoader.load({
    key: proxy.amapKey,
    version: "2.0",
    plugins: ["AMap.Geolocation", "AMap.CitySearch", "AMap.PlaceSearch", "AMap.ToolBar", "AMap.Geocoder", "AMap.Geolocation", "AMap.DistrictSearch"],
  }).then((amap) => {
    AMap = amap;
    map = new AMap.Map("map", {
      zoom: 10,
    });
    const icon = new AMap.Icon({
      size: new AMap.Size(36, 40),
      image: positionIcon,
      imageSize: new AMap.Size(36, 40),
      imageOffset: new AMap.Pixel(0, 0),
    });
    marker = new AMap.Marker({
      icon,
      offset: new AMap.Pixel(-18, -36),
      draggable: true,
    });
    //map.add(marker);
    mapPlug.value.mapPlaceSearch = new AMap.PlaceSearch({ city: "500100" });
    marker.on("dragend", (e) => {
      getAddress(e.lnglat);
      console.log(e, 191919);
    });
    map.on("complete", () => {
      console.log(selected.value,8888888)
      if (selected.value.address) {
        const point = new AMap.LngLat(selected.value.point[0], selected.value.point[1]);
        console.log(point, 156);
        map.setZoom(16);
        map.setCenter(point);
        map.add(marker);
        marker.setPosition(point);
      } else {
        let ll;
        let citySearch = new AMap.CitySearch();
        citySearch.getLocalCity((status, result) => {
          console.log(status, result, 88);
          if (status === "complete" && result.info === "OK") {
            // this.search.city = result.adcode;
            ll = result.bounds.getCenter();
            lngLat.value = [ll.lng, ll.lat];
            console.log(lngLat, 111);
            map.setCenter(lngLat.value);

            map.add(marker);
            marker.setPosition(lngLat.value);
          }
        });
      }
    });
    // marker.setMap(this.map);
    mapPlug.value.mark = marker;
    //map.setFitView(null, true);
  });
}

// function initMap() {
// 	AMapLoader.load({
// 		key: proxy.amapKey,
// 		version: "2.0",
// 		plugins: ['AMap.CitySearch', 'AMap.AutoComplete', 'AMap.Geocoder', 'AMap.Geolocation'],
// 	}).then((amap) => {
// 		AMap = amap;
// 		// marker
// 		const icon = new AMap.Icon({
// 			size: new AMap.Size(36, 40),
// 			image: positionIcon,
// 			imageSize: new AMap.Size(36, 40),
// 			imageOffset: new AMap.Pixel(0, 0),
// 		});
// 		mark.value = new AMap.Marker({
// 			icon,
// 			offset: new AMap.Pixel(-18, -36),
// 			draggable: true,
// 		});
// 		mark.value.on('dragend', e => {
// 			// 拖拽
// 			getAddress(e.lnglat);
// 		});
// 		// 搜索
// 		mapPlug.value.mapPlaceSearch = new AMap.AutoComplete({});
// 		// 地址
// 		geocoder.value = new AMap.Geocoder();
// 		// 定位到当前城市
// 		const citySearch = new AMap.CitySearch();
// 		citySearch.getLocalCity((status, result) => {
// 			console.log('getLocalCity:', status, result);
// 			if (status === 'complete' && result.info === 'OK') {
// 				search.value.city = result.adcode;
// 				lngLat.value = result.bounds.getCenter();
// 				if (visible.value && (!selected.value.lng || !selected.value.lat)) {
// 					// 弹窗已打开，重置定位
// 					goselfLocation();
// 				}
// 			}
// 		});
// 		// 定位到当前位置
// 		const geolocation = new AMap.Geolocation({
// 			enableHighAccuracy: true,
// 			timeout: 10000,
// 		});
// 		geolocation.getCurrentPosition((status, res) => {
// 			// 浏览器定位不稳定且很慢，可能会失败
// 			console.log('getCurrentPosition:', status, res);
// 			if (status === 'complete') {
// 				lngLat.value = res.position;
// 				if (visible.value && (!selected.value.lng || !selected.value.lat)) {
// 					// 弹窗已打开，重置定位
// 					goselfLocation();
// 				}
// 			}
// 		});
// 		// 地图定位
// 		setMap();
// 	});
// }

function setMap() {
	// 地图
	const mapdom = proxy.$refs.refMap;
	map.value = new AMap.Map(mapdom, {
		zoom: 14,
	});
	map.value.on('complete', () => {
		if (selected.value.lng && selected.value.lat) {
			// 如果传入了位置，地图定位到该位置
			console.log(12345)
			const lnglat = [selected.value.lng, selected.value.lat];
			location = dingweisel;
			map.value.setCenter(lnglat, true);
			mark.value.setPosition(lnglat, true);
		} else if (lngLat.value) {
			// 否则定位到当前位置
			goselfLocation();
		}
		mark.value.setMap(map.value);
		mapPlug.value.mark = mark.value;
	});
}


</script>

<style lang='scss' scoped>
.location {
	width: 50px;
	height: 50px;
	position: absolute;
	top: 20px;
	right: 20px;
	z-index: 30;
	cursor: pointer;

	img {
		width: 50px;
		height: 50px;
	}
}

.address-box {
	width: 100%;
	height: 100%;
	box-sizing: border-box;
	font-size: 14px;
	position: relative;

	.select-box {
		position: absolute;
		top: 20px;
		left: 50%;
		width: 400px;
		transform: translate(-50%);
		z-index: 20;

		::v-deep .el-input {
			width: 100%;
		}

		::v-deep .el-input__suffix-inner {
			line-height: 40px;
			color: #3642ff;
			padding-right: 15px;
		}

		::v-deep .el-input--suffix .el-input__inner {
			padding-right: 50px;
		}

		.map-search-content {
			width: 400px;
			max-height: 262px;
			margin-top: 10px;
			background: #ffffff;
			box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.12);
			border-radius: 4px;
			overflow-y: auto;
			cursor: pointer;

			.noAddress {
				color: #888888;
				line-height: 120px;
				text-align: center;
			}

			.no-search {
				padding-top: 20px;
				width: 100%;
				text-align: center;
				color: #3642ff;
			}

			.search-item {
				width: 100%;
				height: 74px;
				box-sizing: border-box;
				padding: 15px 20px;

				&:hover {
					opacity: 0.8;
					background: #fafafa;
					// color: #3642ff;
				}
			}

			// .search-item:hover .address {
			//   color: #3642ff;
			// }
		}
	}

	.map-container {
		width: 100%;
		height: 100%;
		position: relative;
	}

	.map-select-container {
		position: absolute;
		bottom: 30px;
		left: 50%;
		transform: translate(-50%);
		width: 518px;
		height: 88px;
		background: #ffffff;
		box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.06);
		border-radius: 4px;
		filter: blur(0px);
		box-sizing: border-box;
		padding: 20px;
		background-color: #ffffff;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.map-select {
			// width: 328px;
			flex: 1;
			overflow: hidden;
		}
	}
}

p {
	margin: 0;
	padding: 0;
}

.address {
	width: 100%;
	height: 20px;
	line-height: 20px;
	font-size: 14px;
	font-weight: 600;
	color: #333333;

	margin-bottom: 4px;
}

.detailed-address {
	width: 100%;
	height: 20px;
	line-height: 20px;
	font-size: 14px;
	font-weight: 400;
	color: #888888;
}

.search-button {
	width: 70px;
	height: 34px;
	background: #3642ff;
	border-radius: 4px;
	filter: blur(0px);
	line-height: 34px;
	text-align: center;
	color: #fff;
}

// 超出一行显示省略号
.over-omit {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.crumb-item {
	color: #333;
	cursor: pointer;
}

.del-icon {
	width: 20px;
	height: 20px;
	cursor: pointer;

	&:hover {
		opacity: 0.8;
	}

	img {
		width: 100%;
		height: 100%;
		object-fit: cover;
	}
}

// flex布局
.flex {
	display: flex;
}

.flex-wrap {
	flex-wrap: wrap;
}

.flex-column {
	@extend .flex;
	flex-direction: column;
}

.flex-column-center {
	@extend .flex-column;
	align-items: center;
}

.flex-center {
	@extend .flex;
	align-items: center;
}

.flex-stretch {
	@extend .flex;
	align-items: stretch;
}

.flex-line {
	@extend .flex;
	align-items: baseline;
}

.flex-center-center {
	@extend .flex-center;
	justify-content: center;
}

.flex-center-side {
	@extend .flex-center;
	justify-content: space-between;
}

.flex-stretch-center {
	@extend .flex-stretch;
	justify-content: center;
}

.flex-stretch-side {
	@extend .flex-stretch;
	justify-content: space-between;
}

::v-deep .el-input__validateIcon {
	display: none;
}

:deep(.amap-marker-label) {
	border: none;
	padding: 0;
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
