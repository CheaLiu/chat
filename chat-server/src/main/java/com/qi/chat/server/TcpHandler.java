package com.qi.chat.server;

import java.net.Socket;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.server.TcpHandler
 */
public class TcpHandler implements Runnable {

    private final Socket socket;

    public TcpHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
