package com.example.blocksimulation;

/**
 * 作者:wyq
 * 日期:2023/2/4 20:20
 * 描述:生成hash的类
 */
public final class HashGenerator {
    //数据部分
    public static byte[] data;
    //生成hash的key
    public static int key = 123;
    //hash的大小
    public static int hashSize = 8;


    //生成hash值(指定key)
    public static boolean[] hashGenerator(byte[] data,int key,int hashSize){

        //************** hash部分 *************
        if (data == null)
            return null;

        int result = 1;
        for (byte element : data)
            result = 30 * result + element;
        //************************************

        byte[] hashByte = {};
        boolean[] hash = byteToBoolen(hashByte);
        return hash;
    }

    //生成hash值(使用默认的key)
    public static boolean[] hashGenerator(byte[] data,int hashSize){

        byte[] hashByte = {};
        boolean[] hash = byteToBoolen(hashByte);
        return hash;
    }

    //byte数组转换成布尔类型数组
    private static boolean[] byteToBoolen(byte[] bytes){

        boolean[] booleanArray = {};
        return booleanArray;
    }

    // 将字符串转化为二进制字符串的函数/Function that converts a string to a binary string
    public static String toBinaryByte(byte[] bytes) {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            binary.append(Integer.toBinaryString(b & 0xff));
        }
        return String.valueOf(binary);
    }
}
