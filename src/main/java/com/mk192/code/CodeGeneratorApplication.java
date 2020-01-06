package com.mk192.code;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
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
import java.util.Scanner;

public class CodeGeneratorApplication {

    // main函数
    public static void main(String[] args) {

        String author = "mk192";
        String userName = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=utf8&useSSL=false";
        String parent = "com.mk192.code";

        AutoGenerator ag = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 得到当前项目的路径
        String oPath = System.getProperty("user.dir");
        // 生成文件输出根目录
        gc.setOutputDir(oPath + "/src/main/java");
        // 生成文件后不打开文件夹
        gc.setOpen(false);
        // 文件覆盖
        gc.setFileOverride(true);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // 开启swagger2模式
        gc.setSwagger2(true);
        // 作者
        gc.setAuthor(author);

        // 自定义文件命名，%s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        ag.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(userName);
        dsc.setPassword(password);
        dsc.setUrl(url);
        ag.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("entity");
        pc.setXml("xml");
        pc.setModuleName(scanner("模块名"));
        pc.setParent(parent);
        ag.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 可设置是否生成全部表
        String scanner = scanner("是否生成全部表，yes是，no否");
        if (StringUtils.isNotBlank(scanner) && "yes".equals(scanner)) {
            strategy.setExclude();
        } else {
            strategy.setInclude(scanner());
        }
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
        ag.setStrategy(strategy);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return oPath + "/src/main/resources/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        ag.setCfg(cfg);
        ag.setTemplate(new TemplateConfig().setXml(null));

        ag.setTemplateEngine(new FreemarkerTemplateEngine());
        // 执行生成
        ag.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        System.out.println("请输入" + tip + "：");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * <p>
     * 读取控制台输入的表名
     * </p>
     */
    private static String[] scanner() {
        System.out.println("请输入表名(多个表名以逗号','隔开)：");
        Scanner scanner = new Scanner(System.in);
        String[] tableNames = scanner.next().split(",");
        scanner.close();
        if (tableNames.length <= 0) {
            throw new MybatisPlusException("请输入表名");
        }
        return tableNames;
    }

}
