package com.qi.chat.server.heartbeat;

import java.util.Observable;

/**
 * Creator  liuqi
 * Data     2018/7/23
 * Class    com.qi.chat.server.heartbeat.HeartBeatReceiver
 * <p>心跳接收器</p>
 */
public abstract class HeartBeatReceiver extends Observable{
    /**
     * 启动接收器
     */
    public abstract void start();

    /**
     * 销毁接收器
     */
    public abstract void destroy();
}
