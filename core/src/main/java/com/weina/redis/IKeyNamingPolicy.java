package com.weina.redis;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/14 10:14
 */
public interface IKeyNamingPolicy {
    String getKeyName(Object key);

    static final IKeyNamingPolicy defaultKeyNamingPolicy = new IKeyNamingPolicy() {
        public String getKeyName(Object key) {
            return key.toString();
        }
    };
}
