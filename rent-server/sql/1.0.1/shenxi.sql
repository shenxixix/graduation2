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
  ep_pics text COLLATE pg_catalog.default,
  promise_pdf text COLLATE pg_catalog.default,
  price_list_pdf text COLLATE pg_catalog.default,
  ep_status int4 NOT NULL DEFAULT 0,
  delete_flag int4 NOT NULL DEFAULT 0,
  total_star float8 NOT NULL DEFAULT 0,
  evaluate_num int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  opt_org_id varchar(40) COLLATE pg_catalog.default,
  fault_ep_code varchar(100) COLLATE pg_catalog.default,
  service_start_time timestamp(6),
  service_end_time timestamp(6),
  admin_user_id int8,
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
------ 商家 --------------------

------ 用户 --------------------