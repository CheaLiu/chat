package com.qi.client;

import com.qi.chat.common.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.SocketChannel;

/**
 * Creator  liuqi
 * Data     2018/4/16
 * Class    PACKAGE_NAME.com.qi.client.ClientManager
 */
public class ClientManager {


    private DatagramSocket socket;
    private String serverHost;
    private int port;
    private Message.Builder messageBuilder;

    public void connect(String serverHost, int port) throws Exception {
        socket = new DatagramSocket();
        messageBuilder = new Message.Builder()
                .setSource(Inet4Address.getLocalHost().getHostAddress() + ":" + socket.getPort())
                .setDestination(serverHost + ":" + port);
    }

    public void send(String msg) throws Exception {
        Message message = messageBuilder.setBody(msg).build();

        DatagramPacket datagramPacket = new DatagramPacket();
    }

}
