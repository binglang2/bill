drop table if EXISTS transaction_book;
CREATE TABLE `transaction_book`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'user_id',
    `name`          varchar(50)         NOT NULL DEFAULT '' COMMENT '账本名称',
    `status`        tinyint(2) unsigned NOT NULL DEFAULT '0' comment '1-默认账本',
    `deleted`       tinyint(2) unsigned NOT NULL DEFAULT '0',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_userid` (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户账本表';

drop table if EXISTS transaction_category;
CREATE TABLE `transaction_category`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '用户 id',
    `book_id`       bigint(20) unsigned NOT NULL default '0' COMMENT '账本 id',
    `name`          varchar(50)         NOT NULL DEFAULT '' COMMENT '交易类别名称',
    `trans_type`    tinyint(2)          NOT NULL DEFAULT '1' COMMENT '1-支出 2-收入',
    `deleted`       tinyint(2) unsigned NOT NULL DEFAULT '0',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_userid` (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户自定义交易类别表';

drop table if EXISTS transaction_fixed;
CREATE TABLE `transaction_fixed`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '用户 id',
    `book_id`       bigint(20) unsigned NOT NULL default '0' COMMENT '账本 id',
    `amount`        decimal(16, 2)      NOT NULL DEFAULT '0.00' COMMENT '金额',
    `day`           int(2) unsigned     NOT NULL DEFAULT '0' COMMENT '0-默认，1～28',
    `trans_way`     TINYINT(4)          NOT NULL DEFAULT '0' COMMENT '交易方式 0-未知，1-支付宝，2-微信，3-现金，4-pos刷卡，5-apple pay，6-其他',
    `trans_type`    tinyint(2)          NOT NULL DEFAULT '1' COMMENT '1-支出 2-收入',
    `category_id`   bigint(20)          NOT NULL DEFAULT '0' COMMENT '交易类别 id',
    `frequency`     tinyint(4)          NOT NULL DEFAULT '0' COMMENT '0-每月固定，1-每天固定',
    `remark`        varchar(200)        NOT NULL DEFAULT '' COMMENT '备注',
    `enabled`       tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用',
    `deleted`       tinyint(2) unsigned NOT NULL DEFAULT '0',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_userid` (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='固定交易信息表';

drop table if EXISTS transaction_flow;
CREATE TABLE `transaction_flow`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) unsigned NOT NULL default '0' COMMENT '用户 id',
    `book_id`       bigint(20) unsigned NOT NULL default '0' COMMENT '账本 id',
    `amount`        decimal(16, 2)      NOT NULL DEFAULT '0.00' COMMENT '金额',
    `trans_date`    date                NOT NULL COMMENT '交易日期',
    `trans_way`     TINYINT(4)          NOT NULL DEFAULT '0' COMMENT '交易方式 0-未知，1-支付宝，2-微信，3-现金，4-pos刷卡，5-apple pay，6-其他',
    `trans_type`    tinyint(2)          NOT NULL DEFAULT '1' COMMENT '1-支出 2-收入',
    `category_id`   bigint(20)          NOT NULL DEFAULT '0' COMMENT '交易类别 id',
    `remark`        varchar(200)        NOT NULL DEFAULT '' COMMENT '备注',
    `fixed_id`      bigint(20) unsigned NOT NULL default '0' COMMENT '默认-0，transaction_fixed id',
    `deleted`       tinyint(2) unsigned NOT NULL DEFAULT '0',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_userid` (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='交易流水表';

drop table if EXISTS user_wx;
CREATE TABLE `user_wx`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `open_id`       varchar(50)         NOT NULL DEFAULT '' COMMENT '微信唯一id',
    `nick_name`     varchar(50)         NOT NULL DEFAULT '' COMMENT '昵称',
    `sex`           tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0 未知， 1 男， 2女',
    `province`      varchar(20)         NOT NULL DEFAULT '' COMMENT '省份',
    `city`          varchar(20)         NOT NULL DEFAULT '' COMMENT '城市',
    `country`       varchar(20)         NOT NULL DEFAULT '' COMMENT '国家',
    `avatar`        varchar(200)        NOT NULL DEFAULT '' COMMENT '头像',
    `privilege`     varchar(500)        NOT NULL DEFAULT '',
    `session_key`   varchar(500)        NOT NULL DEFAULT '',
    `union_id`      varchar(100)        NOT NULL DEFAULT '',
    `deleted`       tinyint(2) unsigned NOT NULL DEFAULT '0',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='微信用户表';
create unique index uk_openid on user_wx (open_id);

drop table if EXISTS user_feedback;
CREATE TABLE `user_feedback`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'user_id',
    `content`       varchar(1000)       NOT NULL DEFAULT '' COMMENT '反馈内容',
    `contact`       varchar(200)        NOT NULL DEFAULT '' comment '联系方式',
    `deleted`       tinyint(2) unsigned NOT NULL DEFAULT '0',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户反馈意见表';