package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

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
    @Setter
    // 区块中的数据部分
    private byte[] data;
    @Setter
    @Getter
    // 区块的hash值
    private boolean[] hash;
    @Setter
    @Getter
    //前一个区块的hash值
    private boolean[] previousHash;
    /*@Getter
    //用于记录该区块以及该区块之前的区块地址
    Map<Integer,Integer> blockAddress = new HashMap<Integer,Integer>();*/

    public Block() {

    }

    public Block(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", data=" + Arrays.toString(data) +
                ", hash=" + booleanArrayToBinaryString(hash) +
                ", previousHash=" + booleanArrayToBinaryString(previousHash) +
                '}';
    }
    public static String booleanArrayToBinaryString(boolean[] input) {
        StringBuilder result = new StringBuilder();
        for (boolean b : input) {
            result.append(b ? '1' : '0');
        }
        return result.toString();
    }
}
