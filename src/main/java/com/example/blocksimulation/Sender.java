package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

/**
 * 作者:wyq
 * 日期:2023/2/8 18:45
 * 描述:
 */
public class Sender {


    @Getter
    @Setter
    //要发送的区块数量
    private int blockNumber;
    @Getter
    @Setter
    //hash的位数
    private int hashSize;
    @Getter
    @Setter
    //数据部分
    private byte[] data;
    @Getter
    @Setter
    //生成hash的key
    private int key;

    public void generateBlock(){
        Block block  = new Block();
        block.setHash(HashGenerator.hashGenerator(data,key,hashSize));
    }


}
