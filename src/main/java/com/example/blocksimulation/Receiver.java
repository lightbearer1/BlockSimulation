package com.example.blocksimulation;

import java.util.*;

/**
 * 作者:wyq
 * 日期:2023/2/8 18:47
 * 描述:
 */
public class Receiver {

    //区块的数量
    private int blockNumber;

    //区块的序号
    static int index=0;

    //设置初始区块的previousHash值
    static boolean[] previousHash = new boolean[]{true,true,true,true};

    //用于记录区块
    //Map<Integer,Integer> blockAddress = new HashMap<Integer,Integer>();
    static List<Block> blockChain = new ArrayList<Block>();


    public static boolean receiveBlock(Block block,int blockNumber){

        //用于比较初始区块是否合法
        if (Arrays.equals(block.getPreviousHash(), previousHash)){
            block.setIndex(index++);
            blockChain.add(block);
            previousHash=block.getHash();

            return true;

        }
        return false;


    }

    public static void printBlockChain(){
        for (int i = 0; i < blockChain.size(); i++) {
            Block block = blockChain.get(i);
            System.out.println(block);
        }
    }

}
