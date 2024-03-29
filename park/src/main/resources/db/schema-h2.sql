-- DROP TABLE IF EXISTS user;


-- CREATE SCHEMA IF NOT EXISTS `tdatabase` DEFAULT CHARACTER SET utf8 ;
-- USE `tdatabase`;

-- DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user
(
	id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS book;
CREATE TABLE IF NOT EXISTS book
(
    id INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名',
    author VARCHAR(30) NULL DEFAULT NULL COMMENT '作者',
    description VARCHAR(500) NULL DEFAULT NULL COMMENT '描述',
    ctime datetime default now() COMMENT '更新时间',
    utime datetime default now() COMMENT '更新时间',
    create_time datetime default now() COMMENT '更新时间',
    update_time datetime default now() COMMENT '更新时间',
    PRIMARY KEY (id)
);
