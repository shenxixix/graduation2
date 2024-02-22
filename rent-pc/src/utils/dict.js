import useDictStore from '@/store/modules/dict'

/**
 * 获取字典
 *
 * 只传入key，将得到索引对应的字典数组；若key是数组，将返回字典数组对象，这在批量获取字典数据时会很方便，比如select选项组
 * 传入value时，将得到value对应的label
 *
 * @param {Array,String} key 字典索引
 * @param {Number,String} value 字典值
 * @returns {Object,Array,String}
 */
export function dict(key, value) {
  // key是数组，返回字典数组对象
  if (Array.isArray(key)) {
    const res = ref({});
    key.forEach((dictType) => {
      res.value[dictType] = useDictStore().getDict(dictType);
    });
    return toRefs(res.value);
  }
  // key是字符串
  const dict = useDictStore().getDict(key);
  if (value === undefined) {
    // 没有value，返回key对应的字典数组
    return dict;
  } else {
    // 有value值，返回对应的label
    const act = dict.find((ele) => +ele.value === +value);
    if (act) {
      return act.label;
    }
    return '';
  }
}

/**
 * 字典数据
 * 
 * `label`,`value`必填
 * todo：可能命名空间冲突，后期使用接口返回的数据
 * 
 * @param {String} label 名称
 * @param {String,Number} value 值
 * @param {String} type 类型 可选值`primary / success / warning / danger / info`
 * @param {String} effect 主题 可选值`dark / light / plain`
 * @param {Boolean} round 圆角
 * @param {String} size 尺寸 可选值`medium / small / mini`
 * @returns {Object}
 */

export const dictData = {
  // 客户类型
  enterpriseType: [
    { label: "配送站点", value: 1 },
    { label: "租车商户", value: 2 },
    { label: "合作服务商", value: 3 },
  ],
  // 企业状态
  businessStatus: [
    { label: "正常", value: 1, type: "success" },
    { label: "暂停合作", value: 2, type: "danger" },
  ],
  // 性别
  userSex: [
    { label: "男", value: 1 },
    { label: "女", value: 2 },
  ],
  // 账号状态
  userAccountStatus: [
    { label: "正常", value: 1, type: "success" },
    { label: "已暂停", value: 0, type: "info" },
  ],
  // 用户类型
  userType: [
    { label: "骑手用户", value: 1 },
    { label: "站点用户", value: 2 },
  ],
  // 用户状态
  userStatus: [
    { label: "正常", value: 0, type: "success" },
    { label: "已注销", value: 1, type: "info" },
  ],
  // 实名状态
  realNameStatus: [
    { label: "未认证", value: 0, type: "info" },
    { label: "已认证", value: 1, type: "success" },
  ],
  // 配送站点情况
  stationRel: [
    {
      label: "已关联配送站点",
      value: 1,
      type: "",
      elTagClass: "text-blue-700 cursor-pointer hover:text-opacity-75",
    },
    {
      label: "未关联配送站点",
      value: 2,
      type: "",
      elTagClass: "text-hui",
    },
    {
      label: "已开通配送站点",
      value: 3,
      type: "",
      elTagClass: "text-blue-700 cursor-pointer hover:text-opacity-75",
    },
    {
      label: "未开通配送站点",
      value: 4,
      type: "",
      elTagClass: "text-hui",
    },
  ],
  // 配送站点状态
  stationStatus: [
    { label: "正常", value: 0, type: "success" },
    { label: "已删除", value: 1, type: "info" },
  ],
  // 来源
  source: [
    { label: "配送站点", value: 1 },
    { label: "华潍", value: 2 },
  ],
  // 审批状态
  applyStatus: [
    { label: "待审核", value: 1, type: "info" },
    { label: "通过", value: 2, type: "success" },
    { label: "驳回", value: 3, type: "danger" },
  ],
  // 取车状态
  pickStatus: [
    { label: "待取车", value: 1, type: "info" },
    { label: "已取车", value: 2, type: "primary" },
  ],
  // 退车状态
  refundStatus: [
    { label: "待退车", value: 1, type: "info" },
    { label: "已退车", value: 2, type: "primary" },
  ],
  // 押金类型
  depositType: [
    { label: "通用", value: 1 },
    { label: "定制", value: 2 },
  ],
  // 流水押金类型
  ledgerDepositType: [
    { label: "缴押", value: 1 },
    { label: "退押", value: 2 },
  ],
  // 流水类型
  flowType: [
    { label: "押金退还", value: 1 },
    { label: "骑手租金支付", value: 2 },
    { label: "骑手租金分成", value: 3 },
  ],
  // 订单状态
  orderStatus: [
    { label: "待支付", value: 0, type: "info" },
    { label: "待生效", value: 1, type: "primary" },
    { label: "生效中", value: 2, type: "success" },
    { label: "已到期", value: 6, type: "danger" },
    { label: "已结束", value: 5, type: "danger" },
    { label: "已取消", value: 7, type: "danger" },
  ],
  // 提现结果
  cashOut: [
    { label: "提现成功", value: 2 },
    { label: "提现失败", value: 3 },
  ],
  // 支付方式
  payType: [
    { label: "支付宝,", value: 1 },
    { label: "微信", value: 2 },
  ],
  // 订单类型
  orderType: [
    { label: "普通车辆租赁", value: 1 },
    { label: "大客户集团租赁", value: 2 },
  ],
  // 车辆状态
  acc: [
    { label: "已熄火", value: 0, type: "info" },
    { label: "行驶中", value: 1, type: "success" },
    { label: "无网络", value: 2, type: "danger" },
    { label: "未上线", value: 3, type: "danger" },
    { label: "终端被拆卸", value: 4, type: "danger" },
  ],
  // 出库状态
  outStatus: [
    { label: "未出库", value: 0, type: "info" },
    { label: "已出库", value: 1, type: "success" },
    { label: "已接收", value: 2, type: "success" },
  ],
  //仓库类型
  storeType: [
    { label: "新车仓库", value: 1 },
    { label: "售后仓库", value: 2 },
  ],
  // 补缴状态
  paymentStatus: [
    { label: "待补缴", value: 1, type: "info" },
    { label: "已补缴", value: 2, type: "success" },
  ],
  // 补缴类型
  profitType: [
    { label: "亏损", value: 1 },
    { label: "收益", value: 2 },
    { label: "持平", value: 3 },
  ],
  // 产品模式
  goodsMode: [
    { label: "租赁", value: 1 },
    { label: "分期", value: 2 },
  ],
  // 分期合约状态
  contractStatus: [
    { label: "审核中", value: 1, type: "info" },
    { label: "审核未通过", value: 2, type: "danger" },
    { label: "待生效", value: 3, type: "success" },
    { label: "生效中", value: 4, type: "success" },
    { label: "已结束", value: 5, type: "danger" },
  ],
  // 更新方式
  updateType: [
    { label: "可选更新", value: 0 },
    { label: "强制更新", value: 1 },
  ],
  // 订车单状态
  carOrderStatus: [
    { label: "待接单", value: 1 },
    { label: "备车中", value: 2 },
    { label: "已备车", value: 3 },
  ],
  //商家类型
  storeType: [
    { label: "配送站点", value: 1 },
    { label: "合作服务商", value: 3 },
  ],
  //绑定状态
  bindStatus: [
    { label: '绑定中', value: 1 },
    { label: '已解绑', value: 2 },
  ],
  //车辆归属
  carBelong: [
    { label: '自有车辆', value: 1 },
    { label: '华潍车辆', value: 2 },
  ],
  // 租金情况
  rentStatus: [
    { label: '有逾期', value: 1, type: 'danger' },
    { label: '已结清', value: 2, type: 'primary' },
  ],
  // 还款状态
  repayStatus: [
    { label: '待还款', value: 0, type: 'info' },
    { label: '已还款', value: 1, type: 'primary' },
    { label: '已取消', value: 2, type: 'info' },
  ],
  // 还款方式
  paySource: [
    { label: '自动还款', value: 1 },
    { label: '手动还款', value: 2 },
  ],
  //能源类型
  energys: [
    { label: "汽油", value: '1'},
    { label: "柴油", value: '4'},
    { label: "燃气", value: '5'},
    { label: "纯电动", value: '3'},
    { label: "插电式混动", value: '2'},
    { label: "轻混系统 ", value: '6'},
    { label: "氢燃料", value: '7'},
    { label: "增程式 ", value: '8'},
  ],
};
