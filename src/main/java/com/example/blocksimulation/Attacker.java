package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 作者:wyq
 * 日期:2023/2/8 18:47
 * 描述:
 */
public class Attacker extends Thread{
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
    //攻击次数
    private int attackNum;

    public Attacker() {
    }

    public Attacker(byte[] data) {
        this.data = data;
    }

    public Attacker(int blockNumber, int hashSize) {
        this.blockNumber = blockNumber;
        this.hashSize = hashSize;
    }

    public void run() {
        try {
            boolean[] previousHash = generateHash(hashSize);
            for (int i = 0; i < blockNumber*attackNum; i++) {
                //创建随机数据部分
                byte[] data = "Error Block".getBytes();
                //创建一个区块对象
                Block block = new Block();
                //把前一个区块的hash存入当前区块
                block.setPreviousHash(previousHash);
                //设置当前区块的hash
                block.setHash(generateHash(hashSize));
                //把当前区块的hash存入临时变量，下次循环的时候赋值给下个区块的previousHash属性
                previousHash = block.getHash();
                boolean b = Receiver.receiveBlock(block, blockNumber);
                if (!b){
                    System.err.println("failed");
                }

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用于生成区块
     */
    public void generateAttackBlock() throws NoSuchAlgorithmException {

        boolean[] previousHash = generateHash(hashSize);
        for (int i = 0; i < blockNumber*attackNum; i++) {
            //创建随机数据部分
            byte[] data = "Error Block".getBytes();
            //创建一个区块对象
            Block block = new Block();
            //把前一个区块的hash存入当前区块
            block.setPreviousHash(previousHash);
            //设置当前区块的hash
            block.setHash(generateHash(hashSize));
            //把当前区块的hash存入临时变量，下次循环的时候赋值给下个区块的previousHash属性
            previousHash = block.getHash();

        }


    }


    /**
     * 生成随机hash
     * @param size hash的长度
     * @return 布尔类型数组
     */
    public static boolean[] generateHash(int size) throws NoSuchAlgorithmException {
        boolean[] booleanHash = new boolean[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            booleanHash[i] = random.nextBoolean();
        }
        return booleanHash;
    }
}
