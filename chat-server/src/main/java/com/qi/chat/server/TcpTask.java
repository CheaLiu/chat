package com.qi.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.server.TcpTask
 */
public class TcpTask implements Runnable {

    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());

    public TcpTask(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                TcpHandler tcpHandler = new TcpHandler(socket);
                executorService.execute(tcpHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
