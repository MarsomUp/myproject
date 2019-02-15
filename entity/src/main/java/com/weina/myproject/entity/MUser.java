package com.weina.myproject.entity;

import com.weina.annotation.WnColumn;
import com.weina.annotation.WnTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2019/02/13
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@WnTable(tableName = "m_user")
public class MUser extends BaseEntity {
    @WnColumn(fieldName = "oppo")
    private String oppo;

    @WnColumn(fieldName = "vivo")
    private String vivo;

    @WnColumn(fieldName = "huawei")
    private String huawei;

    @WnColumn(fieldName = "is_delete")
    private Boolean isDelete;
}