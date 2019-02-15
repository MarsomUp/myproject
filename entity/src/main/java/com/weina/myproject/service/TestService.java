package com.weina.myproject.service;

import com.weina.exception.BusinessException;
import com.weina.myproject.entity.MUser;
import com.weina.myproject.mapper.MUserMapper;
import com.weina.web.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/12 17:04
 */
@Service
@Transactional
public class TestService extends BaseService {

    @Autowired
    private MUserMapper mUserMapper;

    public void addMUser(MUser mUser) {
        mUser.setNewId();
        mUser.setCreateTime(System.currentTimeMillis());
        mUser.setUpdateTime(System.currentTimeMillis());
        mUserMapper.insert(mUser);
    }

    public void testException() {
        throw new BusinessException(ResultCodeEnum.DATABASE_ERROR.code, "从Serive中的报错！");
    }
}
