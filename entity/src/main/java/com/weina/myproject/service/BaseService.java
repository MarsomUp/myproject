package com.weina.myproject.service;

import com.weina.annotation.WnColumn;
import com.weina.annotation.WnTable;
import com.weina.exception.BusinessException;
import com.weina.myproject.entity.MUser;
import com.weina.util.CamelUnderline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/13 10:08
 */
@Component
public class BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
    private static final String ENTITY_SUPER_CLASS_NAME = "com.weina.myproject.entity.BaseEntity";

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public <T> T update(T object) {
        Class superClazz = object.getClass().getSuperclass();
        if (!ENTITY_SUPER_CLASS_NAME.equals(superClazz.getName())) {
            throw new BusinessException(1000, "传入的实体必须继承自com.weina.myproject.entity.BaseEntity.java类");
        }
        // 获取类对应的表名
        WnTable wnTable = object.getClass().getAnnotation(WnTable.class);
        String tabName = wnTable.tableName();
        List<Object> params = new ArrayList<>(10);
        List<Object> ids = new ArrayList<>(10);
        Object checkId = null;
        StringBuilder sql = new StringBuilder("");
        StringBuilder where = new StringBuilder("");
        sql.append(" update ").append(tabName).append(" set ");
        where.append(" where ");
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            WnColumn wnColumn = field.getAnnotation(WnColumn.class);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                LOGGER.error("更新"+object.getClass().getName()+"数据时发生异常");
                throw new BusinessException(1001, "更新数据异常！");
            }
            if (fieldValue != null) {
                if (params.size() > 0) {
                    sql.append(",");
                }
                if (wnColumn != null) {
                    // 如果字段有WnColumn注解，直接使用
                    sql.append(wnColumn.fieldName()).append("= ?");
                } else {
                    // 否则，将驼峰字段改为下划线
                    sql.append(CamelUnderline.camelToUnderline(field.getName())).append("= ?");
                }
                params.add(fieldValue);
            }
        }
        // 父类字段
        Field[] supFields = object.getClass().getSuperclass().getDeclaredFields();
        for (Field field : supFields) {
            if (field.getName().equals("serialVersionUID")) continue;
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                LOGGER.error("更新数据异常");
                throw new BusinessException(1001, "更新数据异常");
            }
            if (fieldValue != null) {
                if (field.getName().equals("id")) {
                    where.append("id = ?");
                    ids.add(fieldValue);
                    checkId = fieldValue;
                } else {
                    sql.append(CamelUnderline.camelToUnderline(field.getName())).append("= ?");
                    if (params.size() > 0) {
                        sql.append(",");
                    }
                    params.add(fieldValue);
                }
            }
        }
        if (checkId == null) {
            throw new BusinessException(1000, "更新的数据ID不能为null");
        }
        params.addAll(ids);
        sql.append(where);
        jdbcTemplate.update(sql.toString(), params.toArray());
        return object;
    }

    /*public static void main(String[] args) {
        MUser mUser = new MUser();
        mUser.setId(1234L);
        mUser.setHuawei("p20");
        mUser.setVivo("x21");
        mUser.setOppo("findx");
        mUser.setCreateTime(System.currentTimeMillis());
        mUser.setUpdateTime(System.currentTimeMillis());
        mUser.setDeleted(false);

        Field[] fields = mUser.getClass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(mUser);
                System.out.println(field.getName()+"="+o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }*/
}
