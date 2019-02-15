package com.weina.redis;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/14 09:56
 */
public interface ISerializer {

    byte[] keyToBytes(String key);
    String keyFromBytes(byte[] bytes);

    byte[] fieldToBytes(Object field);
    Object fieldFromBytes(byte[] bytes);

    byte[] valueToBytes(Object value);
    Object valueFromBytes(byte[] bytes);
}
