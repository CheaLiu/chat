package com.qi.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Creator  liuqi
 * Data     2018/4/16
 * Class    com.qi.server.ServerManager
 */
public class ServerManager {

    private ServerSocket serverSocket;
    private boolean isClosed;

    public void startServer(int port) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(15 * 1000);
        while (true) {
            Socket socket = serverSocket.accept();
            executorService.execute(new SocketTask(this, socket));
        }
    }

    public void stopServer() throws IOException {
        isClosed = true;
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    public static class SocketTask implements Runnable {

        private final ServerManager serverManager;
        private Socket socket;

        public SocketTask(ServerManager serverManager, Socket socket) {
            this.serverManager = serverManager;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                if (serverManager.isClosed) {
                    shutdown();
                } else {
                    InputStream inputStream = socket.getInputStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                shutdown();
            }
        }

        private void shutdown() {
            if (socket != null) {
                try {
                    if (!socket.isOutputShutdown())
                        socket.shutdownOutput();
                    if (!socket.isInputShutdown())
                        socket.shutdownInput();
                    if (!socket.isClosed())
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
