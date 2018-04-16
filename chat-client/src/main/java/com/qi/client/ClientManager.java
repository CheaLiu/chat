package com.qi.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * Creator  liuqi
 * Data     2018/4/16
 * Class    PACKAGE_NAME.com.qi.client.ClientManager
 */
public class ClientManager {

    private Socket socket;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socket.setSoTimeout(15 * 1000);
        SocketChannel channel = socket.getChannel();
    }

    public void read() throws IOException {
        InputStream inputStream = socket.getInputStream();
    }

    public void write() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
    }

    public void close() {
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
