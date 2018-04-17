package com.qi.client;

import com.qi.chat.common.message.Message;
import com.qi.chat.common.message.TextMessage;
import com.qi.chat.common.utils.ArrayUtil;

import java.io.IOException;
import java.net.*;

/**
 * Creator  liuqi
 * Data     2018/4/16
 * Class    PACKAGE_NAME.com.qi.client.ClientManager
 */
public class ClientManager {

    private DatagramSocket socket;
    private String serverHost;
    private int port;
    private TextMessage message;

    public void connect(String serverHost, int port) throws Exception {
        socket = new DatagramSocket();
        message = new TextMessage();
        socket.connect(InetAddress.getByName(message.getDestination()), message.getDesPort());
        message.setHeader(InetAddress.getLocalHost().getHostName(), socket.getPort(), serverHost, port);
    }

    public byte[] receive() throws Exception {
        byte[] data = null;
        byte[] buff = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buff, 0, buff.length);
        while (datagramPacket.getLength() > 0) {
            socket.receive(datagramPacket);
            data = ArrayUtil.createBytes(buff, datagramPacket.getLength());
        }
        return data;
    }

    public void sendMsg(String msg) throws Exception {
        message.setMsg(msg);
        byte[] data = ArrayUtil.combineArray(message.getHeader(), msg.getBytes());
        DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length);
        socket.send(datagramPacket);
    }

}
