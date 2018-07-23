package com.qi.chat.common.heartbeat;
/**
 * Creator  liuqi
 * Data     2018/7/23
 * Class    heartbeat.Message
 * 心跳消息类
 */
public class Message {
    /**
     * 用户id
     */
    public String uid;
    /**
     * 用户状态
     */
    public int status;

    public interface Status{
        int exit = 0;
        int logining = 1;
        int leave = 2;
    }
}
