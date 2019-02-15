package com.weina.annotation;

import java.lang.annotation.*;

/**
 * @Description: 此注解用于数据库表对应的实体类的字段上，
 * 主要在用jdbcTemplate查询的时候使用
 * @Author: mayc
 * @Date: 2019/02/13 10:37
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WnColumn {
    String fieldName();
}
