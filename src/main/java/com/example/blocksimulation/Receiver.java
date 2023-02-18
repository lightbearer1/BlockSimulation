package com.example.blocksimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    //是否开始接收数据
    //static volatile boolean isBeginReceive = false;

    //用于记录主区块链的区块
    //Map<Integer,Integer> blockAddress = new HashMap<Integer,Integer>();
    static List<Block> mainBlockChain = new CopyOnWriteArrayList<>();
    //用于记录产生的每一条区块链
    static List<List<Block>> blockChainNumber = new CopyOnWriteArrayList<>();




    public synchronized static String receiveBlock(Block block,int blockNumber){
        //TODO 应该要检查传入的区块preHash和以前的所有区块的hash是否有相同的，这些也会产生分支链路
        //判断当前是否没有任何区块链，如果是则添加主链路到链路集合
        if (blockChainNumber.size()==0){
            blockChainNumber.add(mainBlockChain);
        }
        //循环遍历存放链路的集合
        for (List<Block> chain : blockChainNumber) {
        /*for (int i = 0; i < blockChainNumber.size(); i++) {
            List<Block> chain = blockChainNumber.get(i);*/
            //遍历当前链路
            /*for (int i = 1; i < chain.size(); i++) {*/

            //}
            if (Arrays.equals("End Chain".getBytes(), block.getData())){
                block.setIndex(index++);
                //更改当前状态为开始接收消息
                //isBeginReceive=true;

                chain.add(block);

                previousHash=block.getHash();
                return "End";
            }
            //只有当初始区块以外的区块进入接收才会判断
            if (index>1){
                //如果当前传入区块的preHash和上一个区块的preHash相同
                if (Arrays.equals(block.getPreviousHash(), chain.get(chain.size()-1).getPreviousHash())) {
                    //创建一个新的链路分支，并且将当前的链路复制给新的分支
                    List<Block> newChain = new ArrayList<>(chain);
                    newChain.remove(chain.size()-1);
                    block.setIndex(chain.size()-1);
                    newChain.add(block);
                    blockChainNumber.add(newChain);

                    //return "true";
                }
            }

            //验证收到的区块数量是否小于额定的区块数量
            if (index<blockNumber-1){
                //用于比较初始区块是否合法
                if (Arrays.equals(block.getPreviousHash(), previousHash)){
                    block.setIndex(index++);
                    //更改当前状态为开始接收消息
                    //isBeginReceive=true;

                    chain.add(block);

                    previousHash=block.getHash();
                    return "true";

                }

            }



            /*if (Arrays.equals(block.getPreviousHash(), chain.get(index).getHash())) {
                blockChainNumber.add(new ArrayList<>());
            }*/
        }
        //isBeginReceive = false;
        return "false";


    }

    public static void printBlockChain(){

        System.out.println("The number of chain is "+blockChainNumber.size());
        for (int i = 0; i < blockChainNumber.size(); i++) {
            System.out.println("The value of No."+(i+1)+" chain is:");
            for (Block block:blockChainNumber.get(i)
                 ) {
                System.out.println("  "+block.toString());
            }
        }
    }


}
