package com.qi.chat.common.message;


import com.qi.chat.common.utils.Array2NumberConvertor;
import com.qi.chat.common.utils.ArrayUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.common.message.MessageParse
 */
public class MessageParse {

    public static Message parse(InputStream inputStream) throws Exception {
        Message message;
        String src = new String(read(inputStream, Message.SOURCE_LENGHT));
        int srcPort = Array2NumberConvertor.byte4ToInt(read(inputStream, Message.PORT_LENGTH), 0);
        String des = new String(read(inputStream, Message.DESTINATION_LENGTH));
        int desPort = Array2NumberConvertor.byte4ToInt(read(inputStream, Message.PORT_LENGTH), 0);
        int messageType = Array2NumberConvertor.byte4ToInt(read(inputStream, Message.MESSAGE_TYPE_LENGTH), 0);
        String fileName = null;
        String extenceName = null;
        if (messageType == Message.MessageType.TEXT.type) {
            message = new TextMessage();
        } else {
            message = new StreamMessage();
            fileName = new String(read(inputStream, Message.FILE_NAME_LENGTH));
            extenceName = new String(read(inputStream, Message.EXTENSION_NAME_LENGTH));
        }
        int messageSize = Array2NumberConvertor.byte4ToInt(read(inputStream, Message.MESSAGE_SIZE_LENGTH), 0);
        message.setHeader(src, srcPort, des, desPort);
        if (message.getMessageType() == Message.MessageType.TEXT) {
            TextMessage textMessage = (TextMessage) message;
            byte[] buff = new byte[messageSize];
            int read = inputStream.read(buff, 0, buff.length);
            textMessage.setBody(ArrayUtil.subBytes(buff, 0, read));
        } else {
            StreamMessage streamMessage = (StreamMessage) message;
            streamMessage.setBody(fileName, extenceName, messageSize, inputStream);
        }
        return message;
    }

    public static Message.Header parseHeader(byte[] bytes) throws Exception {
        int pos = 0;
        String src = new String(ArrayUtil.subBytes(bytes, pos, (pos+=Message.SOURCE_LENGHT)));
        int srcPort = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, (pos+=Message.PORT_LENGTH)), 0);
        String des = new String(ArrayUtil.subBytes(bytes, pos, (pos+=Message.SOURCE_LENGHT)));
        int desPort = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, (pos+=Message.PORT_LENGTH)), 0);
        int messageType = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, (pos+=Message.MESSAGE_TYPE_LENGTH)), 0);
        String fileName = null;
        String extenceName = null;
        if (messageType == Message.MessageType.TEXT.type) {
//            message = new TextMessage();
        } else {
//            message = new StreamMessage();
            fileName = new String(ArrayUtil.subBytes(bytes, pos, (pos+=Message.FILE_NAME_LENGTH)));
            extenceName = new String(ArrayUtil.subBytes(bytes, pos, (pos+=Message.EXTENSION_NAME_LENGTH)));
        }
        int messageSize = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, pos + Message.MESSAGE_SIZE_LENGTH), 0);
        return new Message.Header(src,srcPort,des,desPort,messageType== Message.MessageType.TEXT.type? Message.MessageType.TEXT: Message.MessageType.STREAM,fileName,extenceName,messageSize);
    }

    private static byte[] read(InputStream is, int length) throws IOException {
        byte[] bytes = new byte[length];
        is.read(bytes, 0, length);
        return bytes;
    }

    public static Message.MessageType parseType(byte[] data) {
        return null;
    }
}
