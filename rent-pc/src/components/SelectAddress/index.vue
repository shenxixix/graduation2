<template>
  <div class="sel-car">
    <div @click="triggerClick">
      <el-button v-if="!$slots.default" size="small" type="primary">选择位置点</el-button>
      <slot></slot>
    </div>
    <Dialog v-model="visible" v-if="visible" title="选择位置点" height="max" :footerShow="false" :padding="0" @close="close">
      <div class="con">
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
          <div id="map" style="width:100%;height: 100%"></div>
        </div>
        <!-- 底部信息栏 -->
        <div class="map-select-container" v-if="selected.name">
          <div class="map-select">
            <p class="address over-omit">{{ selected.name }}</p>
            <p class="detailed-address over-omit">{{ selected.address }}</p>
          </div>
          <el-button type="primary" size="small" class="select-button" @click="bottomSelected">选择</el-button>
        </div>
        <!-- <div class="content flex" style="height: 100%">
            <div class="mapcon">
              <div id="map" class="map"></div>
              <div class="map-select-container" v-if="selected.address">
                <div class="map-select">
                  <span class="item">{{ selected.name }}</span>
                  <span class="item mt-2">{{ selected.address }}</span>
                </div>
                <el-button type="primary" size="small" @click="bottomSelected">选择</el-button>
              </div>
            </div>
          </div> -->
      </div>
    </Dialog>
  </div>
</template>
<script setup>
import { ref, nextTick } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import { provinceAndCityData, CodeToText } from "element-china-area-data";
import positionIcon from "@/assets/images/icon_dingweizhi@2x.png";

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const queryParams = ref({
  cityCode: "",
});
const visible = ref(false);
const data = ref("");
const lngLat = ref([]);
const searchValue = ref('');
const hisList = ref([]);
const search = ref({
  city: null,
  txt: "",
  tips: [],
  selIndex: null,
});

const activeName = ref("first");

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
  console.log(props.address, 111);
  visible.value = true;
  clearData();
  selected.value = props.modelValue;
  nextTick(() => {
    initMap();
  });
  if (!JSON.parse(localStorage.getItem("hisSearch"))) {
    localStorage.setItem("hisSearch", JSON.stringify([]));
  } else {
    hisList.value = JSON.parse(localStorage.getItem("hisSearch"));
  }
}
function clearData() {
  search.value = {
    city: null,
    txt: "",
    tips: [],
    selIndex: null,
  };
  selected.value = {
    name: "",
    address: "",
    lat: '',
    lng: ''
  };
}
let map, AMap, marker;
const mapPlug = ref({});
const selected = ref({});
const name = ref("");
const nowAddress = ref("");
// 选择城市
const separator = "-";
const cityCode = ref("");
const cascaderProps = {
  expandTrigger: "hover",
};
const city = ref(["110000", "120000", "310000", "500000"]);
function cascaderChange(val) {
  selected.value = {
    name: "",
    address: "",
    lat: '',
    lng: ''
  };
  map.setZoom(10);
  if (!val || !val.length) return;
  if (city.value.includes(val[0])) {
    queryParams.value.cityCode = val[0];
  } else {
    queryParams.value.cityCode = val[1];
  }
  console.log(queryParams.value, 444);
  queryParams.value.cityName = `${CodeToText[val[0]]}${separator}${CodeToText[val[1]]}`;
  console.log(queryParams.value, 555);
  new AMap.DistrictSearch().search(queryParams.value.cityCode, function (status, result) {
    if (status === "complete") {
      console.log(result, 666);
      //getData(result.districtList[0],obj.id);
      lngLat.value = [result.districtList[0].center.lng, result.districtList[0].center.lat];
      map.setCenter(lngLat.value);
      marker.setPosition(lngLat.value);
    }
  });
}

function getAddress(lnglat) {
  new AMap.Geocoder({}).getAddress(lnglat, (status, result) => {
    if (status === "complete" && result.regeocode) {
      let address = result.regeocode.addressComponent;
      if (address.building !== "") {
        selected.value.name = address.building;
        // name.value = address.building;
      } else {
        selected.value.name = address.township + address.street + address.streetNumber;
      }
      selected.value.address = result.regeocode.formattedAddress;
      selected.value.lng = lnglat.lng
      selected.value.lat = lnglat.lat
    }
  });
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
      console.log(selected.value, 8888888)
      if (selected.value.address) {
        console.log(11)
        const point = new AMap.LngLat(selected.value.lng, selected.value.lat);
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

function querySearch(keyword) {
	search.value.selIndex = null;
	new AMap.PlaceSearch().search(keyword, (status, result) => {
		if (result.poiList && result.poiList.pois && result.poiList.pois.length > 0) {
      search.value.tips = result.poiList.pois;
    } else {
      search.value.tips = [];
    }
	});
}

/**
 * 选择搜索地址
 */
function querySearched(index, item,flag) {
  search.value.selIndex = index;
  selected.value.name = item.name;
  selected.value.address = item.address;
  selected.value.lng = item.location.lng
  selected.value.lat = item.location.lat
  marker.setPosition(item.location);
  map.setCenter(item.location);
  searchValue.value = ''
  if (flag) {
		const clone = JSON.parse(JSON.stringify(selected.value));
		emit("select", selected.value);
    emit("update:modelValue", selected.value);
		visible.value = false;
		map.destroy();
	}
}


function bottomSelected() {
  emit("select", selected.value);
  emit("update:modelValue", selected.value);
  map.destroy();
  visible.value = false;
}

function clearHis() {
  localStorage.setItem("hisSearch", JSON.stringify([]));
  hisList.value = [];
}
function close() {
  console.log(111);
  map.destroy();
}
</script>

<style lang="scss" scoped>
.map-container {
  width: 100%;
  height: 100%;
  position: relative;
}

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

.sel-car {
  width: 100%;
}

.con {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  font-size: 14px;
  position: relative;
}

:deep(.el-form) {
  display: flex;
  justify-content: flex-end;
}

:deep(.el-form-item) {
  margin-right: 10px;
}

.label {
  width: 100px;
}

.content {
  width: 100%;
  display: flex;

  .his-list {
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow-y: auto;
  }

  .his-item {
    font-size: 14px;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 10px 20px 10px;
    border-bottom: 1px solid #eee;
  }

  .his-item-left {
    width: 100%;
    display: flex;
    align-items: center;
  }

  .address {
    display: flex;
    flex-direction: column;
    margin-left: 10px;
    margin-right: 10px;

    .address-item {
      line-height: 22px;
    }
  }
}

:deep(.is-active) {
  background: none !important;
  color: #409eff !important;
}

.mapcon {
  width: 100%;
  height: 400px;
  position: relative;
}

.map {
  height: 400px;
}

.mt-4 {
  margin-top: 20px;
}

.map-search-content {
  position: absolute;
  z-index: 1000;
  right: 0;
  width: 400px;
  height: 262px;
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
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #eee;

    &:hover {
      opacity: 0.8;
      background: #fafafa;
      color: #3642ff;
    }

    .search-left {
      display: flex;
      flex-direction: column;
      width: 80%;

      span {
        line-height: 22px;
      }
    }
  }

  .search-item:hover .address {
    color: #3642ff;
  }
}

.map-select-container {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  width: 368px;
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
    display: flex;
    flex-direction: column;
    width: 80%;
    .item {
      line-height: 14px;
    }
  }
}

p {
  margin: 0;
  padding: 0;
}

.over-omit {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
