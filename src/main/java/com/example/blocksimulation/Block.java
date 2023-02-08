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
    @Setter
    // 区块的hash值
    private boolean[] hash;
    //下一个区块的hash值
    private boolean[] nextHash;
    /*@Getter
    //用于记录该区块以及该区块之前的区块地址
    Map<Integer,Integer> blockAddress = new HashMap<Integer,Integer>();*/

    public Block() {

    }

    public Block(String data) {
        this.data = data;
    }



}
