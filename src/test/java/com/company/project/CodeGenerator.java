package com.company.project;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

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

    private static final String BASE_PACKAGE = "com.company.project";
    private static final String AUTHOR = "libaogang"; //作者

    private static final boolean OVERRIDE = true; //是否覆盖已生成文件
    private static final String MODULE_NAME = "sys";  //模块名
    private static final String TABLE_PREFIX = "sys_";  //表前缀
    private static final String TABLE_NAME = "sys_user";  //表名,多张表用英文逗号隔开。

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setFileOverride(OVERRIDE); //是否覆盖已生成的文件
        gc.setBaseResultMap(true); //mapper.xml中添加baseResultMap
        gc.setBaseColumnList(true); //mapper.xml中添加baseColumList
        gc.setServiceName("%sService");  //默认生成的service接口名有I前缀，去掉I前缀。%s为对应实体名
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        autoGenerator.setGlobalConfig(gc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController"); //父controller
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");//父entity
        //strategy.setSuperEntityColumns("id"); // 写于父类中的公共字段

        strategy.setInclude(TABLE_NAME.split(","));
        strategy.setControllerMappingHyphenStyle(true);  //驼峰转连字符
        strategy.setTablePrefix(TABLE_PREFIX);  //表前缀，生成的文件将去掉前缀
        autoGenerator.setStrategy(strategy);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(JDBC_URL);
        // dsc.setSchemaName("public");
        dsc.setDriverName(JDBC_DIVER_CLASS_NAME);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        autoGenerator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        if (StringUtils.isNotEmpty(MODULE_NAME)) {
            pc.setModuleName(MODULE_NAME);
        }
        pc.setParent(BASE_PACKAGE);
        autoGenerator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        //该模板文件位于mybatis-plus-generator jar包中
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + MODULE_NAME
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }
}