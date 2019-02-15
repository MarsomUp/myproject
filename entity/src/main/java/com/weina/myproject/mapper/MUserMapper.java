package com.weina.myproject.mapper;

import com.weina.myproject.entity.MUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MUserMapper {
    int insert(MUser record);

    int insertSelective(MUser record);
}