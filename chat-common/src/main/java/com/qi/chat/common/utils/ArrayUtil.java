package com.qi.chat.common.utils;

import java.util.Arrays;

/**
 * Creator  liuqi
 * Data     2018/4/17
 * Class    com.qi.chat.common.utils.ArrayUtil
 */
public class ArrayUtil {
    public static byte[] combineArray(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] combineArray = new byte[length];
        int pos = 0;
        for (int i = 0; i < arrays.length; i++) {
            System.arraycopy(arrays, 0, combineArray, pos, arrays.length);
            pos += arrays.length;
        }
        return combineArray;
    }

    /**
     * 将数组移到长度为length的新数组中
     *
     * @param src
     * @param length 新数组的长度
     * @return
     * @throws Exception
     */
    public static byte[] createBytes(byte[] src, int length) throws Exception {
        byte[] des = new byte[length];
        int d = des.length - src.length;
        if (d < 0) throw new Exception("消息头的长度大于定义的长度不符合");
        for (int i = 0; i < src.length; i++) {
            des[d + i] = src[i];
        }
        return des;
    }

    ;
}
