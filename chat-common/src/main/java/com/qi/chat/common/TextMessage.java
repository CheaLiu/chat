package com.qi.chat.common;

import com.qi.chat.common.packages.Array2NumberConvertor;
import com.qi.chat.common.packages.TextUtil;

import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by qi on 2018/4/16.
 */
public class TextMessage extends Message {

    private int srcPort;
    private int desPort;

    /**
     * @param source        发送者信息
     * @param destination   接收者信息
     * @param messageType   消息类型
     * @param fileName      文件名
     * @param extensionName 文件扩展名
     * @param messageSize   文件长度
     * @param inputStream   消息（文本、多媒体）输入流
     */
    private TextMessage(String source, String destination, MessageType messageType, String fileName, String extensionName, int messageSize, InputStream inputStream) {
        super(source, destination, messageType, fileName, extensionName, messageSize, inputStream);
    }

    public void setHeader(String source, int srcPort, String destination, int desPort) throws Exception {
        if (TextUtil.isEmptyOrNull(source) || TextUtil.isEmptyOrNull(destination)) {
            throw new Exception("地址不明确");
        }
        this.source = source;
        this.destination = destination;
        this.srcPort = srcPort;
        this.desPort = desPort;
        this.messageType = MessageType.TEXT;
    }

    public byte[] getHeader() throws Exception {
        byte[] sourceBytes = createBytes(source.getBytes(), sourceLenght);
        byte[] srcPortBytes = Array2NumberConvertor.intToByte4(srcPort);
        byte[] desBytes = createBytes(destination.getBytes(), destinationLength);
        byte[] desPortBytes = Array2NumberConvertor.intToByte4(desPort);
        byte[] messageTypeBytes = Array2NumberConvertor.intToByte4(messageType.type);
        if (messageType == MessageType.STREAM) {
            byte[] fileNameBytes = createBytes(fileName.getBytes(), fileNameLength);
            byte[] extenceNameBytes = createBytes(extensionName.getBytes(), extensionNameLength);
        }
        if (messageSize == 0) {
            throw new Exception("请先设置消息内容");
        }
        byte[] msgBytes = Array2NumberConvertor.intToByte4(messageSize);
        byte[] header = new byte[sourceLenght + PORT_LENGTH + destinationLength + PORT_LENGTH
                + messageTypeLength + (messageType == MessageType.STREAM ? fileNameLength + extensionNameLength : 0) + messageSizeLength + messageSizeLength];
        System.arraycopy();
        return header;
    }

    public void setBody(String msg) {
        this.msg = msg;
    }


}
