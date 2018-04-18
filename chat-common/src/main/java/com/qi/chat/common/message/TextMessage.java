package com.qi.chat.common.message;

import com.qi.chat.common.utils.Array2NumberConvertor;
import com.qi.chat.common.utils.ArrayUtil;

/**
 * Created by qi on 2018/4/16.
 */
public class TextMessage extends Message {
    /**
     * 文本消息
     */
    private byte[] body;

    public TextMessage() {
        header.messageType = MessageType.TEXT;
    }

    /**
     * 返回消息头信息
     *
     * @return
     * @throws Exception
     */
    public byte[] getHeader() throws Exception {
        byte[] sourceBytes = ArrayUtil.extendBytes(header.source.getBytes(), SOURCE_LENGHT);
        byte[] srcPortBytes = Array2NumberConvertor.intToByte4(header.srcPort);
        byte[] desBytes = ArrayUtil.extendBytes(header.destination.getBytes(), DESTINATION_LENGTH);
        byte[] desPortBytes = Array2NumberConvertor.intToByte4(header.desPort);
        byte[] messageTypeBytes = Array2NumberConvertor.intToByte4(header.messageType.type);
        byte[] msgSizeBytes = Array2NumberConvertor.intToByte4(header.messageSize);
        return ArrayUtil.combineArray(sourceBytes, srcPortBytes, desBytes, desPortBytes, messageTypeBytes, msgSizeBytes);
    }

    public void setBody(byte[] body) {
        header.messageSize = body.length;
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] getMessage() throws Exception {
        return ArrayUtil.combineArray(getHeader(), getBody());
    }
}
