package com.weina.mq;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/18 16:59
 */
public enum MqTag {
    normal("normal"),
    system("system");

    public String type;

    MqTag(String type) {
        this.type = type;
    }
}
