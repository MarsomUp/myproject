package com.weina.myproject.mapper;

import com.weina.myproject.entity.MItem;

public interface MItemMapper {
    int insert(MItem record);

    int insertSelective(MItem record);
}