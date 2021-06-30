package com.banma.bill.mp;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author binglang
 */
@Data
@Accessors(chain = true)
public class MpConfig {

    private String projectPath;

    private String packageName;

    private String[] tables;

    private String dbName;

    private String url;

    private String driverName = "com.mysql.cj.jdbc.Driver";

    private String user = "root";

    private String pwd = "root2222";

    private boolean fileOverride = true;

    /**
     * 时间类型对应策略，默认 java8 新的时间类型
     */
    private DateType dateType = DateType.TIME_PACK;

    public MpConfig setTables(String... tables) {
        this.tables = tables;
        return this;
    }

    public MpConfig setDbName(String dbName) {
        this.dbName = dbName;
        this.url = String.format("jdbc:mysql://127.0.0.1:3306/%s?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai", dbName);
        return this;
    }
}
