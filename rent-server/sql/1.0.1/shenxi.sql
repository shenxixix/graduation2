------ 平台 --------------------
CREATE TABLE rent_v_manager (
  user_id int8 NOT NULL,
  user_name varchar(50) NOT NULL,
  user_password varchar(100) NOT NULL,
  user_mobile varchar(20) NOT NULL,
  user_sex int4,
  enterprise_id int8,
  user_role int4,
  user_account_status int4 DEFAULT 1,
  creator_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_v_manager_pkey PRIMARY KEY (user_id)
);
COMMENT ON COLUMN rent_v_manager.user_id IS '用户id';
COMMENT ON COLUMN rent_v_manager.user_name IS '用户名';
COMMENT ON COLUMN rent_v_manager.user_password IS '密码';
COMMENT ON COLUMN rent_v_manager.user_mobile IS '手机号';
COMMENT ON COLUMN rent_v_manager.user_sex IS '性别';
COMMENT ON COLUMN rent_v_manager.enterprise_id IS '车企id';
COMMENT ON COLUMN rent_v_manager.user_role IS '1:平台管理员; 2:车企管理员';
COMMENT ON COLUMN rent_v_manager.user_account_status IS '1:启用;0:停用';
COMMENT ON COLUMN rent_v_manager.creator_id IS '创建者id';
COMMENT ON COLUMN rent_v_manager.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_manager.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_manager IS '管理端用户表';
INSERT INTO rent_v_manager(user_id, user_name, user_password, user_mobile, user_sex, enterprise_id, user_role, user_account_status, creator_id, update_timestamp, insert_timestamp) VALUES (1, '平台管理员', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '158000001234', 1, NULL, 1, 1, 1, now(), now());

CREATE TABLE rent_v_enterprise (
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
  CONSTRAINT pk_rent_v_enterprise PRIMARY KEY (key_id)
);

COMMENT ON COLUMN rent_v_enterprise.key_id IS '主键ID';
COMMENT ON COLUMN rent_v_enterprise.ep_name IS '企业名称';
COMMENT ON COLUMN rent_v_enterprise.credit_code IS '社会信用代码';
COMMENT ON COLUMN rent_v_enterprise.ep_type IS '企业类型';
COMMENT ON COLUMN rent_v_enterprise.contacts IS '联系人';
COMMENT ON COLUMN rent_v_enterprise.contacts_phone IS '联系电话';
COMMENT ON COLUMN rent_v_enterprise.opening_time IS '营业时间';
COMMENT ON COLUMN rent_v_enterprise.area_code IS '所在地区';
COMMENT ON COLUMN rent_v_enterprise.address IS '详细地址';
COMMENT ON COLUMN rent_v_enterprise.introduce IS '企业简介';
COMMENT ON COLUMN rent_v_enterprise.ep_pics IS '门头照片';
COMMENT ON COLUMN rent_v_enterprise.promise_pdf IS '承诺书';
COMMENT ON COLUMN rent_v_enterprise.price_list_pdf IS '价目表';
COMMENT ON COLUMN rent_v_enterprise.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_enterprise.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN rent_v_enterprise.ep_status IS '状态(0:正常,1:停用)';
COMMENT ON COLUMN rent_v_enterprise.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN rent_v_enterprise.total_star IS '总体得分';
COMMENT ON COLUMN rent_v_enterprise.evaluate_num IS '评价条数';
COMMENT ON COLUMN rent_v_enterprise.opt_user_id IS '添加人ID';
COMMENT ON COLUMN rent_v_enterprise.opt_org_id IS '添加单位ID';
COMMENT ON COLUMN rent_v_enterprise.fault_ep_code IS '维修企业编码';
COMMENT ON COLUMN rent_v_enterprise.service_start_time IS '服务开始时间';
COMMENT ON COLUMN rent_v_enterprise.service_end_time IS '服务结束时间';
COMMENT ON COLUMN rent_v_enterprise.admin_user_id IS '管理员用户ID';
COMMENT ON COLUMN rent_v_enterprise.withdraw_bank_no IS '提现银行卡';
COMMENT ON COLUMN rent_v_enterprise.withdraw_bank_name IS '提现银行名称';
COMMENT ON COLUMN rent_v_enterprise.withdraw_bank_user IS '提现用户名';
COMMENT ON COLUMN rent_v_enterprise.withdraw_bank_mobile IS '提现手机号';
COMMENT ON TABLE rent_v_enterprise IS '企业信息表';

CREATE TABLE rent_car_type (
  key_id int8 NOT NULL,
  car_type_name varchar(200) NOT NULL,
  remarks varchar(200) ,
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  sort_num int4,
  CONSTRAINT pk_rent_car_type PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_car_type.key_id IS '主键ID';
COMMENT ON COLUMN rent_car_type.car_type_name IS '车辆类型';
COMMENT ON COLUMN rent_car_type.remarks IS '描述';
COMMENT ON COLUMN rent_car_type.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN rent_car_type.opt_user_id IS '操作人ID';
COMMENT ON COLUMN rent_car_type.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_car_type.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN rent_car_type.sort_num IS '排序';
COMMENT ON TABLE rent_car_type IS '企业服务平台车辆类型表';
INSERT INTO rent_car_type(key_id, car_type_name, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, sort_num) VALUES (1736643404114395137, '6-15座商务车', NULL, 0, 1, '2023-12-19 05:44:40.673', '2023-12-18 07:03:47.474013', 4);
INSERT INTO rent_car_type(key_id, car_type_name, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, sort_num) VALUES (1736663052411125762, '皮卡', NULL, 0, 1, '2023-12-19 06:03:42.879', '2023-12-18 08:21:52.017876', 5);
INSERT INTO rent_car_type(key_id, car_type_name, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, sort_num) VALUES (1736643322812006402, 'SUV', NULL, 0, 1, '2024-01-10 06:16:57.291', '2023-12-18 07:03:28.090334', 7);
INSERT INTO rent_car_type(key_id, car_type_name, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, sort_num) VALUES (1736643118847197185, '轿车', NULL, 0, 1, '2024-01-10 06:17:07.056', '2023-12-18 07:02:39.462262', 2);
INSERT INTO rent_car_type(key_id, car_type_name, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, sort_num) VALUES (1736643368597028865, '商务车', NULL, 0, 1, '2024-01-10 06:19:53.335', '2023-12-18 07:03:39.004573', 8);
INSERT INTO rent_car_type(key_id, car_type_name, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, sort_num) VALUES (1736643290343899138, '新能源', NULL, 0, 1, '2024-01-10 06:23:59.996', '2023-12-18 07:03:20.347653', 9);

CREATE TABLE rent_car_model (
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
  CONSTRAINT pk_rent_car_model PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_car_model.key_id IS '主键ID';
COMMENT ON COLUMN rent_car_model.car_model_name IS '车辆型号';
COMMENT ON COLUMN rent_car_model.car_brand_id IS '车辆品牌ID';
COMMENT ON COLUMN rent_car_model.car_type_id IS '车辆类型ID';
COMMENT ON COLUMN rent_car_model.car_pics IS '车辆参考样图';
COMMENT ON COLUMN rent_car_model.car_params IS '基本参数';
COMMENT ON COLUMN rent_car_model.config_params IS '配置参数';
COMMENT ON COLUMN rent_car_model.remarks IS '描述';
COMMENT ON COLUMN rent_car_model.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN rent_car_model.opt_user_id IS '操作人ID';
COMMENT ON COLUMN rent_car_model.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_car_model.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN rent_car_model.energy_type IS '能量类型';
COMMENT ON TABLE rent_car_model IS '企业服务平台车辆型号表';
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736659916736831490, '秦', 1736659321950969857, 1736643118847197185, 'https://img.yolodt.com/caafc173918141138c7e7ee6d87bbfe2.png', '[{key:车辆类型,value:轿车,unit:-,type:2,id:1736643118847197185},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:燃气,unit:-,type:3,id:5}]', '[{key:座椅加热,value:无,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:40.134', '2023-12-18 08:09:24.389141', 5);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736649623692623874, 'r6', 1735267980918620161, 1736643290343899138, 'https://huaweizulin.oss-cn-shanghai.aliyuncs.com/1702884486069f04062dd14394f92b10cbcba151d5399G.jpg', '[{key:车辆类型,value:1736643290343899138,unit:-,type:2},{key:座位数,value:2,unit:座,type:1},{key:车门数,value:1,unit:门,type:1},{key:能源类型,value:3,unit:-,type:3}]', '[{key:发动机,value:有,unit:null,type:null}]', NULL, 1, 1, '2023-12-18 08:07:11.656', '2023-12-18 07:28:30.348143', 3);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737305603849809922, '测试车型', 1737041617334022145, 1736643118847197185, 'https://img.yolodt.com/e3d14887bed84fbf8e85d224a7ee3442.jpg', '[{key:车辆类型,value:轿车,unit:-,type:2},{key:座位数,value:4,unit:座,type:1},{key:车门数,value:4,unit:门,type:1},{key:能源类型,value:1,unit:-,type:3}]', '[{key:测试,value:无,unit:null,type:null}]', NULL, 1, 1, '2023-12-20 03:02:18.895', '2023-12-20 02:55:08.197958', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736659645948370945, '长安逸动', 1736643463790952450, 1736643118847197185, 'https://img.yolodt.com/0da498bae2ba41c8a6c81d0c44d32a0e.jpg', '[{key:车辆类型,value:轿车,unit:-,type:2,id:1736643118847197185},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:插电式混动,unit:-,type:3,id:2}]', '[{key:座椅加热,value:无,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:21:08.363', '2023-12-18 08:08:19.831198', 2);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736986894945886210, 'Q5', 1736643494254182401, 1736643322812006402, 'https://img.yolodt.com/2a1befe05b6c432b95037fca24a73864.jpg', '[{key:车辆类型,value:SUV,unit:-,type:2,id:1736643322812006402},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:汽油,unit:-,type:3,id:1}]', '[{key:四驱悬挂,value:有,unit:null,type:null,id:null},{key:座椅加热,value:无,unit:null,type:null,id:null},{key:定速巡航,value:有,unit:null,type:null,id:null},{key:转向助力,value:有,unit:null,type:null,id:null},{key:预防撞,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:45.248', '2023-12-19 05:48:42.076526', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736662719240781826, 'A6', 1736643494254182401, 1736643368597028865, 'https://img.yolodt.com/ed84bc6cc4114ea188389ef23eb231cb.jpg', '[{key:车辆类型,value:商务车,unit:-,type:2,id:1736643368597028865},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:汽油,unit:-,type:3,id:1}]', '[{key:悬挂,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:35.072', '2023-12-18 08:20:32.570625', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736662908697493505, '汉', 1736659321950969857, 1736643404114395137, 'https://img.yolodt.com/1636edf7d39e433c99c8b4aabf93bc8b.jpg', '[{key:车辆类型,value:6-15座商务车,unit:-,type:2,id:1736643404114395137},{key:座位数,value:7,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:轻混系统 ,unit:-,type:3,id:6}]', '[{key:悬挂,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:18:37.266', '2023-12-18 08:21:17.72951', 6);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736661317168799745, 'QS5', 1736643463790952450, 1736643322812006402, 'https://img.yolodt.com/19998ab48b8141ee94ef5f472351cc8d.png,https://img.yolodt.com/a3e1456bfd8d4bfeb7774269f0b42e31.jpg', '[{key:车辆类型,value:SUV,unit:-,type:2,id:1736643322812006402},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:汽油,unit:-,type:3,id:1}]', '[{key:悬挂,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:52.725', '2023-12-18 08:14:58.28176', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736663196896509954, '长城炮', 1736659304125177858, 1736663052411125762, 'https://img.yolodt.com/97802e50be984ce1931ae45a00c7e72f.png', '[{key:车辆类型,value:皮卡,unit:-,type:2,id:1736663052411125762},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:纯电动,unit:-,type:3,id:3}]', '[{key:悬挂,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:17:39.714', '2023-12-18 08:22:26.442117', 3);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737715989392527362, 'M7', 1737715753391624193, 1736643368597028865, 'https://img.yolodt.com/9e162f4545ab436f8a6ceab92f286500.jpg', '[{key:车辆类型,value:商务车,unit:-,type:2,id:1736643368597028865},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:插电式混动,unit:-,type:3,id:2}]', '[{key:自动驾驶,value:有,unit:null,type:null,id:null},{key:座椅加热,value:有,unit:null,type:null,id:null},{key:柏林之声,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:18:07.468', '2023-12-21 06:05:51.73029', 2);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736660124556206081, '宝马3系', 1736643445822554113, 1736643118847197185, 'https://img.yolodt.com/ec05c2a893344e77900ae3626053b1b9.png', '[{key:车辆类型,value:轿车,unit:-,type:2,id:1736643118847197185},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:汽油,unit:-,type:3,id:1}]', '[{key:悬挂,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:15.844', '2023-12-18 08:10:13.938975', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737303220847624193, '哈弗H6', 1737041673244094465, 1736643118847197185, 'https://huaweizulin.oss-cn-shanghai.aliyuncs.com/17030403194176095c5197c1b490c99bdd82c4522e64bG.jpg', '[{key:车辆类型,value:轿车,unit:-,type:2},{key:座位数,value:1,unit:座,type:1},{key:车门数,value:2,unit:门,type:1},{key:能源类型,value:1,unit:-,type:3}]', '[{key:11,value:有,unit:null,type:null}]', NULL, 1, 1, '2023-12-20 03:02:24.433', '2023-12-20 02:45:40.045571', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737022166597251074, '比亚迪秦', 1735267804820766721, 1736643290343899138, 'https://huaweizulin.oss-cn-shanghai.aliyuncs.com/170297331723244fb24f10e6b48ac9f2b014d5f295b36G.jpg', '[{key:车辆类型,value:商务车,unit:-,type:2,id:1736643368597028865},{key:座位数,value:1,unit:座,type:1,id:null},{key:车门数,value:2,unit:门,type:1,id:null},{key:能源类型,value:氢燃料,unit:-,type:3,id:7}]', '[{key:2,value:无,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:27.016', '2023-12-19 08:08:51.493469', 7);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737714779474235394, '海豹', 1736659321950969857, 1736643118847197185, 'https://img.yolodt.com/2b260632fd1e460e82e389662c887ab9.png', '[{key:车辆类型,value:轿车,unit:-,type:2,id:1736643118847197185},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:燃气,unit:-,type:3,id:5}]', '[{key:助力转向,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:59.153', '2023-12-21 06:01:03.264851', 5);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1739469832308604929, '测试', 1739203873425539074, 1736663052411125762, 'https://img.yolodt.com/21c7a8b97d054efd86fec9b5f9368709.jpg', '[{key:车辆类型,value:轿车,unit:-,type:2,id:1736643118847197185},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:汽油,unit:-,type:3,id:1}]', '[{key:前驻车雷达,value:有,unit:null,type:null,id:null},{key:后驻车雷达,value:有,unit:null,type:null,id:null},{key:自动头灯,value:有,unit:null,type:null,id:null},{key:自适应远近光,value:有,unit:null,type:null,id:null},{key:胎压监测,value:有,unit:null,type:null,id:null},{key:自动驻车,value:有,unit:null,type:null,id:null},{key:上坡辅助,value:有,unit:null,type:null,id:null},{key:陡坡缓降,value:有,unit:null,type:null,id:null},{key:并线辅助,value:有,unit:null,type:null,id:null},{key:倒车影像,value:有,unit:null,type:null,id:null},{key:电动后备箱,value:无,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-27 03:17:14.355', '2023-12-26 02:15:00.480294', 1);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737680809986494466, 'Q8', 1736643494254182401, 1736643322812006402, 'https://img.yolodt.com/687a9f02cebc4c19a76d5d5709d49cec.jpg', '[{key:车辆类型,value:SUV,unit:-,type:2,id:1736643322812006402},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:燃气,unit:-,type:3,id:5}]', '[{key:3,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:17:52.107', '2023-12-21 03:46:04.306362', 5);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736660469613207553, '起源A70', 1736643463790952450, 1736643368597028865, 'https://img.yolodt.com/761afe9c4b074121974d3f1ad47ddbc9.png', '[{key:车辆类型,value:轿车,unit:-,type:2,id:1736643118847197185},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:燃气,unit:-,type:3,id:5}]', '[{key:悬挂,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:19:38.721', '2023-12-18 08:11:36.208011', 5);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737725846090813442, '长安UNIT', 1736643463790952450, 1736643118847197185, 'https://img.yolodt.com/33f572675e524c8f914d8c6479155b61.jpg', '[{key:车辆类型,value:6-15座商务车,unit:-,type:2,id:1736643404114395137},{key:座位数,value:5,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:燃气,unit:-,type:3,id:5}]', '[{key:方向盘,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:19:50.98', '2023-12-21 06:45:01.750372', 5);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1736663568478289922, 'F70', 1736643463790952450, 1736663052411125762, 'https://img.yolodt.com/6832720b1b334adeb3c287b33f110ae9.jpg', '[{key:车辆类型,value:皮卡,unit:-,type:2,id:1736663052411125762},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:增程式 ,unit:-,type:3,id:8}]', '[{key:悬挂,value:有,unit:null,type:null,id:null},{key:运动,value:无,unit:null,type:null,id:null},{key:启动弹射,value:有,unit:null,type:null,id:null},{key:氮气加速,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:20:02.051', '2023-12-18 08:23:55.032763', 8);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737304904609001474, '哈弗H2', 1737041673244094465, 1736643118847197185, 'https://img.yolodt.com/3fab24a72efb45b78aafd8395c2e95b8.jpg', '[{key:车辆类型,value:轿车,unit:-,type:2},{key:座位数,value:1,unit:座,type:1},{key:车门数,value:2,unit:门,type:1},{key:能源类型,value:3,unit:-,type:3}]', '[{key:1,value:有,unit:null,type:null}]', NULL, 1, 1, '2023-12-20 03:02:22.136', '2023-12-20 02:52:21.491768', 3);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1738037163037290498, '汉兰达', 1737715753391624193, 1736643404114395137, 'https://huaweizulin.oss-cn-shanghai.aliyuncs.com/17032153131783f839384367540d0a6c6fb2830949abeG.jpg', '[{key:车辆类型,value:6-15座商务车,unit:-,type:2,id:1736643404114395137},{key:座位数,value:1,unit:座,type:1,id:null},{key:车门数,value:2,unit:门,type:1,id:null},{key:能源类型,value:氢燃料,unit:-,type:3,id:7}]', '[{key:前驻车雷达,value:有,unit:null,type:null,id:null},{key:后驻车雷达,value:有,unit:null,type:null,id:null},{key:自动头灯,value:有,unit:null,type:null,id:null},{key:自适应远近光,value:有,unit:null,type:null,id:null},{key:胎压监测,value:有,unit:null,type:null,id:null},{key:自动驻车,value:有,unit:null,type:null,id:null},{key:上坡辅助,value:有,unit:null,type:null,id:null},{key:陡坡缓降,value:有,unit:null,type:null,id:null},{key:并线辅助,value:有,unit:null,type:null,id:null},{key:倒车影像,value:有,unit:null,type:null,id:null},{key:电动后备箱,value:无,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-22 03:22:05.500457', '2023-12-22 03:22:05.500457', 7);
INSERT INTO rent_car_model(key_id, car_model_name, car_brand_id, car_type_id, car_pics, car_params, config_params, remarks, delete_flag, opt_user_id, update_timestamp, insert_timestamp, energy_type) VALUES (1737715242319876098, '比亚迪宋', 1736659321950969857, 1736643322812006402, 'https://img.yolodt.com/8c2557d4a3a04fb6ae5ac9a29e3cedaa.png', '[{key:车辆类型,value:SUV,unit:-,type:2,id:1736643322812006402},{key:座位数,value:4,unit:座,type:1,id:null},{key:车门数,value:4,unit:门,type:1,id:null},{key:能源类型,value:汽油,unit:-,type:3,id:1}]', '[{key:助力转向,value:有,unit:null,type:null,id:null}]', NULL, 0, 1, '2023-12-21 08:18:16.564', '2023-12-21 06:02:53.61521', 1);

CREATE TABLE rent_car_brand (
  key_id int8 NOT NULL,
  car_brand_name varchar(200) NOT NULL,
  remarks varchar(200),
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_car_brand PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_car_brand.key_id IS '主键ID';
COMMENT ON COLUMN rent_car_brand.car_brand_name IS '品牌名称';
COMMENT ON COLUMN rent_car_brand.remarks IS '描述';
COMMENT ON COLUMN rent_car_brand.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN rent_car_brand.opt_user_id IS '操作人ID';
COMMENT ON COLUMN rent_car_brand.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_car_brand.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_car_brand IS '企业服务平台车辆品牌表';
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1735267746582855682, '丰田', NULL, 0, 1, '2023-12-14 11:57:25.173074', '2023-12-14 11:57:25.173074');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1735267804820766721, '奇瑞', NULL, 0, 1, '2023-12-14 11:57:39.059741', '2023-12-14 11:57:39.059741');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1735267980918620161, '雅马哈', NULL, 0, 1, '2023-12-14 11:58:21.043882', '2023-12-14 11:58:21.043882');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1736659321950969857, '比亚迪', NULL, 0, 1, '2023-12-19 05:44:55.736', '2023-12-18 08:07:02.582643');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1736659304125177858, '长城', NULL, 0, 1, '2023-12-19 05:45:04.055', '2023-12-18 08:06:58.348297');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1736643494254182401, '奥迪', NULL, 0, 1, '2023-12-19 05:45:06.876', '2023-12-18 07:04:08.963539');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1736643463790952450, '长安', NULL, 0, 1, '2023-12-19 05:45:10.302', '2023-12-18 07:04:01.702309');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1736643445822554113, '宝马', NULL, 0, 1, '2023-12-19 05:45:12.987', '2023-12-18 07:03:57.418');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1737041617334022145, '雷克萨斯', NULL, 0, 1, '2023-12-19 09:26:08.90941', '2023-12-19 09:26:08.90941');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1737041656659816449, '日产', NULL, 0, 1, '2023-12-19 09:26:18.286091', '2023-12-19 09:26:18.286091');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1737041673244094465, '本田', NULL, 0, 1, '2023-12-19 09:32:33.401', '2023-12-19 09:26:22.241955');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1737715753391624193, '问界', NULL, 0, 1, '2023-12-21 06:04:55.464131', '2023-12-21 06:04:55.464131');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202281846554625, '阿维塔', NULL, 0, 1, '2023-12-25 08:31:51.48012', '2023-12-25 08:31:51.48012');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202510272544769, '福特', NULL, 0, 1, '2023-12-25 08:32:45.940407', '2023-12-25 08:32:45.940407');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202536054931457, '吉利', NULL, 0, 1, '2023-12-25 08:32:52.088768', '2023-12-25 08:32:52.088768');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202599351173122, '广汽传媒', NULL, 0, 1, '2023-12-25 08:33:07.178573', '2023-12-25 08:33:07.178573');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202642460229634, '沃尔沃', NULL, 0, 1, '2023-12-25 08:33:17.456327', '2023-12-25 08:33:17.456327');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202763633672194, '别克', NULL, 0, 1, '2023-12-25 08:33:46.346015', '2023-12-25 08:33:46.346015');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202937349160961, '红旗', NULL, 0, 1, '2023-12-25 08:34:27.763354', '2023-12-25 08:34:27.763354');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739202955393056770, '哈佛', NULL, 0, 1, '2023-12-25 08:34:32.066403', '2023-12-25 08:34:32.066403');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203182648836097, '极氪', NULL, 0, 1, '2023-12-25 08:35:26.247524', '2023-12-25 08:35:26.247524');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203208691269633, '领克', NULL, 0, 1, '2023-12-25 08:35:32.46116', '2023-12-25 08:35:32.46116');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203332431626242, '凯迪拉克', NULL, 0, 1, '2023-12-25 08:36:01.959217', '2023-12-25 08:36:01.959217');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203392556974081, '理想', NULL, 0, 1, '2023-12-25 08:36:16.29632', '2023-12-25 08:36:16.29632');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203415940218881, '领跑', NULL, 0, 1, '2023-12-25 08:36:21.869948', '2023-12-25 08:36:21.869948');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203533984710658, '马自达', NULL, 0, 1, '2023-12-25 08:36:50.01883', '2023-12-25 08:36:50.01883');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203586455453698, '哪吒汽车', NULL, 0, 1, '2023-12-25 08:37:02.525002', '2023-12-25 08:37:02.525002');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203695410888706, '起亚', NULL, 0, 1, '2023-12-25 08:37:28.498553', '2023-12-25 08:37:28.498553');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203873425539074, '雪佛兰', NULL, 0, 1, '2023-12-25 08:38:10.941524', '2023-12-25 08:38:10.941524');
INSERT INTO "public"."rent_car_brand"("key_id", "car_brand_name", "remarks", "delete_flag", "opt_user_id", "update_timestamp", "insert_timestamp") VALUES (1739203893839216641, '雪铁龙', NULL, 0, 1, '2023-12-25 08:38:15.809657', '2023-12-25 08:38:15.809657');


CREATE TABLE rent_v_role (
  role_id int8 NOT NULL,
  role_name varchar(50),
  type int4 NOT NULL,
  remark varchar(100),
  creator_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_v_role_pkey PRIMARY KEY (role_id)
);
COMMENT ON COLUMN rent_v_role.role_id IS '角色ID';
COMMENT ON COLUMN rent_v_role.role_name IS '角色名';
COMMENT ON COLUMN rent_v_role.type IS '类型1:平台 2:车企';
COMMENT ON COLUMN rent_v_role.remark IS '备注';
COMMENT ON COLUMN rent_v_role.creator_id IS '创建人id';
COMMENT ON COLUMN rent_v_role.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_role.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_role IS '平台角色';
INSERT INTO "public"."rent_v_role"("role_id", "role_name", "type", "remark", "creator_id", "update_timestamp", "insert_timestamp") VALUES (1, '平台管理员', 1, '平台管理员', 1, '2024-03-18 04:19:14.135814', '2024-03-18 04:19:14.135814');
INSERT INTO "public"."rent_v_role"("role_id", "role_name", "type", "remark", "creator_id", "update_timestamp", "insert_timestamp") VALUES (2, '企业管理员', 2, '企业管理员', 1, '2024-03-18 04:19:49.952295', '2024-03-18 04:19:49.952295');

CREATE TABLE rent_v_role_menu (
  key_id int8 NOT NULL,
  role_id int8 NOT NULL,
  menu_id int8 NOT NULL,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_v_role_menu_pkey PRIMARY KEY (key_id)
);

COMMENT ON COLUMN rent_v_role_menu.key_id IS '用户id';
COMMENT ON COLUMN rent_v_role_menu.role_id IS '角色id';
COMMENT ON COLUMN rent_v_role_menu.menu_id IS '菜单id';
COMMENT ON COLUMN rent_v_role_menu.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_role_menu.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_role_menu IS '平台角色菜单';
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (1, 1, 1, '2024-03-18 04:43:16.603859', '2024-03-18 04:43:16.603859');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (2, 1, 101, '2024-03-18 04:43:16.603859', '2024-03-18 04:43:16.603859');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (3, 1, 2, '2024-03-18 04:44:05.852029', '2024-03-18 04:44:05.852029');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (4, 1, 201, '2024-03-18 04:44:19.036905', '2024-03-18 04:44:19.036905');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (5, 1, 3, '2024-03-18 04:44:27.97292', '2024-03-18 04:44:27.97292');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (6, 1, 301, '2024-03-18 04:44:36.02158', '2024-03-18 04:44:36.02158');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (7, 1, 4, '2024-03-18 04:44:48.64303', '2024-03-18 04:44:48.64303');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (8, 1, 401, '2024-03-18 04:44:59.630346', '2024-03-18 04:44:59.630346');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (9, 1, 402, '2024-03-18 04:45:08.982731', '2024-03-18 04:45:08.982731');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (10, 1, 5, '2024-03-18 04:45:30.049766', '2024-03-18 04:45:30.049766');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (11, 1, 501, '2024-03-18 04:45:38.414515', '2024-03-18 04:45:38.414515');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (12, 1, 11, '2024-03-18 04:45:52.123429', '2024-03-18 04:45:52.123429');
INSERT INTO "public"."rent_v_role_menu"("key_id", "role_id", "menu_id", "update_timestamp", "insert_timestamp") VALUES (13, 1, 1101, '2024-03-18 04:46:00.927473', '2024-03-18 04:46:00.927473');

CREATE TABLE rent_v_user_role (
  key_id int8 NOT NULL,
  user_id int8 NOT NULL,
  role_id int8 NOT NULL,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_v_user_role_pkey PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_v_user_role.key_id IS '功能ID';
COMMENT ON COLUMN rent_v_user_role.user_id IS '用户id';
COMMENT ON COLUMN rent_v_user_role.role_id IS '角色id';
COMMENT ON COLUMN rent_v_user_role.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_user_role.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_user_role IS '用户角色';
INSERT INTO rent_v_user_role(key_id, user_id, role_id, update_timestamp, insert_timestamp) VALUES (1, 1, 1, '2024-03-18 04:37:18.160418', '2024-03-18 04:37:18.160418');

CREATE TABLE rent_v_menu (
  menu_id int8 NOT NULL,
  name varchar(50) ,
  parent_id int8 DEFAULT 0,
  url varchar(500) ,
  type int4,
  icon varchar(50) ,
  order_num int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_v_menu_pkey PRIMARY KEY (menu_id)
);

COMMENT ON COLUMN rent_v_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN rent_v_menu.name IS '菜单名';
COMMENT ON COLUMN rent_v_menu.parent_id IS '父级菜单id';
COMMENT ON COLUMN rent_v_menu.url IS '跳转页面url';
COMMENT ON COLUMN rent_v_menu.type IS '菜单类型0:目录;1:菜单;';
COMMENT ON COLUMN rent_v_menu.icon IS '图标';
COMMENT ON COLUMN rent_v_menu.order_num IS '菜单序号';
COMMENT ON COLUMN rent_v_menu.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_menu.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_menu IS '系统菜单表';
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (1, '企业管理', 0, NULL, 0, NULL, 1, '2024-03-18 04:20:50.851672', '2024-03-18 04:20:50.851672');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (101, '企业列表', 1, NULL, 1, NULL, 1, '2024-03-18 04:21:21.268051', '2024-03-18 04:21:21.268051');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (201, '租车用户', 2, NULL, 1, NULL, 1, '2024-03-18 04:22:14.084679', '2024-03-18 04:22:14.084679');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (301, '订单列表', 3, NULL, 1, NULL, 1, '2024-03-18 04:23:11.778551', '2024-03-18 04:23:11.778551');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (401, '账号管理', 4, NULL, 1, NULL, 1, '2024-03-18 04:23:48.745623', '2024-03-18 04:23:48.745623');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (402, '权限管理', 4, NULL, 1, NULL, 2, '2024-03-18 04:24:05.221536', '2024-03-18 04:24:05.221536');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (2, '用户管理', 0, NULL, 0, NULL, 2, '2024-03-18 04:20:50.851672', '2024-03-18 04:20:50.851672');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (3, '订单管理', 0, NULL, 0, NULL, 3, '2024-03-18 04:22:48.366396', '2024-03-18 04:22:48.366396');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (4, '系统设置', 0, NULL, 0, NULL, 4, '2024-03-18 04:23:31.255607', '2024-03-18 04:23:31.255607');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (501, '车型库管理', 5, NULL, 1, NULL, 1, '2024-03-18 04:25:01.713096', '2024-03-18 04:25:01.713096');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (6, '车辆管理', 0, NULL, 0, NULL, 6, '2024-03-18 04:25:45.790592', '2024-03-18 04:25:45.790592');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (5, '平台支持', 0, NULL, 0, NULL, 15, '2024-03-18 04:24:31.206601', '2024-03-18 04:24:31.206601');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (7, '产品管理', 0, NULL, 0, NULL, 7, '2024-03-18 04:26:28.098094', '2024-03-18 04:26:28.098094');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (701, '产品列表', 7, NULL, 1, NULL, 1, '2024-03-18 04:26:57.347363', '2024-03-18 04:26:57.347363');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (601, '车辆档案', 6, NULL, 1, NULL, 1, '2024-03-18 04:25:59.542921', '2024-03-18 04:25:59.542921');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (8, '司机管理', 0, NULL, 0, NULL, 9, '2024-03-18 04:27:20.856086', '2024-03-18 04:27:20.856086');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (801, '司机档案', 8, NULL, 1, NULL, 1, '2024-03-18 04:27:48.596698', '2024-03-18 04:27:48.596698');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (9, '订单管理', 0, NULL, 0, NULL, 10, '2024-03-18 04:28:04.682373', '2024-03-18 04:28:04.682373');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (901, '订单列表', 9, NULL, 1, NULL, 1, '2024-03-18 04:28:23.513501', '2024-03-18 04:28:23.513501');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (10, '派车单管理', 0, NULL, 0, NULL, 11, '2024-03-18 04:28:42.870994', '2024-03-18 04:28:42.870994');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (1001, '派车待办', 10, NULL, 1, NULL, 1, '2024-03-18 04:29:08.021007', '2024-03-18 04:29:08.021007');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (11, '费用结算', 0, NULL, 0, NULL, 14, '2024-03-18 04:29:55.310768', '2024-03-18 04:29:55.310768');
INSERT INTO rent_v_menu(menu_id, name, parent_id, url, type, icon, order_num, update_timestamp, insert_timestamp) VALUES (1101, '结算单', 11, NULL, 1, NULL, 1, '2024-03-18 04:30:14.40721', '2024-03-18 04:30:14.40721');

CREATE TABLE rent_withdraw_rule (
  enterprise_id int8 NOT NULL,
  ratio int8 NOT NULL,
  operate_user int8 NOT NULL,
  gmt_create timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  insert_timestamp timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_rent_withdraw_rule PRIMARY KEY (enterprise_id)
);
COMMENT ON COLUMN public.rent_withdraw_rule.enterprise_id IS '企业ID';
COMMENT ON COLUMN public.rent_withdraw_rule.ratio IS '提现比例';
COMMENT ON TABLE public.rent_withdraw_rule IS '企业提现规则信息表';

CREATE TABLE rent_platform_log (
  key_id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_name varchar(50),
  operation varchar(50),
  content text,
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_platform_log_pkey PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_platform_log.user_id IS '用户id';
COMMENT ON COLUMN rent_platform_log.user_name IS '用户名';
COMMENT ON COLUMN rent_platform_log.operation IS '操作';
COMMENT ON COLUMN rent_platform_log.content IS '方法';
COMMENT ON COLUMN rent_platform_log.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_platform_log IS '平台日志';

------ 商家 --------------------
CREATE TABLE rent_enterprise_car (
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
  CONSTRAINT pk_rent_enterprise_car PRIMARY KEY (car_id)
);
COMMENT ON COLUMN rent_enterprise_car.car_id IS '主键ID';
COMMENT ON COLUMN rent_enterprise_car.license IS '车牌号';
COMMENT ON COLUMN rent_enterprise_car.car_model_id IS '车辆型号ID';
COMMENT ON COLUMN rent_enterprise_car.register_date IS '注册日期';
COMMENT ON COLUMN rent_enterprise_car.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN rent_enterprise_car.opt_user_id IS '操作人ID';
COMMENT ON COLUMN rent_enterprise_car.rent_status IS '当前租赁状态0:未租 1:已租';
COMMENT ON COLUMN rent_enterprise_car.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_enterprise_car.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_enterprise_car IS '企业服务平台车辆信息表';

drop table rent_enterprise_driver;
CREATE TABLE rent_enterprise_driver (
  driver_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  user_name varchar(20),
  mobile varchar(20),
  driver_license varchar(100),
  status int2 default 0,
  is_del int2 default 0,
  insert_timestamp timestamp(6) DEFAULT CURRENT_TIMESTAMP,
  update_timestamp timestamp(6) DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_rent_enterprise_driver PRIMARY KEY (driver_id)
);
COMMENT ON COLUMN rent_enterprise_driver.driver_id IS '司机ID';
COMMENT ON COLUMN rent_enterprise_driver.enterprise_id IS '企业ID';
COMMENT ON COLUMN rent_enterprise_driver.user_name IS '姓名';
COMMENT ON COLUMN rent_enterprise_driver.mobile IS '联系电话';
COMMENT ON COLUMN rent_enterprise_driver.status IS '状态0空闲 1派车中';
COMMENT ON COLUMN rent_enterprise_driver.is_del IS '删除状态 0 正常 1 删除';
COMMENT ON COLUMN rent_enterprise_driver.driver_license IS '驾照图片';
COMMENT ON COLUMN rent_enterprise_driver.insert_timestamp IS '插入时间戳';
COMMENT ON COLUMN rent_enterprise_driver.update_timestamp IS '更新时间戳';
COMMENT ON TABLE rent_enterprise_driver IS '企业驾驶员';

CREATE TABLE rent_v_product (
  key_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  product_name varchar(100) NOT NULL,
  car_model_id int8 NOT NULL,
  day_fee int4,
  pic varchar(100),
  config_params text,
  up_status int4 DEFAULT 0,
  delete_flag int4 NOT NULL DEFAULT 0,
  opt_user_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_v_product PRIMARY KEY (key_id)
);

COMMENT ON COLUMN rent_v_product.key_id IS '主键ID';
COMMENT ON COLUMN rent_v_product.enterprise_id IS '企业ID';
COMMENT ON COLUMN rent_v_product.product_name IS '产品名称';
COMMENT ON COLUMN rent_v_product.car_model_id IS '车辆型号ID';
COMMENT ON COLUMN rent_v_product.day_fee IS '日租费(单位:分)';
COMMENT ON COLUMN rent_v_product.pic IS '产品图片';
COMMENT ON COLUMN rent_v_product.config_params IS '产品特色';
COMMENT ON COLUMN rent_v_product.up_status IS '上架状态(0:待处理,1:已上架,2:已下架)';
COMMENT ON COLUMN rent_v_product.delete_flag IS '删除标志(0:正常,1:删除)';
COMMENT ON COLUMN rent_v_product.opt_user_id IS '操作人ID';
COMMENT ON COLUMN rent_v_product.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_product.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_product IS '企业服务平台产品信息表';

CREATE TABLE rent_dispatch_car (
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
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT rent_dispatch_car_pkey PRIMARY KEY (dispatch_id)
);
COMMENT ON COLUMN rent_dispatch_car.user_id IS '用车人ID';
COMMENT ON COLUMN rent_dispatch_car.user_name IS '用车人名';
COMMENT ON COLUMN rent_dispatch_car.enterprise_id IS '派车企业id';
COMMENT ON COLUMN rent_dispatch_car.start_time IS '租车开始时间';
COMMENT ON COLUMN rent_dispatch_car.end_time IS '租车结束时间';
COMMENT ON COLUMN rent_dispatch_car.start_address IS '取车地址';
COMMENT ON COLUMN rent_dispatch_car.end_address IS '还车地址';
COMMENT ON COLUMN rent_dispatch_car.car_id IS '车辆ID';
COMMENT ON COLUMN rent_dispatch_car.license IS '车牌号';
COMMENT ON COLUMN rent_dispatch_car.status IS '状态0:待派车 1:已派车 2:已接车 3:已还车';
COMMENT ON TABLE rent_dispatch_car IS '派车单';

CREATE TABLE rent_enterprise_wallet (
  enterprise_id int8 NOT NULL,
  money int4 NOT NULL DEFAULT 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_enterprise_wallet PRIMARY KEY (enterprise_id)
);
COMMENT ON COLUMN rent_enterprise_wallet.enterprise_id IS '企业id';
COMMENT ON COLUMN rent_enterprise_wallet.money IS '金额(分)';
COMMENT ON TABLE rent_enterprise_wallet IS '钱包表';

CREATE TABLE rent_enterprise_transfer_apply (
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
  CONSTRAINT pk_rent_enterprise_transfer_apply PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_enterprise_transfer_apply.apply_user_id IS '申请人用户id';
COMMENT ON COLUMN rent_enterprise_transfer_apply.enterprise_id IS '企业id';
COMMENT ON COLUMN rent_enterprise_transfer_apply.money IS '金额(分), 包含了手续费的';
COMMENT ON COLUMN rent_enterprise_transfer_apply.transfer_account IS '提现账号';
COMMENT ON COLUMN rent_enterprise_transfer_apply.transfer_user_name IS '收款人';
COMMENT ON COLUMN rent_enterprise_transfer_apply.commission IS '手续费';
COMMENT ON COLUMN rent_enterprise_transfer_apply.remark IS '备注';
COMMENT ON COLUMN rent_enterprise_transfer_apply.transfer_pic IS '转账凭证';
COMMENT ON COLUMN rent_enterprise_transfer_apply.handle_user_id IS '处理人id';
COMMENT ON COLUMN rent_enterprise_transfer_apply.apply_status IS '审核状态(1:待转账,2:成功,3:失败)';
COMMENT ON COLUMN rent_enterprise_transfer_apply.handle_timestamp IS '处理时间';
COMMENT ON COLUMN rent_enterprise_transfer_apply.update_timestamp IS '修改时间';
COMMENT ON COLUMN rent_enterprise_transfer_apply.insert_timestamp IS '添加时间';
COMMENT ON TABLE rent_enterprise_transfer_apply IS '企业提现申请表';

CREATE TABLE rent_enterprise_wallet_log (
  key_id int8 NOT NULL,
  enterprise_id int8 NOT NULL,
  type int4 NOT NULL default 1,
  money int4 NOT NULL DEFAULT 0,
  remark varchar(500),
  buss_id int8,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_enterprise_wallet_log PRIMARY KEY (key_id)
);
COMMENT ON COLUMN rent_enterprise_wallet_log.enterprise_id IS '企业id';
COMMENT ON COLUMN rent_enterprise_wallet_log.money IS '金额(分),大于0转入小于0转出';
COMMENT ON COLUMN rent_enterprise_wallet_log.type IS '类型1:订单费用 2:提现费用';
COMMENT ON COLUMN rent_enterprise_wallet_log.buss_id IS '业务id,type=1订单id,type=2提现申请id';
COMMENT ON TABLE rent_enterprise_wallet_log IS '企业钱包历史表';
------ 用户 --------------------
CREATE TABLE rent_v_user (
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
  CONSTRAINT rent_v_user_pkey PRIMARY KEY (user_id)
);
COMMENT ON COLUMN rent_v_user.user_id IS '用户ID';
COMMENT ON COLUMN rent_v_user.user_name IS '用户名';
COMMENT ON COLUMN rent_v_user.user_password IS '密码';
COMMENT ON COLUMN rent_v_user.user_mobile IS '手机号';
COMMENT ON COLUMN rent_v_user.user_sex IS '男1;女2';
COMMENT ON COLUMN rent_v_user.change_password_status IS '强制修改密码状态(1:需要修改;0:不需要修改)';
COMMENT ON COLUMN rent_v_user.user_account_status IS '1:启用;0:停用';
COMMENT ON COLUMN rent_v_user.user_nick_name IS '用户名昵称';
COMMENT ON COLUMN rent_v_user.user_path IS '用户头像';
COMMENT ON COLUMN rent_v_user.birthday IS '生日';
COMMENT ON COLUMN rent_v_user.update_timestamp IS '更新时间戳';
COMMENT ON COLUMN rent_v_user.insert_timestamp IS '插入时间戳';
COMMENT ON TABLE rent_v_user IS '租赁用户信息';

CREATE TABLE rent_user_order (
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
  pay_way int4 NOT NULL default 1,
  status int2 NOT NULL DEFAULT 0,
  settle_status int2 NOT NULL DEFAULT 0,
  settle_fee int8 NOT NULL DEFAULT 0,
  update_timestamp timestamp(6) DEFAULT now(),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_user_order PRIMARY KEY (order_id)
);
COMMENT ON COLUMN rent_user_order.status IS '状态1:已支付';
COMMENT ON COLUMN rent_user_order.user_id IS '法人身份证';
COMMENT ON COLUMN rent_user_order.user_id_card IS '法人身份证';
COMMENT ON COLUMN rent_user_order.user_real_name IS '法人姓名';
COMMENT ON COLUMN rent_user_order.product_id IS '产品id';
COMMENT ON COLUMN rent_user_order.product_name IS '产品名称';
COMMENT ON COLUMN rent_user_order.product_pic IS '产品图片';
COMMENT ON COLUMN rent_user_order.start_time IS '开始时间';
COMMENT ON COLUMN rent_user_order.end_time IS '结束时间';
COMMENT ON COLUMN rent_user_order.start_address IS '取车地址';
COMMENT ON COLUMN rent_user_order.start_latitude IS '取车地址纬度';
COMMENT ON COLUMN rent_user_order.start_longitude IS '取车地址经度';
COMMENT ON COLUMN rent_user_order.end_address IS '还车地址';
COMMENT ON COLUMN rent_user_order.end_latitude IS '还车地址纬度';
COMMENT ON COLUMN rent_user_order.end_longitude IS '还车地址经度';
COMMENT ON COLUMN rent_user_order.fee IS '费用(分)';
COMMENT ON COLUMN rent_user_order.car_brand IS '车品牌';
COMMENT ON COLUMN rent_user_order.car_model IS '车型';
COMMENT ON COLUMN rent_user_order.car_type IS '车类型';
COMMENT ON COLUMN rent_user_order.pay_way IS '支付方式1:支付宝 2:微信 3:银行';
COMMENT ON COLUMN rent_user_order.product_detail IS '产品详情';
COMMENT ON COLUMN rent_user_order.status IS '状态0待支付 1:已支付 2:已取消';
COMMENT ON COLUMN rent_user_order.settle_status IS '结算状态0:未结算 1:已结算';
COMMENT ON COLUMN rent_user_order.settle_fee IS '结算费用';
COMMENT ON TABLE rent_user_order IS '租赁订单表';

drop table rent_user_pick_up;
CREATE TABLE rent_user_pick_up (
  dispatch_id int8 NOT NULL,
  user_id int8 NOT NULL,
  car_id int8,
  car_pics text,
  remark varchar(500),
  driver_id int8,
  get_time timestamp(6),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_user_pick_up PRIMARY KEY (dispatch_id)
);
COMMENT ON COLUMN rent_user_pick_up.dispatch_id IS '派车单id';
COMMENT ON COLUMN rent_user_pick_up.user_id IS '用户id';
COMMENT ON COLUMN rent_user_pick_up.car_id IS '车id';
COMMENT ON COLUMN rent_user_pick_up.get_time IS '取车时间';
COMMENT ON COLUMN rent_user_pick_up.driver_id IS '派车司机';
COMMENT ON COLUMN rent_user_pick_up.car_pics IS '取车时的车辆图片信息';
COMMENT ON COLUMN rent_user_pick_up.remark IS '备注';
COMMENT ON TABLE rent_user_pick_up IS '取车信息表';

drop table rent_user_car_back;
CREATE TABLE rent_user_car_back (
  dispatch_id int8 NOT NULL,
  user_id int8 NOT NULL,
  car_id int8 NOT NULL,
  car_pics text,
  remark varchar(500),
  driver_id int8,
  extra_fee int8 default 0,
  back_time timestamp(6),
  insert_timestamp timestamp(6) DEFAULT now(),
  CONSTRAINT pk_rent_user_car_back PRIMARY KEY (dispatch_id)
);
COMMENT ON COLUMN rent_user_car_back.dispatch_id IS '派车单id';
COMMENT ON COLUMN rent_user_car_back.user_id IS '用户id';
COMMENT ON COLUMN rent_user_car_back.car_id IS '车id';
COMMENT ON COLUMN rent_user_car_back.driver_id IS '接收司机';
COMMENT ON COLUMN rent_user_car_back.back_time IS '还车时间';
COMMENT ON COLUMN rent_user_car_back.car_pics IS '取车时的车辆图片信息';
COMMENT ON COLUMN rent_user_car_back.remark IS '备注';
COMMENT ON COLUMN rent_user_car_back.extra_fee IS '额外费用(分)';
COMMENT ON TABLE rent_user_car_back IS '还车信息表';

