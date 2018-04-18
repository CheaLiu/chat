package com.qi.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creator  liuqi
 * Data     2018/4/16
 * Class    com.qi.server.ServerManager
 */
public class ServerManager {

    private ServerSocket serverSocket;
    private boolean isClosed;
    private ExecutorService executorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());

    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        TcpTask tcpTask = new TcpTask(serverSocket);
        executorService.execute(tcpTask);
    }

    public void stopServer() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
        Socket socket;
        while ((socket = SocketPool.getSocketPool().poll()) != null) {
            socket.close();
        }
    }
}
