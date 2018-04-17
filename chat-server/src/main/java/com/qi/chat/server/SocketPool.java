package com.qi.chat.server;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.server.SocketPool
 */
public class SocketPool {

    private LinkedBlockingQueue<Socket> queue = new LinkedBlockingQueue<>();

    private final static SocketPool pool = new SocketPool();

    private SocketPool() {
    }

    ;

    public static SocketPool getSocketPool() {
        return pool;
    }

    public void put(Socket socket) throws InterruptedException {
        queue.put(socket);
    }

    public Socket poll() {
        return queue.poll();
    }
}

