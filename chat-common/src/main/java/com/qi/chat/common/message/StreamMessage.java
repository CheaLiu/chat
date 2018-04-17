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
    /**
     * 文件名
     */
    protected String fileName;
    /**
     * 文件扩展名
     */
    protected String extensionName;

    public StreamMessage() {
        messageType = MessageType.STREAM;
    }

    /**
     * 返回消息头信息
     *
     * @return
     * @throws Exception
     */
    public byte[] getHeader() throws Exception {
        byte[] sourceBytes = createBytes(TextUtil.getNotNullString(source).getBytes(), SOURCE_LENGHT);
        byte[] srcPortBytes = Array2NumberConvertor.intToByte4(srcPort);
        byte[] desBytes = createBytes(TextUtil.getNotNullString(destination).getBytes(), DESTINATION_LENGTH);
        byte[] desPortBytes = Array2NumberConvertor.intToByte4(desPort);
        byte[] messageTypeBytes = Array2NumberConvertor.intToByte4(messageType.type);
        byte[] fileNameBytes = createBytes(TextUtil.getNotNullString(fileName).getBytes(), FILE_NAME_LENGTH);
        byte[] extenceNameBytes = createBytes(TextUtil.getNotNullString(extensionName).getBytes(), EXTENSION_NAME_LENGTH);
        byte[] msgSizeBytes = Array2NumberConvertor.intToByte4(messageSize);
        return ArrayUtil.combineArray(sourceBytes, srcPortBytes, desBytes, desPortBytes, messageTypeBytes, fileNameBytes, extenceNameBytes, msgSizeBytes);
    }

    public void setBody(String fileName, String extensionName, int length, InputStream inputStream) {
        this.fileName = fileName;
        this.extensionName = extensionName;
        this.messageSize = length;
        this.message = inputStream;
    }

    public InputStream getBody() {
        return message;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExtensionName() {
        return extensionName;
    }

}
