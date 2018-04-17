package com.qi.chat.common.message;

import com.qi.chat.common.utils.Array2NumberConvertor;
import com.qi.chat.common.utils.ArrayUtil;

import java.io.IOException;

/**
 * Created by qi on 2018/4/16.
 */
public class TextMessage extends Message {
    /**
     * 文本消息
     */
    private String msg;

    public TextMessage(){
        this.messageType = MessageType.TEXT;
    }

    /**
     * 返回消息头信息
     *
     * @return
     * @throws Exception
     */
    public byte[] getHeader() throws Exception {
        byte[] sourceBytes = createBytes(source.getBytes(), SOURCE_LENGHT);
        byte[] srcPortBytes = Array2NumberConvertor.intToByte4(srcPort);
        byte[] desBytes = createBytes(destination.getBytes(), DESTINATION_LENGTH);
        byte[] desPortBytes = Array2NumberConvertor.intToByte4(desPort);
        byte[] messageTypeBytes = Array2NumberConvertor.intToByte4(messageType.type);
        byte[] msgSizeBytes = Array2NumberConvertor.intToByte4(messageSize);
        return ArrayUtil.combineArray(sourceBytes, srcPortBytes, desBytes, desPortBytes, messageTypeBytes, msgSizeBytes);
    }

    public void setMsg(String msg) {
        this.msg = msg;
        this.messageSize = msg.getBytes().length;
    }

    public String getMsg() throws IOException {
        return msg;
    }

}
