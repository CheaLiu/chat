package com.qi.chat.common;

import com.qi.chat.common.packages.Array2NumberConvertor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 消息封装类 source + destination + messageType + [fileName + extensionName] + messageSize + message
 * Created by qi on 2018/4/15.
 */
public class Message {
    static final int sourceLenght = 15;
    static final int destinationLength = 15;
    final static int PORT_LENGTH = 4;
    static final int messageTypeLength = 4;
    final static int fileNameLength = 16;
    final static int extensionNameLength = 10;
    final static int messageSizeLength = 4;

    /**
     * 文件名
     */
    protected String fileName;
    /**
     * 文件扩展名
     */
    protected String extensionName;
    /**
     * 发送者信息
     */
    protected String source;
    /**
     * 接收者信息
     */
    protected String destination;
    /**
     * 文件长度
     */
    protected int messageSize;
    /**
     * 消息类型
     */
    protected MessageType messageType;
    /**
     * inputStream
     */
    protected InputStream message;
    /**
     * 文本消息
     */
    public String msg;

    /**
     * @param source        发送者信息
     * @param destination   接收者信息
     * @param messageType   消息类型
     * @param fileName      文件名
     * @param extensionName 文件扩展名
     * @param messageSize   文件长度
     * @param inputStream   消息（文本、多媒体）输入流
     */
    protected Message(String source, String destination, MessageType messageType, String fileName, String extensionName, int messageSize, InputStream inputStream) {
        this.source = source;
        this.destination = destination;
        this.messageType = messageType;
        this.message = inputStream;
        this.messageSize = messageSize;
        this.fileName = fileName;
        this.extensionName = extensionName;
    }

    public void write(OutputStream os) {
        try {
            byte[] sourceArray = new byte[sourceLenght];
            if (source == null) {
                source = "";
            }
            copyByteArray(source.getBytes(), sourceArray);
            os.write(sourceArray);
            byte[] desArray = new byte[destinationLength];
            if (destination == null) destination = "";
            copyByteArray(destination.getBytes(), desArray);
            os.write(desArray);
            os.write(Array2NumberConvertor.intToByte4(messageType.type));
            if (messageType == MessageType.STREAM) {
                byte[] fileNameArray = new byte[fileNameLength];
                if (fileName == null) fileName = "";
                copyByteArray(fileName.getBytes(), fileNameArray);
                os.write(fileNameArray);
                byte[] extensionNameArray = new byte[extensionNameLength];
                if (extensionName == null) extensionName = "";
                copyByteArray(extensionName.getBytes(), extensionNameArray);
                os.write(extensionNameArray);
            }
            os.write(Array2NumberConvertor.intToByte4(messageSize));
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = message.read(buff, 0, buff.length)) != -1) {
                os.write(buff, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (message != null) {
                    message.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //消息封装类 source + destination + messageType + [fileName + extensionName] + messageSize + message
    public void read(InputStream is) {
        try {
            source = new String(read(is, sourceLenght));
            source = new String(read(is, destinationLength));
            int type = Array2NumberConvertor.byte4ToInt(read(is, messageTypeLength), 0);
            if (type == MessageType.STREAM.type) {
                messageType = MessageType.STREAM;
                fileName = new String(read(is, fileNameLength));
                extensionName = new String(read(is, extensionNameLength));
            } else messageType = MessageType.TEXT;
            messageSize = Array2NumberConvertor.byte4ToInt(read(is, messageSizeLength), 0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] read(InputStream inputStream, int len) throws IOException {
        byte[] buff = new byte[len];
        inputStream.read(buff, 0, len);
        return buff;
    }

    private void copyByteArray(byte[] src, byte[] des) throws Exception {
        int d = des.length - src.length;
        if (d < 0) throw new Exception("消息头的长度大于定义的长度不符合");
        for (int i = 0; i < src.length; i++) {
            des[d + i] = src[i];
        }
    }

    protected byte[] createBytes(byte[] src, int length) throws Exception {
        byte[] des = new byte[length];
        int d = des.length - src.length;
        if (d < 0) throw new Exception("消息头的长度大于定义的长度不符合");
        for (int i = 0; i < src.length; i++) {
            des[d + i] = src[i];
        }
        return des;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public MessageType getMessageType() {
        return messageType;
    }


    public static class Builder {
        private String source;
        private String destination;
        private int size;
        private String fileName;
        private String extensionName;
        private MessageType messageType;
        private InputStream inputStream;

        public Builder setDestination(String destination) throws Exception {
            this.destination = destination;
            return this;
        }

        public Builder setSource(String source) throws Exception {
            this.source = source;
            return this;
        }

        public Builder setBody(String msg) {
            this.size = msg.getBytes().length;
            this.messageType = MessageType.TEXT;
            inputStream = new ByteArrayInputStream(msg.getBytes());
            return this;
        }

        public Builder setBody(InputStream inputStream, String filename, int size) throws Exception {
            String[] split = filename.split(".");
            this.fileName = split[0];
            this.extensionName = split[1];
            this.size = size;
            this.messageType = MessageType.STREAM;
            this.inputStream = inputStream;
            return this;
        }

        public Message build() {
            return new Message(source, destination, messageType, fileName, extensionName, size, inputStream);
        }

    }

    public enum MessageType {
        TEXT(1), STREAM(2);
        int type;

        MessageType(int type) {
            this.type = type;
        }
    }
}