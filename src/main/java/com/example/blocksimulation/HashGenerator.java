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
    public static int key ;
    //hash的大小
    public static int hashSize = 8;



    /**
     * 生成hash值(指定key)
     * @param data 存放数据的byte[]数组
     * @param key   生成hash的密钥key
     * @param hashSize  生成的hash大小
     * @return 生成boolen[]类型的hash
     */
    public static boolean[] hashGenerator(byte[] data,int key,int hashSize){
        //*************合并data和key****************
        byte[] keyByte = Integer.toString(key).getBytes();
        byte[] temp = new byte[data.length + keyByte.length];
        System.arraycopy(data, 0, temp, 0, data.length);
        System.arraycopy(keyByte, 0, temp, data.length, keyByte.length);

        //**********对合并后的数据进行异或运算，求得hash*******
        int blockSize = data.length / hashSize;
        boolean[] dataBoolen = byteToBoolen(temp);
        boolean[] result = new boolean[hashSize];
        for (int i = 0; i < hashSize; i++) {
            result[i] = dataBoolen[i * blockSize];
            for (int j = 1; j < blockSize; j++) {
                result[i] = result[i] ^ dataBoolen[i * blockSize + j];
            }
        }
        return result;
    }


    //byte数组转换成布尔类型数组
    public static boolean[] byteToBoolen(byte[] input) {
        boolean[] result = new boolean[input.length * 8];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < 8; j++) {
                result[i * 8 + j] = (input[i] & (1 << j)) != 0;
            }
        }
        return result;
    }

    public static byte[] intToByteArray(int input) {
        return new byte[]{
                (byte) (input >>> 24),
                (byte) (input >>> 16),
                (byte) (input >>> 8),
                (byte) input
        };
    }

    public static boolean[] hash(boolean[] data, int key, int length) {
        int blockSize = data.length / length;
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = data[i * blockSize];
            for (int j = 1; j < blockSize; j++) {
                result[i] = result[i] ^ data[i * blockSize + j];
            }
        }
        return result;
    }
}
