package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

/**
 * 作者:wyq
 * 日期:2023/2/4 19:57
 * 描述:
 */
public class Block {
    @Getter
    @Setter
    //区块在生产出来时的序号
    private int actualIndex;
    @Getter
    @Setter
    //区块链的序号
    private int index;
    @Setter
    @Getter
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

    @Setter
    @Getter
    //一个用于标记链路是否含有非法区块的特殊属性，默认为true
    private boolean isLegal = true;
    @Getter
    @Setter
    //标注该区块是否已经被读取检测过
    private boolean isRead = false;
    /*@Getter
    //用于记录该区块以及该区块之前的区块地址
    Map<Integer,Integer> blockAddress = new HashMap<Integer,Integer>();*/

    public Block() {
        this.data = " ".getBytes();
        this.hash = HashGenerator.byteToBoolen("0".getBytes());
        this.previousHash = HashGenerator.byteToBoolen("0".getBytes());
    }

    public Block(byte[] data) {
        this.data = data;
        this.hash = HashGenerator.byteToBoolen("0".getBytes());
        this.previousHash = HashGenerator.byteToBoolen("0".getBytes());
    }

    /*@Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", data=" + Arrays.toString(data) +
                ", hash=" + booleanArrayToBinaryString(hash) +
                ", previousHash=" + booleanArrayToBinaryString(previousHash) +
                '}';
    }*/
    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", actualIndex=" + actualIndex +
                ", data=" + new String(data) +
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
