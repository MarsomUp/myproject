package com.weina.myproject.entity;

import com.weina.annotation.WnColumn;
import com.weina.annotation.WnTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2019/02/18
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@WnTable(tableName = "m_item")
public class MItem extends BaseEntity {
    @WnColumn(fieldName = "name")
    private String name;

    @WnColumn(fieldName = "length")
    private Integer length;

    @WnColumn(fieldName = "description")
    private String description;
}