------ 平台 --------------------
CREATE TABLE zm_platform_user (
  user_id int8 NOT NULL,
  user_name varchar(50) NOT NULL,
  user_password varchar(100) NOT NULL,
  user_mobile varchar(20) NOT NULL,
  user_sex int4,
  company_id varchar(40),
  user_role int4,
  user_account_status int4 DEFAULT 1,
  creator_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT zm_platform_user_pkey PRIMARY KEY (user_id)
);
COMMENT ON COLUMN zm_platform_user.user_id IS '用户id';
COMMENT ON COLUMN zm_platform_user.user_name IS '用户名';
COMMENT ON COLUMN zm_platform_user.user_password IS '密码';
COMMENT ON COLUMN zm_platform_user.user_mobile IS '手机号';
COMMENT ON COLUMN zm_platform_user.user_sex IS '性别';
COMMENT ON COLUMN zm_platform_user.company_id IS '车企id';
COMMENT ON COLUMN zm_platform_user.user_role IS '1:平台管理员; 2:车企管理员';
COMMENT ON COLUMN zm_platform_user.user_account_status IS '1:启用;0:停用';
COMMENT ON COLUMN zm_platform_user.creator_id IS '创建者id';
COMMENT ON COLUMN zm_platform_user.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_platform_user.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_platform_user IS '管理端用户表';

CREATE TABLE zm_v_enterprise (
  key_id int8 NOT NULL,
  ep_name varchar(100) NOT NULL,
  credit_code varchar(100) NOT NULL,
  ep_type int4 NOT NULL,
  contacts varchar(50) NOT NULL,
  contacts_phone varchar(20) NOT NULL,
  opening_time varchar(100) NOT NULL,
  area_code int4 NOT NULL,
  address varchar(200) NOT NULL,
  introduce text NOT NULL,
  ep_pics text ,
  promise_pdf text ,
  price_list_pdf text ,
  ep_status int4 NOT NULL DEFAULT 0,
  delete_flag int4 NOT NULL DEFAULT 0,
  total_star float8 NOT NULL DEFAULT 0,
  evaluate_num int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  opt_org_id varchar(40) ,
  fault_ep_code varchar(100),
  service_start_time timestamp(6),
  service_end_time timestamp(6),
  admin_user_id int8,
  withdraw_bank_no varchar(100),
  withdraw_bank_name varchar(100),
  withdraw_bank_user varchar(100),
  withdraw_bank_mobile varchar(100),
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_v_enterprise PRIMARY KEY (key_id)
);

COMMENT ON COLUMN zm_v_enterprise.key_id IS '主键ID';
COMMENT ON COLUMN zm_v_enterprise.ep_name IS '企业名称';
COMMENT ON COLUMN zm_v_enterprise.credit_code IS '社会信用代码';
COMMENT ON COLUMN zm_v_enterprise.ep_type IS '企业类型';
COMMENT ON COLUMN zm_v_enterprise.contacts IS '联系人';
COMMENT ON COLUMN zm_v_enterprise.contacts_phone IS '联系电话';
COMMENT ON COLUMN zm_v_enterprise.opening_time IS '营业时间';
COMMENT ON COLUMN zm_v_enterprise.area_code IS '所在地区';
COMMENT ON COLUMN zm_v_enterprise.address IS '详细地址';
COMMENT ON COLUMN zm_v_enterprise.introduce IS '企业简介';
COMMENT ON COLUMN zm_v_enterprise.ep_pics IS '门头照片';
COMMENT ON COLUMN zm_v_enterprise.promise_pdf IS '承诺书';
COMMENT ON COLUMN zm_v_enterprise.price_list_pdf IS '价目表';
COMMENT ON COLUMN zm_v_enterprise.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_enterprise.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN zm_v_enterprise.ep_status IS '状态(0:正常,1:停用)';
COMMENT ON COLUMN zm_v_enterprise.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN zm_v_enterprise.total_star IS '总体得分';
COMMENT ON COLUMN zm_v_enterprise.evaluate_num IS '评价条数';
COMMENT ON COLUMN zm_v_enterprise.opt_user_id IS '添加人ID';
COMMENT ON COLUMN zm_v_enterprise.opt_org_id IS '添加单位ID';
COMMENT ON COLUMN zm_v_enterprise.fault_ep_code IS '维修企业编码';
COMMENT ON COLUMN zm_v_enterprise.service_start_time IS '服务开始时间';
COMMENT ON COLUMN zm_v_enterprise.service_end_time IS '服务结束时间';
COMMENT ON COLUMN zm_v_enterprise.admin_user_id IS '管理员用户ID';
COMMENT ON COLUMN zm_v_enterprise.withdraw_bank_no IS '提现银行卡';
COMMENT ON COLUMN zm_v_enterprise.withdraw_bank_name IS '提现银行名称';
COMMENT ON COLUMN zm_v_enterprise.withdraw_bank_user IS '提现用户名';
COMMENT ON COLUMN zm_v_enterprise.withdraw_bank_mobile IS '提现手机号';
COMMENT ON TABLE zm_v_enterprise IS '企业信息表';

CREATE TABLE zm_car_type (
  key_id int8 NOT NULL,
  car_type_name varchar(200) NOT NULL,
  remarks varchar(200) ,
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  sort_num int4,
  CONSTRAINT pk_zm_car_type PRIMARY KEY (key_id)
);
COMMENT ON COLUMN zm_car_type.key_id IS '主键ID';
COMMENT ON COLUMN zm_car_type.car_type_name IS '车辆类型';
COMMENT ON COLUMN zm_car_type.remarks IS '描述';
COMMENT ON COLUMN zm_car_type.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN zm_car_type.opt_user_id IS '操作人ID';
COMMENT ON COLUMN zm_car_type.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_car_type.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN zm_car_type.sort_num IS '排序';
COMMENT ON TABLE zm_car_type IS '企业服务平台车辆类型表';

CREATE TABLE zm_car_model (
  key_id int8 NOT NULL,
  car_model_name varchar(200) NOT NULL,
  car_brand_id int8 NOT NULL,
  car_type_id int8 NOT NULL,
  car_pics text ,
  car_params text ,
  config_params text ,
  remarks varchar(200) ,
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  energy_type int4 NOT NULL,
  CONSTRAINT pk_zm_car_model PRIMARY KEY (key_id)
);
COMMENT ON COLUMN zm_car_model.key_id IS '主键ID';
COMMENT ON COLUMN zm_car_model.car_model_name IS '车辆型号';
COMMENT ON COLUMN zm_car_model.car_brand_id IS '车辆品牌ID';
COMMENT ON COLUMN zm_car_model.car_type_id IS '车辆类型ID';
COMMENT ON COLUMN zm_car_model.car_pics IS '车辆参考样图';
COMMENT ON COLUMN zm_car_model.car_params IS '基本参数';
COMMENT ON COLUMN zm_car_model.config_params IS '配置参数';
COMMENT ON COLUMN zm_car_model.remarks IS '描述';
COMMENT ON COLUMN zm_car_model.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN zm_car_model.opt_user_id IS '操作人ID';
COMMENT ON COLUMN zm_car_model.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_car_model.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN zm_car_model.energy_type IS '能量类型';
COMMENT ON TABLE zm_car_model IS '企业服务平台车辆型号表';

CREATE TABLE zm_car_brand (
  key_id int8 NOT NULL,
  car_brand_name varchar(200) NOT NULL,
  remarks varchar(200),
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_car_brand PRIMARY KEY (key_id)
);
COMMENT ON COLUMN zm_car_brand.key_id IS '主键ID';
COMMENT ON COLUMN zm_car_brand.car_brand_name IS '品牌名称';
COMMENT ON COLUMN zm_car_brand.remarks IS '描述';
COMMENT ON COLUMN zm_car_brand.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN zm_car_brand.opt_user_id IS '操作人ID';
COMMENT ON COLUMN zm_car_brand.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_car_brand.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_car_brand IS '企业服务平台车辆品牌表';

CREATE TABLE zm_v_role (
  role_id int8 NOT NULL,
  role_name varchar(50),
  type int4 NOT NULL
  remark varchar(100),
  creator_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT zm_v_role_pkey PRIMARY KEY (role_id)
);

COMMENT ON COLUMN zm_v_role.role_id IS '角色ID';
COMMENT ON COLUMN zm_v_role.role_name IS '角色名';
COMMENT ON COLUMN zm_v_role.type IS '类型1:平台 2:车企';
COMMENT ON COLUMN zm_v_role.remark IS '备注';
COMMENT ON COLUMN zm_v_role.creator_id IS '创建人id';
COMMENT ON COLUMN zm_v_role.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_role.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_v_role IS '平台角色';

CREATE TABLE zm_v_role_menu (
  key_id int8 NOT NULL,
  role_id int8 NOT NULL,
  menu_id int8 NOT NULL,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT zm_v_role_menu_pkey PRIMARY KEY (key_id)
);

COMMENT ON COLUMN zm_v_role_menu.key_id IS '用户id';
COMMENT ON COLUMN zm_v_role_menu.role_id IS '角色id';
COMMENT ON COLUMN zm_v_role_menu.menu_id IS '菜单id';
COMMENT ON COLUMN zm_v_role_menu.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_role_menu.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_v_role_menu IS '平台角色菜单';

CREATE TABLE zm_v_user_role (
  key_id int8 NOT NULL,
  user_id int8 NOT NULL,,
  role_id int8 NOT NULL,,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT zm_v_user_role_pkey PRIMARY KEY (key_id)
);
COMMENT ON COLUMN zm_v_user_role.key_id IS '功能ID';
COMMENT ON COLUMN zm_v_user_role.user_id IS '用户id';
COMMENT ON COLUMN zm_v_user_role.role_id IS '角色id';
COMMENT ON COLUMN zm_v_user_role.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_user_role.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_v_user_role IS '用户角色';

CREATE TABLE zm_v_menu (
  menu_id int8 NOT NULL,
  name varchar(50) ,
  parent_id int8 DEFAULT 0,
  url varchar(500) ,
  type int4,
  icon varchar(50) ,
  order_num int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  perm_val int4,
  CONSTRAINT zm_v_menu_pkey PRIMARY KEY (menu_id)
);

COMMENT ON COLUMN zm_v_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN zm_v_menu.name IS '菜单名';
COMMENT ON COLUMN zm_v_menu.parent_id IS '父级菜单id';
COMMENT ON COLUMN zm_v_menu.url IS '跳转页面url';
COMMENT ON COLUMN zm_v_menu.type IS '菜单类型0:目录;1:菜单;';
COMMENT ON COLUMN zm_v_menu.icon IS '图标';
COMMENT ON COLUMN zm_v_menu.order_num IS '菜单序号';
COMMENT ON COLUMN zm_v_menu.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_menu.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_v_menu IS '系统菜单表';

CREATE TABLE zm_withdraw_rule (
  enterprise_id int8 NOT NULL,
  ratio int8 NOT NULL,
  operate_user int8 NOT NULL,
  gmt_create timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  insert_timestamp timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_zm_withdraw_rule PRIMARY KEY (key_id)
);
COMMENT ON COLUMN public.zm_withdraw_rule.enterprise_id IS '企业ID';
COMMENT ON COLUMN public.zm_withdraw_rule.ratio IS '提现比例';
COMMENT ON TABLE public.zm_withdraw_rule IS '企业提现规则信息表';

CREATE TABLE hw_rent_order (
  order_id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_id_card varchar(200) NOT NULL,
  user_real_name varchar(200) NOT NULL,
  enterprise_id int8 NOT NULL,
  product_id int8 NOT NULL,
  product_name varchar(100) NOT NULL,
  product_pic varchar(100) NOT NULL,
  start_time timestamp(6) NOT NULL,
  end_time timestamp(6) NOT NULL,
  end_time timestamp(6) NOT NULL,
  start_address varchar(300) NOT NULL,
  start_latitude float8 NOT NULL,
  start_longitude float8 NOT NULL,
  end_address varchar(300) NOT NULL,
  end_latitude float8 NOT NULL,
  end_longitude float8 NOT NULL,
  fee int8 NOT NULL default 0,
  car_brand_id int8 NOT NULL,
  car_brand varchar(200) NOT NULL,
  car_model_id int8 NOT NULL,
  car_model varchar(200) NOT NULL,
  car_type_id int8 NOT NULL,
  car_type varchar(200) NOT NULL,
  product_detail text NOT NULL,
  status int2 NOT NULL DEFAULT 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_hw_rent_order PRIMARY KEY (order_id)
);
COMMENT ON COLUMN hw_rent_order.status IS '状态1:已支付';
COMMENT ON COLUMN hw_rent_order.user_id IS '法人身份证';
COMMENT ON COLUMN hw_rent_order.user_id_card IS '法人身份证';
COMMENT ON COLUMN hw_rent_order.user_real_name IS '法人姓名';
COMMENT ON COLUMN hw_rent_order.product_id IS '产品id';
COMMENT ON COLUMN hw_rent_order.product_name IS '产品名称';
COMMENT ON COLUMN hw_rent_order.product_pic IS '产品图片';
COMMENT ON COLUMN hw_rent_order.start_time IS '开始时间';
COMMENT ON COLUMN hw_rent_order.end_time IS '结束时间';
COMMENT ON COLUMN hw_rent_order.start_address IS '取车地址';
COMMENT ON COLUMN hw_rent_order.start_latitude IS '取车地址纬度';
COMMENT ON COLUMN hw_rent_order.start_longitude IS '取车地址经度';
COMMENT ON COLUMN hw_rent_order.end_address IS '还车地址';
COMMENT ON COLUMN hw_rent_order.end_latitude IS '还车地址纬度';
COMMENT ON COLUMN hw_rent_order.end_longitude IS '还车地址经度';
COMMENT ON COLUMN hw_rent_order.fee IS '费用(分)';
COMMENT ON COLUMN hw_rent_order.car_brand IS '车品牌';
COMMENT ON COLUMN hw_rent_order.car_model IS '车型';
COMMENT ON COLUMN hw_rent_order.car_type IS '车类型';
COMMENT ON COLUMN hw_rent_order.product_detail IS '产品详情';
COMMENT ON COLUMN hw_rent_order.status IS '状态1:已支付';
COMMENT ON TABLE hw_rent_order IS '租赁订单表';
------ 商家 --------------------
CREATE TABLE zm_enterprise_station (
  station_id int8 NOT NULL,
  station_name varchar(50) NOT NULL,
  enterprise_id int8 NOT NULL,
  phone_num varchar(20),
  latitude float8,
  longitude float8,
  location varchar(500),
  city_code int4,
  city_name varchar(50),
  station_pics text,
  del_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  opt_timestamp timestamp(6),
  update_timestamp timestamp(6) DEFAULT CURRENT_TIMESTAMP,
  insert_timestamp timestamp(6) DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_zm_enterprise_station PRIMARY KEY (station_id)
);
COMMENT ON COLUMN zm_enterprise_station.station_id IS '企业id';
COMMENT ON COLUMN zm_enterprise_station.station_name IS '企业名称';
COMMENT ON COLUMN zm_enterprise_station.enterprise_id IS '租赁企业id';
COMMENT ON COLUMN zm_enterprise_station.latitude IS '纬度';
COMMENT ON COLUMN zm_enterprise_station.longitude IS '经度';
COMMENT ON COLUMN zm_enterprise_station.location IS '位置信息';
COMMENT ON COLUMN zm_enterprise_station.city_code IS '所属城市代码';
COMMENT ON COLUMN zm_enterprise_station.city_name IS '所属城市';
COMMENT ON COLUMN zm_enterprise_station.phone_num IS '联系电话';
COMMENT ON COLUMN zm_enterprise_station.station_pics IS '门脸照片';
COMMENT ON COLUMN zm_enterprise_station.del_flag IS '删除状态，0：未删除，1：已删除';
COMMENT ON COLUMN zm_enterprise_station.opt_user_id IS '关闭操作人ID';
COMMENT ON COLUMN zm_enterprise_station.opt_timestamp IS '操作时间';
COMMENT ON TABLE zm_enterprise_station IS '企业站点表(用于取还车)';

CREATE TABLE zm_enterprise_car (
  car_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  license varchar(10) NOT NULL,
  car_model_id int8 NOT NULL,
  register_date timestamp(6) NOT NULL,
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  rent_status int4 not null default 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_enterprise_car PRIMARY KEY (car_id)
);
COMMENT ON COLUMN zm_enterprise_car.car_id IS '主键ID';
COMMENT ON COLUMN zm_enterprise_car.license IS '车牌号';
COMMENT ON COLUMN zm_enterprise_car.car_model_id IS '车辆型号ID';
COMMENT ON COLUMN zm_enterprise_car.register_date IS '注册日期';
COMMENT ON COLUMN zm_enterprise_car.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN zm_enterprise_car.opt_user_id IS '操作人ID';
COMMENT ON COLUMN zm_enterprise_car.rent_status IS '当前租赁状态0:未租 1:已租';
COMMENT ON COLUMN zm_enterprise_car.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_enterprise_car.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_enterprise_car IS '企业服务平台车辆信息表';

CREATE TABLE zm_v_product (
  key_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  car_model_id int8 NOT NULL,
  day_fee int4,
  pic varchar(100),
  config_params text,
  up_status int4 DEFAULT 0,
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_v_product PRIMARY KEY (key_id)
);

COMMENT ON COLUMN zm_v_product.key_id IS '主键ID';
COMMENT ON COLUMN zm_v_product.enterprise_id IS '企业ID';
COMMENT ON COLUMN zm_v_product.car_model_id IS '车辆型号ID';
COMMENT ON COLUMN zm_v_product.day_fee IS '日租费(单位:分)';
COMMENT ON COLUMN zm_v_product.pic IS '产品图片';
COMMENT ON COLUMN zm_v_product.config_params IS '产品特色';
COMMENT ON COLUMN zm_v_product.up_status IS '上架状态(0:待处理,1:已上架,2:已下架)';
COMMENT ON COLUMN zm_v_product.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN zm_v_product.opt_user_id IS '操作人ID';
COMMENT ON COLUMN zm_v_product.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_product.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_v_product IS '企业服务平台产品信息表';

CREATE TABLE zm_dispatch_car (
  dispatch_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_name varchar(40),
  order_id int8 not null,
  start_time timestamp(6),
  end_time timestamp(6),
  start_address varchar(500),
  end_address varchar(500),
  car_id int8,
  license varchar(20),
  status int4 not null default 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),,
  CONSTRAINT zm_dispatch_car_pkey PRIMARY KEY (dispatch_id)
);
COMMENT ON COLUMN zm_dispatch_car.user_id IS '用车人ID';
COMMENT ON COLUMN zm_dispatch_car.user_name IS '用车人名';
COMMENT ON COLUMN zm_dispatch_car.enterprise_id IS '派车企业id';
COMMENT ON COLUMN zm_dispatch_car.start_time IS '租车开始时间';
COMMENT ON COLUMN zm_dispatch_car.end_time IS '租车结束时间';
COMMENT ON COLUMN zm_dispatch_car.start_address IS '取车地址';
COMMENT ON COLUMN zm_dispatch_car.end_address IS '还车地址';
COMMENT ON COLUMN zm_dispatch_car.car_id IS '车辆ID';
COMMENT ON COLUMN zm_dispatch_car.license IS '车牌号';
COMMENT ON COLUMN zm_dispatch_car.status IS '状态0:待派车 1:已派车 2:已接车 3:已还车';
COMMENT ON TABLE zm_dispatch_car IS '派车单';

CREATE TABLE zm_enterprise_wallet (
  enterprise_id int8 NOT NULL,
  money int4 NOT NULL DEFAULT 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_enterprise_wallet PRIMARY KEY (enterprise_id)
);
COMMENT ON COLUMN zm_enterprise_wallet.enterprise_id IS '企业id';
COMMENT ON COLUMN zm_enterprise_wallet.money IS '金额(分)';
COMMENT ON TABLE zm_enterprise_wallet IS '钱包表';

CREATE TABLE zm_enterprise_transfer_apply (
  key_id int8 NOT NULL,
  apply_user_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  money int4 NOT NULL DEFAULT 0,
  transfer_account varchar(100) NOT NULL,
  transfer_user_name varchar(200) NOT NULL,
  commission int4 NOT NULL DEFAULT 0,
  remark varchar(500),
  transfer_pic varchar(300),
  handle_user_id int8,
  apply_status int2 NOT NULL DEFAULT 1,
  handle_timestamp timestamp(6),
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_enterprise_transfer_apply PRIMARY KEY (key_id)
);
COMMENT ON COLUMN zm_enterprise_transfer_apply.apply_user_id IS '申请人用户id';
COMMENT ON COLUMN zm_enterprise_transfer_apply.enterprise_id IS '企业id';
COMMENT ON COLUMN zm_enterprise_transfer_apply.money IS '金额(分), 包含了手续费的';
COMMENT ON COLUMN zm_enterprise_transfer_apply.transfer_account IS '提现账号';
COMMENT ON COLUMN zm_enterprise_transfer_apply.transfer_user_name IS '收款人';
COMMENT ON COLUMN zm_enterprise_transfer_apply.commission IS '手续费';
COMMENT ON COLUMN zm_enterprise_transfer_apply.remark IS '备注';
COMMENT ON COLUMN zm_enterprise_transfer_apply.transfer_pic IS '转账凭证';
COMMENT ON COLUMN zm_enterprise_transfer_apply.handle_user_id IS '处理人id';
COMMENT ON COLUMN zm_enterprise_transfer_apply.apply_status IS '审核状态(1:待转账,2:成功,3:失败)';
COMMENT ON COLUMN zm_enterprise_transfer_apply.handle_timestamp IS '处理时间';
COMMENT ON COLUMN zm_enterprise_transfer_apply.update_timestamp IS '修改时间';
COMMENT ON COLUMN zm_enterprise_transfer_apply.insert_timestamp IS '添加时间';
COMMENT ON TABLE zm_enterprise_transfer_apply IS '企业提现申请表';

CREATE TABLE zm_enterprise_wallet_log (
  key_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  type int4 NOT NULL default 1,
  money int4 NOT NULL DEFAULT 0,
  remark varchar(500),
  buss_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_enterprise_wallet_log PRIMARY KEY (key_id)
);
COMMENT ON COLUMN zm_enterprise_wallet_log.enterprise_id IS '企业id';
COMMENT ON COLUMN zm_enterprise_wallet_log.money IS '金额(分),大于0转入小于0转出';
COMMENT ON COLUMN zm_enterprise_wallet_log.type IS '类型1:订单费用 2:提现费用';
COMMENT ON COLUMN zm_enterprise_wallet_log.buss_id IS '业务id,type=1订单id,type=2提现申请id';
COMMENT ON TABLE zm_enterprise_wallet_log IS '企业钱包历史表';
------ 用户 --------------------
CREATE TABLE zm_v_user (
  user_id int8 NOT NULL,
  user_name varchar(20) not null,
  user_password varchar(200),
  user_mobile varchar(20) not null,
  user_sex int4,
  user_nick_name varchar(20) ,
  user_path varchar(100),
  birthday timestamp(6),
  user_real_name varchar(20),
  id_card varchar(200),
  user_account_status int4 DEFAULT 1,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT zm_v_user_pkey PRIMARY KEY (user_id)
);

COMMENT ON COLUMN zm_v_user.user_id IS '用户ID';
COMMENT ON COLUMN zm_v_user.user_name IS '用户名';
COMMENT ON COLUMN zm_v_user.user_password IS '密码';
COMMENT ON COLUMN zm_v_user.user_mobile IS '手机号';
COMMENT ON COLUMN zm_v_user.user_sex IS '男1;女2';
COMMENT ON COLUMN zm_v_user.change_password_status IS '强制修改密码状态(1:需要修改;0:不需要修改)';
COMMENT ON COLUMN zm_v_user.user_account_status IS '1:启用;0:停用';
COMMENT ON COLUMN zm_v_user.user_nick_name IS '用户名昵称';
COMMENT ON COLUMN zm_v_user.user_path IS '用户头像';
COMMENT ON COLUMN zm_v_user.birthday IS '生日';
COMMENT ON COLUMN zm_v_user.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN zm_v_user.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE zm_v_user IS '租赁用户信息';

CREATE TABLE zm_user_order (
  order_id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_id_card varchar(200) NOT NULL,
  user_real_name varchar(200) NOT NULL,
  enterprise_id int8 NOT NULL,
  product_id int8 NOT NULL,
  product_name varchar(100) NOT NULL,
  product_pic varchar(100) NOT NULL,
  start_time timestamp(6) NOT NULL,
  end_time timestamp(6) NOT NULL,
  end_time timestamp(6) NOT NULL,
  start_address varchar(300) NOT NULL,
  start_latitude float8 NOT NULL,
  start_longitude float8 NOT NULL,
  end_address varchar(300) NOT NULL,
  end_latitude float8 NOT NULL,
  end_longitude float8 NOT NULL,
  fee int8 NOT NULL default 0,
  car_brand_id int8 NOT NULL,
  car_brand varchar(200) NOT NULL,
  car_model_id int8 NOT NULL,
  car_model varchar(200) NOT NULL,
  car_type_id int8 NOT NULL,
  car_type varchar(200) NOT NULL,
  product_detail text NOT NULL,
  status int2 NOT NULL DEFAULT 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_user_order PRIMARY KEY (order_id)
);
COMMENT ON COLUMN zm_user_order.status IS '状态1:已支付';
COMMENT ON COLUMN zm_user_order.user_id IS '法人身份证';
COMMENT ON COLUMN zm_user_order.user_idcard IS '法人身份证';
COMMENT ON COLUMN zm_user_order.user_real_name IS '法人姓名';
COMMENT ON COLUMN zm_user_order.product_id IS '产品id';
COMMENT ON COLUMN zm_user_order.product_name IS '产品名称';
COMMENT ON COLUMN zm_user_order.product_pic IS '产品图片';
COMMENT ON COLUMN zm_user_order.start_time IS '开始时间';
COMMENT ON COLUMN zm_user_order.end_time IS '结束时间';
COMMENT ON COLUMN zm_user_order.start_address IS '取车地址';
COMMENT ON COLUMN zm_user_order.start_latitude IS '取车地址纬度';
COMMENT ON COLUMN zm_user_order.start_longitude IS '取车地址经度';
COMMENT ON COLUMN zm_user_order.end_address IS '还车地址';
COMMENT ON COLUMN zm_user_order.end_latitude IS '还车地址纬度';
COMMENT ON COLUMN zm_user_order.end_longitude IS '还车地址经度';
COMMENT ON COLUMN zm_user_order.fee IS '费用(分)';
COMMENT ON COLUMN zm_user_order.car_brand IS '车品牌';
COMMENT ON COLUMN zm_user_order.car_model IS '车型';
COMMENT ON COLUMN zm_user_order.car_type IS '车类型';
COMMENT ON COLUMN zm_user_order.product_detail IS '产品详情';
COMMENT ON COLUMN zm_user_order.status IS '状态0待支付 1:已支付';
COMMENT ON TABLE zm_user_order IS '租赁订单表';

CREATE TABLE zm_user_pick_up (
  dispatch_id int8 NOT NULL,
  user_id int8 NOT NULL,
  car_id int8,
  car_pics text,
  remark varchar(500),
  get_time timestamp(6),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_user_pick_up PRIMARY KEY (dispatch_id)
);
COMMENT ON COLUMN zm_user_pick_up.dispatch_id IS '派车单id';
COMMENT ON COLUMN zm_user_pick_up.user_id IS '用户id';
COMMENT ON COLUMN zm_user_pick_up.car_id IS '车id';
COMMENT ON COLUMN zm_user_pick_up.get_time IS '取车时间';
COMMENT ON COLUMN zm_user_pick_up.car_pics IS '取车时的车辆图片信息';
COMMENT ON COLUMN zm_user_pick_up.remark IS '备注';
COMMENT ON TABLE zm_user_pick_up IS '取车信息表';

CREATE TABLE zm_user_car_back (
  dispatch_id int8 NOT NULL,
  user_id int8 NOT NULL,
  car_id int8,
  car_pics text,
  remark varchar(500),
  extra_fee int8 default 0,
  back_time timestamp(6),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_zm_user_car_back PRIMARY KEY (dispatch_id)
);
COMMENT ON COLUMN zm_user_car_back.dispatch_id IS '派车单id';
COMMENT ON COLUMN zm_user_car_back.user_id IS '用户id';
COMMENT ON COLUMN zm_user_car_back.car_id IS '车id';
COMMENT ON COLUMN zm_user_car_back.back_time IS '还车时间';
COMMENT ON COLUMN zm_user_car_back.car_pics IS '取车时的车辆图片信息';
COMMENT ON COLUMN zm_user_car_back.remark IS '备注';
COMMENT ON COLUMN zm_user_car_back.extra_fee IS '额外费用(分)';
COMMENT ON TABLE zm_user_car_back IS '还车信息表';