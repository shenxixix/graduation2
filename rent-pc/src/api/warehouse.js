import request from '@/utils/request'

// 总览
export function storeCount(data) {
    return request({
        url: '/hw-opt-server/station/storeCount',
        method: 'post',
        data: data
    })
}

// 入库记录
export function importOrderList(data) {
    return request({
        url: '/hw-opt-server/carstore/importOrderList',
        method: 'post',
        data: data
    })
}

// 新车仓库明细
export function importList(data) {
    return request({
        url: '/hw-opt-server/carstore/importList',
        method: 'post',
        data: data
    })
}

// 售后仓库-明细列表
export function returnCarList(data) {
    return request({
        url: '/hw-opt-server/carstore/returnCarList',
        method: 'post',
        data: data
    })
}

// 车辆品牌列表
export function carBrandList(data) {
    return request({
        url: '/hw-opt-server/car/carBrandList',
        method: 'post',
        data: data
    })
}

// 车辆型号列表
export function carModelList(data) {
    return request({
        url: '/hw-opt-server/car/carModelList',
        method: 'post',
        data: data
    })
}

// 车辆类型列表
export function carTypeList(data) {
    return request({
        url: '/hw-opt-server/car/carTypeList',
        method: 'post',
        data: data
    })
}

// 华潍仓库删除车
export function delCar(data) {
    return request({
        url: '/hw-opt-server/carstore/delCar',
        method: 'post',
        data: data
    })
}

//新车入库
export function importCars(data) {
    return request({
        url: '/hw-opt-server/station/importCars',
        method: 'post',
        data: data,
        upload: true
    })
}

// 配送站点列表
export function stationList(data) {
    return request({
        url: '/hw-opt-server/carstore/stationList',
        method: 'post',
        data: data
    })
}

// 企业列表
export function queryEnterpriseList(data) {
    return request({
        url: '/hw-opt-server/enterprise/queryEnterpriseList',
        method: 'get',
        params: data
    })
}

// 租赁合约
export function rentOrderList(data) {
    return request({
        url: '/hw-opt-server/carstore/rentOrderList',
        method: 'post',
        data: data
    })
}

// 出库车辆列表
export function queryCarList(data) {
    return request({
        url: '/hw-opt-server/carstore/queryCarList',
        method: 'post',
        data: data
    })
}

//出库
export function outStore(data) {
    return request({
        url: '/hw-opt-server/carstore/outStore',
        method: 'post',
        data: data
    })
}

//出库明细
export function outStoreList(data) {
    return request({
        url: '/hw-opt-server/carstore/outStoreList',
        method: 'post',
        data: data
    })
}

//调剂
export function dispensCars(data) {
    return request({
        url: '/hw-opt-server/carstore/dispensCars',
        method: 'post',
        data: data
    })
}

//退回
export function returnCars(data) {
    return request({
        url: '/hw-opt-server/carstore/returnCars',
        method: 'post',
        data: data
    })
}

//更换设备
export function changeDevice(data) {
    return request({
        url: '/hw-opt-server/carstore/changeDevice',
        method: 'post',
        data: data
    })
}

//车辆明细
export function outStoreCarList(data) {
    return request({
        url: '/hw-opt-server/carstore/outStoreCarList',
        method: 'post',
        data: data
    })
}

//添加OR更新车辆型号
export function addOrUptCarModel(data) {
    return request({
        url: '/hw-opt-server/car/addOrUptCarModel',
        method: 'post',
        data: data
    })
}

//删除车辆型号
export function delCarModel(data) {
    return request({
        url: '/hw-opt-server/car/delCarModel',
        method: 'get',
        params: data
    })
}

//车辆型号详情
export function viewCarModel(data) {
    return request({
        url: '/hw-opt-server/car/viewCarModel',
        method: 'get',
        params: data
    })
}

//添加OR更新车辆品牌
export function addOrUptBrand(data) {
    return request({
        url: '/hw-opt-server/car/addOrUptBrand',
        method: 'post',
        data: data
    })
}

//删除车辆品牌
export function delBrand(data) {
    return request({
        url: '/hw-opt-server/car/delBrand',
        method: 'get',
        params: data
    })
}

//添加OR更新车辆类型
export function addOrUptCarType(data) {
    return request({
        url: '/hw-opt-server/car/addOrUptCarType',
        method: 'post',
        data: data
    })
}

//删除车辆类型
export function delCarType(data) {
    return request({
        url: '/hw-opt-server/car/delCarType',
        method: 'get',
        params: data
    })
}

export function importOrderDetailList(data) {
    return request({
        url: '/hw-opt-server/carstore/importOrderDetailList',
        method: 'post',
        data: data
    })
}

export function outOrderList(data) {
    return request({
        url: '/hw-opt-server/carstore/outOrderList',
        method: 'post',
        data: data
    })
}
export function outOrderDetailList(data) {
    return request({
        url: '/hw-opt-server/carstore/outOrderDetailList',
        method: 'post',
        data: data
    })
}

export function dispensList(data) {
    return request({
        url: '/hw-opt-server/carstore/dispensList',
        method: 'post',
        data: data
    })
}

export function dispensDetailList(data) {
    return request({
        url: '/hw-opt-server/carstore/dispensDetailList',
        method: 'post',
        data: data
    })
}

export function delOrderList(data) {
    return request({
        url: '/hw-opt-server/carstore/delOrderList',
        method: 'post',
        data: data
    })
}

export function delOrderDetailList(data) {
    return request({
        url: '/hw-opt-server/carstore/delOrderDetailList',
        method: 'post',
        data: data
    })
}

export function changeDeviceList(data) {
    return request({
        url: '/hw-opt-server/carstore/changeDeviceList',
        method: 'post',
        data: data
    })
}

export function chgDvcDetailList(data) {
    return request({
        url: '/hw-opt-server/carstore/chgDvcDetailList',
        method: 'post',
        data: data
    })
}
export function returnList(data) {
    return request({
        url: '/hw-opt-server/carstore/returnList',
        method: 'post',
        data: data
    })
}

export function returnDetailList(data) {
    return request({
        url: '/hw-opt-server/carstore/returnDetailList',
        method: 'post',
        data: data
    })
}

export function findCarStatus(data) {
    return request({
        url: '/hw-opt-server/carControl/findCarStatus',
        method: 'get',
        params: data
    })
}


export function vinRecoredList(data) {
    return request({
        url: '/hw-opt-server/carstore/vinRecoredList',
        method: 'post',
        data: data
    })
}

export function findBookOrderInfo(data) {
    return request({
        url: '/hw-opt-server/bookOrder/findBookOrderInfo',
        method: 'get',
        params: data
    })
}

export function statBookOrder(data) {
    return request({
        url: '/hw-opt-server/bookOrder/statBookOrder',
        method: 'get',
        params: data
    })
}

export function queryBookOrderList(data) {
    return request({
        url: '/hw-opt-server/bookOrder/queryBookOrderList',
        method: 'get',
        params: data
    })
}

export function queryRentPlatformList(data) {
    return request({
        url: '/hw-opt-server/bookOrder/queryRentPlatformList',
        method: 'get',
        params: data
    })
}

export function updateStatus(data) {
    return request({
        url: '/hw-opt-server/bookOrder/updateStatus',
        method: 'post',
        data: data
    })
}

export function queryAdventList(data) {
    return request({
        url: '/hw-opt-server/advent/queryAdventList',
        method: 'post',
        data: data
    })
}

export function addOrUptAdvent(data) {
    return request({
        url: '/hw-opt-server/advent/addOrUptAdvent',
        method: 'post',
        data: data
    })
}

export function delAdvent(data) {
    return request({
        url: '/hw-opt-server/advent/delAdvent',
        method: 'get',
        params: data
    })
}

export function viewAdvent(data) {
    return request({
        url: '/hw-opt-server/advent/viewAdvent',
        method: 'get',
        params: data
    })
}

export function remoteControlCar(data) {
    return request({
        url: '/hw-opt-server/carControl/remoteControlCar',
        method: 'get',
        params: data
    })
}

export function queryRentOrderPage(data) {
    return request({
        url: '/hw-opt-server/rent/queryRentOrderPage',
        method: 'get',
        params: data
    })
}

export function orderDetail(data) {
    return request({
        url: '/hw-rent-user/order/orderDetail',
        method: 'get',
        params: data
    })
}

export function simulateRepayList(data) {
    return request({
        url: '/hw-rent-user/order/simulateRepayList',
        method: 'post',
        data: data
    })
}

export function repayPlanList(data) {
    return request({
        url: '/hw-rent-user/order/repayPlanList',
        method: 'post',
        data: data
    })
}

export function acceptList(data) {
    return request({
        url: '/hw-rent-user/order/acceptList',
        method: 'post',
        data: data
    })
}

export function repayRecords(data) {
    return request({
        url: '/hw-rent-user/order/repayRecords',
        method: 'post',
        data: data
    })
}

export function findOrderPdf(data) {
    return request({
        url: '/hw-rent-user/order/findOrderPdf',
        method: 'post',
        data: data
    })
}

export function findOutUploadUrl(data) {
    return request({
        url: '/hw-rent-user/order/findOutUploadUrl',
        method: 'post',
        data: data
    })
}
//还款计划
export function repayTotalSum(data) {
    return request({
        url: '/hw-opt-server/finance/repayTotalSum',
        method: 'get',
        params: data
    })
}

export function totalList(data) {
    return request({
        url: '/hw-opt-server/finance/totalList',
        method: 'post',
        data: data
    })
}

export function replayList(data) {
    return request({
        url: '/hw-opt-server/finance/replayList',
        method: 'post',
        data: data
    })
}

export function outOrderCarList(data) {
    return request({
        url: '/hw-opt-server/carstore/outOrderCarList',
        method: 'post',
        data: data
    })
}

export function checkDetail(data) {
    return request({
        url: '/hw-rent-user/order/checkDetail',
        method: 'post',
        data: data
    })
}
//生产厂家
export function queryFactoryPage(data) {
    return request({
        url: '/hw-opt-server/carFactory/queryFactoryPage',
        method: 'get',
        params: data
    })
}

export function addOrUpdateFactory(data) {
    return request({
        url: '/hw-opt-server/carFactory/addOrUpdateFactory',
        method: 'post',
        data: data
    })
}

export function deleteFactory(data) {
    return request({
        url: '/hw-opt-server/carFactory/deleteFactory',
        method: 'post',
        data: data
    })
}

export function findFactoryInfo(data) {
    return request({
        url: '/hw-opt-server/carFactory/findFactoryInfo',
        method: 'get',
        params: data
    })
}

export function queryFactoryList(data) {
    return request({
        url: '/hw-opt-server/carFactory/queryFactoryList',
        method: 'get',
        params: data
    })
}

export function queryBookOrderCars(data) {
    return request({
        url: '/hw-opt-server/bookOrder/queryBookOrderCars',
        method: 'get',
        params: data
    })
}
//领用清单
export function queryReceiveCarList(data) {
    return request({
        url: '/hw-car-business/receiveCar/queryReceiveCarList',
        method: 'get',
        params: data
    })
}

export function unbindCar(data) {
    return request({
        url: '/hw-car-business/receiveCar/unbindCar',
        method: 'post',
        data: data
    })
}