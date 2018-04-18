package com.qi.chat.common.message;

import com.qi.chat.common.utils.Array2NumberConvertor;
import com.qi.chat.common.utils.ArrayUtil;
import com.qi.chat.common.utils.TextUtil;

import java.io.InputStream;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.common.message.StreamMessage
 */
public class StreamMessage extends Message {


    /**
     * inputStream
     */
    private InputStream message;

    public StreamMessage() {
        header.messageType = MessageType.STREAM;
    }

    /**
     * 返回消息头信息
     *
     * @return
     * @throws Exception
     */
    public byte[] getHeader() throws Exception {
        byte[] sourceBytes = ArrayUtil.extendBytes(TextUtil.getNotNullString(header.source).getBytes(), SOURCE_LENGHT);
        byte[] srcPortBytes = Array2NumberConvertor.intToByte4(header.srcPort);
        byte[] desBytes = ArrayUtil.extendBytes(TextUtil.getNotNullString(header.destination).getBytes(), DESTINATION_LENGTH);
        byte[] desPortBytes = Array2NumberConvertor.intToByte4(header.desPort);
        byte[] messageTypeBytes = Array2NumberConvertor.intToByte4(header.messageType.type);
        byte[] fileNameBytes = ArrayUtil.extendBytes(TextUtil.getNotNullString(header.fileName).getBytes(), FILE_NAME_LENGTH);
        byte[] extenceNameBytes = ArrayUtil.extendBytes(TextUtil.getNotNullString(header.extensionName).getBytes(), EXTENSION_NAME_LENGTH);
        byte[] msgSizeBytes = Array2NumberConvertor.intToByte4(header.messageSize);
        return ArrayUtil.combineArray(sourceBytes, srcPortBytes, desBytes, desPortBytes, messageTypeBytes, fileNameBytes, extenceNameBytes, msgSizeBytes);
    }

    public void setBody(String fileName, String extensionName, int length, InputStream inputStream) {
        header.fileName = fileName;
        header.extensionName = extensionName;
        header.messageSize = length;
        this.message = inputStream;
    }

    public InputStream getBody() {
        return message;
    }

    public String getFileName() {
        return header.fileName;
    }

    public String getExtensionName() {
        return header.extensionName;
    }

}
