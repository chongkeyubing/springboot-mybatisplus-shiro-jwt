package com.company.project;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * MybatisPlus代码生成器
 *
 * @author libaogang
 * @since 2019-08-14 9:22
 */
public class CodeGenerator {
    //jdbc配置
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/myadmin?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String BASE_PACKAGE = "com.company.project.modules";
    private static final boolean OVERRIDE = false; //是否覆盖已生成文件
    private static final String AUTHOR = "libaogang"; //作者
    private static final String MODULE_NAME = "sys";  //模块名
    private static final String TABLE_PREFIX = "sys_";  //表前缀，生成的实体类会去掉前缀

    public static void main(String[] args) {
        generate("sys_dict","sys_dict_item");  //支持多表
    }

    private static void generate(String... tableName) {

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setUrl(JDBC_URL)
                .setDriverName(JDBC_DIVER_CLASS_NAME)
                .setUsername(JDBC_USERNAME)
                .setPassword(JDBC_PASSWORD);

        //1. 全局配置
        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                .setOpen(false)
                .setAuthor(AUTHOR)
                .setFileOverride(OVERRIDE)  //是否覆盖已生成的文件
                .setDateType(DateType.ONLY_DATE)  // 时间类型使用 java.util.date
                .setBaseResultMap(true) //mapper.xml中添加baseResultMap
                .setBaseColumnList(true) //mapper.xml中添加baseColumList
                .setServiceName("%sService")  //默认生成的service接口名有I前缀，去掉I前缀。%s为对应实体名
                .setEntityName("%sEntity");
                 //.setSwagger2(true)  // 实体属性 Swagger2 注解


        //2. 策略配置
        StrategyConfig strategyConfig = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setInclude(tableName)
                .setControllerMappingHyphenStyle(true)  //驼峰转连字符
                .setTablePrefix(TABLE_PREFIX)  //表前缀，生成的文件将去掉前缀
                //.setSuperControllerClass("com.baomidou.ant.common.BaseController") //父controller
                //.setSuperEntityClass("com.baomidou.ant.common.BaseEntity") //父entity
                //.setSuperEntityColumns("id") // 写于父类中的公共字段;
                .setLogicDeleteFieldName("is_deleted")   //逻辑删除属性名
                .setTableFillList(Arrays.asList(  // 自动填充字段
                        new TableFill("create_user_id", FieldFill.INSERT),
                        new TableFill("update_user_id", FieldFill.UPDATE)
                ));

        //3. 包配置
        PackageConfig packageConfig = new PackageConfig()
                .setParent(BASE_PACKAGE)  //基础包路径
                .setModuleName(StringUtils.isNotEmpty(MODULE_NAME) ? MODULE_NAME : null);  //模块名

        //4. 模板引擎
        FreemarkerTemplateEngine freemarkerTemplateEngine = new FreemarkerTemplateEngine();

        //5. 自定义模板文件
        TemplateConfig templateConfig = new TemplateConfig()
                .setXml(null);
        // .setController("...");    //自定义模板文件
        // .setEntity("...");
        // .setMapper("...");
        // .setXml("...");
        // .setService("...");
        // .setServiceImpl("...");

        //6. 模板自定义属性注入，自定义输出文件
        InjectionConfig injectionConfig = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", "");
                this.setMap(map);
            }
        }.setFileOutConfigList(
                Arrays.asList(
                        new FileOutConfig("/templates/mapper.xml.ftl") {  //该mapper模板文件位于mybatis-plus-generator jar包中
                            @Override
                            public String outputFile(TableInfo tableInfo) {
                                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                                return System.getProperty("user.dir") + "/src/main/resources/mapper/" + MODULE_NAME
//                                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                                return System.getProperty("user.dir") + "/src/main/java/" + BASE_PACKAGE.replace(".", "/")
                                        + "/" + MODULE_NAME + "/mapper/" + tableInfo.getEntityName().replace("Entity", "")
                                        + "Mapper" + StringPool.DOT_XML;
                            }
                        }
                )
        );

        // 整合配置，生成代码
        new AutoGenerator()
                .setDataSource(dataSourceConfig)
                .setGlobalConfig(globalConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig)
                .setTemplateEngine(freemarkerTemplateEngine)
                .setTemplate(templateConfig)
                .setCfg(injectionConfig)
                .execute();
    }
}