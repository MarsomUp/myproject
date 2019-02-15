package com.weina.myproject.entity;

import com.weina.util.IDGen;
import lombok.Data;
import org.springframework.util.IdGenerator;

import java.io.Serializable;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/12 09:37
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7909101184583037085L;

    private Long id;
    private Long createTime;
    private Long updateTime;

    public void setNewId() {
        this.id = IDGen.getId();
    }

    public void setIdIfNew() {
        if (this.id == null) {
            this.id = IDGen.getId();
        }
    }
}
