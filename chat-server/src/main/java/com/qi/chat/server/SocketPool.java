package com.qi.chat.server;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qi on 2018/4/16.
 */
public class SocketPool {

    private LinkedBlockingQueue<Socket> pool = new LinkedBlockingQueue<>();

    public void add(Socket socket){
        pool.add(socket)
    }

}
