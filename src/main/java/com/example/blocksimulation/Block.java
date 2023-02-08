package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者:wyq
 * 日期:2023/2/4 19:57
 * 描述:
 */
public class Block {
    @Getter
    @Setter
    //区块链的序号
    private int index;
    // 区块中的数据部分
    private String data;
    // 区块的key
    private static int key;
    @Setter
    // 区块的hash值
    private boolean[] hash;
    // 哈希的位数
    private int hashSize;
    //下一个区块的hash值
    private boolean[] nextHash;
    /*@Getter
    //用于记录该区块以及该区块之前的区块地址
    Map<Integer,Integer> blockAddress = new HashMap<Integer,Integer>();*/

    public Block() {

    }

    public Block(String data) {
        this.data = data;
        //generateHash();
    }

    public Block(String data, int key) {
        this.data = data;
        this.key = key;
        //generateHash();
    }

    // 构造函数，初始化区块的数据部分和key
    public Block(String data, int key, int hashSize) {
        this.data = data;
        this.key = key;
        this.hashSize = hashSize;
        // 调用generateHash函数生成hash值
        //generateHash();
    }

    /*// 生成hash值的函数
    public void generateHash() {
        // 将data和key转化为二进制字符串/Convert data and key to binary string
        String dataBin = toBinaryString(data);
        String keyBin = toBinaryString(String.valueOf(key));
        // 将dataBin和keyBin拼接起来/Splice dataBin and keyBin
        String combined = dataBin + keyBin;
        // 对拼接后的字符串进行哈希，并截取前hashBits位/Hash the concatenated string and intercept the previous hashBits
        hash = hash(combined).substring(1, hashSize +1);
    }*/

    /*// 哈希函数，使用Java的Arrays类的hashCode函数/Hash function, using the hashCode function of Java's Arrays class
    public static String hash(String s) {
        return Integer.toBinaryString(hashCode(s.getBytes()));
    }
*/
    // 将字符串转化为二进制字符串的函数/Function that converts a string to a binary string
    public static String toBinaryString(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            binary.append(Integer.toBinaryString(b & 0xff));
        }
        return String.valueOf(binary);
    }


    /*public void setHashSize(int hashSize) {
        this.hashSize = hashSize;
        generateHash();
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", data='" + data + '\'' +
                ", hash='" + hash + '\'' +
                ", key='" + key + '\'' +
                ", hashSize='" + hashSize + '\'' +
                '}';
    }


    public static int hashCode(byte[] a) {
        if (a == null)
            return 0;

        int result = 1;
        for (byte element : a)
            result = 30 * result + element;

        return result;
    }*/
}
