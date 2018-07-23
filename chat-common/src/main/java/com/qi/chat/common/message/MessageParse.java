package com.qi.chat.common.message;


import com.qi.chat.common.utils.Array2NumberConvertor;
import com.qi.chat.common.utils.ArrayUtil;

import java.io.IOException;
import java.io.InputStream;

import static com.qi.chat.common.message.Message.*;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.common.message.MessageParse
 */
public class MessageParse {

    public static Message parse(InputStream inputStream) throws Exception {
        Message message;
        String src = new String(read(inputStream, SOURCE_LENGHT));
        int srcPort = Array2NumberConvertor.byte4ToInt(read(inputStream, PORT_LENGTH), 0);
        String des = new String(read(inputStream, DESTINATION_LENGTH));
        int desPort = Array2NumberConvertor.byte4ToInt(read(inputStream, PORT_LENGTH), 0);
        int messageType = Array2NumberConvertor.byte4ToInt(read(inputStream, MESSAGE_TYPE_LENGTH), 0);
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
        message.setHeader(src, srcPort, des, desPort, messageType == Message.MessageType.TEXT.type ? Message.MessageType.TEXT : Message.MessageType.STREAM, "", "", messageSize);
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

    /**
     * 通过字节数组获取Header
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Message.Header parseHeader(byte[] bytes) throws Exception {
        int pos = 0;
        String src = new String(ArrayUtil.subBytes(bytes, pos, (pos += SOURCE_LENGHT)));
        int srcPort = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, (pos += PORT_LENGTH)), 0);
        String des = new String(ArrayUtil.subBytes(bytes, pos, (pos += SOURCE_LENGHT)));
        int desPort = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, (pos += PORT_LENGTH)), 0);
        int messageType = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, (pos += MESSAGE_TYPE_LENGTH)), 0);
        String fileName = null;
        String extenceName = null;
        if (messageType == Message.MessageType.TEXT.type) {
//            message = new TextMessage();
        } else {
//            message = new StreamMessage();
            fileName = new String(ArrayUtil.subBytes(bytes, pos, (pos += Message.FILE_NAME_LENGTH)));
            extenceName = new String(ArrayUtil.subBytes(bytes, pos, (pos += Message.EXTENSION_NAME_LENGTH)));
        }
        int messageSize = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(bytes, pos, pos + Message.MESSAGE_SIZE_LENGTH), 0);
        return new Message.Header(src, srcPort, des, desPort, messageType == Message.MessageType.TEXT.type ? Message.MessageType.TEXT : Message.MessageType.STREAM, fileName, extenceName, messageSize);
    }

    private static byte[] read(InputStream is, int length) throws IOException {
        byte[] bytes = new byte[length];
        is.read(bytes, 0, length);
        return bytes;
    }

    public static Message.MessageType parseType(byte[] data) {
        int srcToDesPort = SOURCE_LENGHT + PORT_LENGTH + DESTINATION_LENGTH + PORT_LENGTH;
        int srcToType = srcToDesPort + MESSAGE_TYPE_LENGTH;
        if (data.length < srcToType) {
            return null;
        }
        return Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(data, srcToDesPort, srcToType), 0) == Message.MessageType.TEXT.type ? Message.MessageType.TEXT : Message.MessageType.STREAM;
    }

    public static int parseBodySize(byte[] data) {
        int startIndex = 0;
        int endIndex = 0;
        int size = 0;
        Message.MessageType messageType = parseType(data);
        if (messageType == null) {
            return 0;
        } else if (messageType == Message.MessageType.TEXT) {
            startIndex = SOURCE_LENGHT + PORT_LENGTH + DESTINATION_LENGTH + PORT_LENGTH + MESSAGE_TYPE_LENGTH;
            endIndex = startIndex + Message.MESSAGE_SIZE_LENGTH;
            size = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(data, startIndex, endIndex), 0);
        } else if (messageType == Message.MessageType.STREAM) {
            size = Array2NumberConvertor.byte4ToInt(ArrayUtil.subBytes(data,
                    SOURCE_LENGHT + PORT_LENGTH + DESTINATION_LENGTH + PORT_LENGTH,
                    SOURCE_LENGHT + PORT_LENGTH + DESTINATION_LENGTH + PORT_LENGTH
                            + MESSAGE_TYPE_LENGTH + Message.FILE_NAME_LENGTH + Message.EXTENSION_NAME_LENGTH + Message.MESSAGE_SIZE_LENGTH),
                    0);
        }
        return size;
    }

    public static int parseHeaderSize(byte[] data) {
        Message.MessageType messageType = parseType(data);
        return SOURCE_LENGHT + PORT_LENGTH + DESTINATION_LENGTH + PORT_LENGTH + MESSAGE_TYPE_LENGTH +
                (messageType == Message.MessageType.TEXT ? 0 : FILE_NAME_LENGTH + EXTENSION_NAME_LENGTH)
                + MESSAGE_SIZE_LENGTH;
    }
}
