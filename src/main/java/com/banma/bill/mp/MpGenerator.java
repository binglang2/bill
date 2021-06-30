package com.banma.bill.mp;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * @author binglang
 * @since 2019/6/26
 */
@RequiredArgsConstructor
public class MpGenerator {

    private final MpConfig mpConfig;

    public void execute() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        if (!StringUtils.isBlank(mpConfig.getProjectPath())) {
            projectPath = projectPath.concat(mpConfig.getProjectPath());
        }
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(System.getProperty("user.name"));
        gc.setOpen(false);
        // 是否覆盖原文件
        gc.setFileOverride(mpConfig.isFileOverride());
        // 实体属性 Swagger2 注解
        gc.setSwagger2(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setEntityName("%s");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sRepository");
        // 默认使用 java 8 新的时间类型
        gc.setDateType(mpConfig.getDateType());
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(mpConfig.getUrl());
        dsc.setDriverName(mpConfig.getDriverName());
        dsc.setUsername(mpConfig.getUser());
        dsc.setPassword(mpConfig.getPwd());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(mpConfig.getPackageName());
        pc.setEntity("repository.entity");
        pc.setService("service");
        pc.setMapper("repository.mapper");
        pc.setServiceImpl("repository");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        String finalProjectPath = projectPath;
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return finalProjectPath + "/src/main/resources/mybatis/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，可以不带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("/templates/entity.java.vm");
        templateConfig.setMapper("/templates/mapper.java.vm");
        templateConfig.setXml(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl("/templates/repository.java.vm");
        templateConfig.setController(null);

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.banma.bill.mp.BaseEntity");
        strategy.setSuperMapperClass("com.banma.bill.mp.BaseExtendMapper");
        strategy.setSuperServiceClass("com.banma.bill.mp.BaseRepository");
        strategy.setEntityLombokModel(true);
        // 表名
        strategy.setInclude(mpConfig.getTables());
        strategy.setSuperEntityColumns("id", "deleted", "create_time", "modified_time");
        strategy.setLogicDeleteFieldName("deleted");
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setFieldPrefix("is_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

}
