package com.weina.annotation;

import java.lang.annotation.*;

/**
 * @Description: 这个注解用于数据库表对应的实体类上，
 * 主要用在用JdbcTemplate的时候获取类对应的表明
 * @Author: mayc
 * @Date: 2019/02/13 10:35
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WnTable {
    String tableName();
}
