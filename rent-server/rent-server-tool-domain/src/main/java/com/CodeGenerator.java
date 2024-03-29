package com; /**
 * @author yanxiao
 * @date 2019/1/712:27
 * @Description: TODO
 */

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:shenxi
 * @Des:
 * @Date:2019/1/7
 */
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static void main(String[] args) {

        /**
         *  平台
         */
//        String tables[]={
//            "rent_platform_user"
//                ,"rent_v_enterprise"
//                ,"rent_car_type"
//                ,"rent_car_model"
//                ,"rent_car_brand"
//                ,"rent_v_role"
//                ,"rent_v_role_menu"
//                ,"rent_v_user_role"
//                ,"rent_v_menu"
//                ,"rent_withdraw_rule"
//                ,"rent_platform_log"
//        };

        /**
         *  商家
         */
//        String tables[]={
//                "rent_enterprise_station"
//                ,"rent_enterprise_car"
//                ,"rent_v_product"
//                ,"rent_dispatch_car"
//                ,"rent_enterprise_wallet"
//                ,"rent_enterprise_transfer_apply"
//                ,"rent_enterprise_wallet_log"
//        };
        /**
         *  用户
         */
//        String tables[]={
//                "rent_v_user"
//                ,"rent_user_order"
//                ,"rent_user_pick_up"
//                ,"rent_user_car_back"
//        };
     //   String tables[]={"rent_v_manager"};
//生成的项目路径
        String projectPath = System.getProperty("user.dir") + "/rent-sys-server";
        //源码包
        String outPutDir="/src/main/java";
        String packageName = "platform";
        //xml生成文件夹
        String xmlPath=outPutDir+"/com/yolo/hw/user/domain/auto/" +packageName+ "/mapper/";
        //类的包名
        String parentPage="com.yolo.hw.user";

        //类的包名
        String entity ="domain.auto."+packageName+".entity";

        //类的包名
        String mapper ="domain.auto." + packageName + ".mapper";

        //类的包名
        String controller="controller." + packageName;
        //类的包名
        String serviceName ="service";
        //类的包名
        String serviceImpName ="service.impl";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();

//        String projectPath = "D:/auto";
        gc.setOutputDir(projectPath + outPutDir);
        gc.setAuthor("shenxi");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setActiveRecord(true);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("cqgcgl");
        dsc.setPassword("cqgcgl");
        dsc.setUrl("jdbc:postgresql://192.168.18.105:35432/shenxi");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();

        //pc.setModuleName(scanner("模块名"));

        pc.setParent(parentPage);
        pc.setEntity(entity);
        pc.setMapper(mapper);
        pc.setServiceImpl(serviceImpName);
        pc.setController(controller);
        pc.setService(serviceName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return projectPath + xmlPath + tableInfo.getEntityName() + ".xml";

            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
//         templateConfig.setEntity();
      //  templateConfig.setService(null);
     //   templateConfig.setController(null);
      //  templateConfig.setServiceImpl(null);

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setEntityLombokModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
