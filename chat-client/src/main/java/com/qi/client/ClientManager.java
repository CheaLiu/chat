package com.qi.client;

import com.qi.chat.common.message.Message;
import com.qi.chat.common.message.MessageParse;
import com.qi.chat.common.message.TextMessage;
import com.qi.chat.common.utils.ArrayUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import java.io.ByteArrayInputStream;
import java.net.*;

/**
 * Creator  liuqi
 * Data     2018/4/16
 * Class    PACKAGE_NAME.com.qi.client.ClientManager
 */
public class ClientManager {

    private DatagramSocket socket;

    public ClientManager() throws SocketException {
        this(0);
    }

    public ClientManager(int localPort) throws SocketException {
        socket = new DatagramSocket(localPort);
    }

    public void connect(String remoteHost, int remotePort) throws Exception {
        socket.connect(InetAddress.getByName(remoteHost), remotePort);
    }

    public Observable<Message> receive() {
        return Observable.create((ObservableEmitter<Message> emitter) -> {
            byte[] buff = new byte[1024];
            byte[] data = new byte[0];
            do {
                DatagramPacket datagramPacket = new DatagramPacket(buff, 0, buff.length);
                socket.receive(datagramPacket);
                Message.MessageType messageType = null;
                int bodySize = 0;
                if (datagramPacket.getLength() > 0) {
                    if (new String(buff, 0, datagramPacket.getLength()).equals(""))
                        data = ArrayUtil.combineArray(data, ArrayUtil.subBytes(buff, 0, datagramPacket.getLength()));
                    if (data.length == Message.SOURCE_LENGHT + Message.PORT_LENGTH + Message.DESTINATION_LENGTH + Message.PORT_LENGTH + Message.MESSAGE_TYPE_LENGTH) {
                        messageType = MessageParse.parseType(data);
                    }
                    if (messageType == Message.MessageType.TEXT && data.length == MessageParse.parseHeaderSize(data)) {
                        bodySize = MessageParse.parseBodySize(data);
                    }else if ()
                    if (datagramPacket.getLength() < buff.length) {
                        emitter.onNext(MessageParse.parse(new ByteArrayInputStream(data)));
                    }
                }
            } while (true);
        });

    }

    public void sendMsg(String msg) throws Exception {
        TextMessage message = new TextMessage();
        message.setHeader(InetAddress.getLocalHost().getHostName(), socket.getLocalPort(), socket.getInetAddress().getHostName(), socket.getPort());
        message.setBody(msg.getBytes());
        byte[] data = message.getMessage();
        DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length);
        socket.send(datagramPacket);
    }

    public String getRemoteHostName() {
        return socket.getInetAddress().getHostName();
    }

    public int getRemotePort() {
        return socket.getPort();
    }

    public String getLocalHostName() {
        return socket.getLocalAddress().getHostName();
    }

    public int getLocalPort() {
        return socket.getLocalPort();
    }

}
