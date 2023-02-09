package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

/**
 * 作者:wyq
 * 日期:2023/2/8 18:45
 * 描述:
 */
public class Sender extends Thread{


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
    //生成hash的key,默认值123
    private int key = 123;

    public Sender() {
    }

    public Sender(byte[] data) {
        this.data = data;
    }

    public Sender(int blockNumber, int hashSize) {
        this.blockNumber = blockNumber;
        this.hashSize = hashSize;
    }

    //发送者线程的执行方法
    public void run() {
        // code to run in Thread A
        boolean[] previousHash = new boolean[]{true,true,true,true};
        //System.out.println("Message of sender is:\n");
        for (int i = 0; i < blockNumber; i++) {
            synchronized (this) {
                //创建随机数据部分
                byte[] data = getRandomString(10).getBytes();
                //创建一个区块对象
                Block block = new Block();
                //把前一个区块的hash存入当前区块
                block.setPreviousHash(previousHash);
                block.setData(data);
                //设置当前区块的hash
                block.setHash(HashGenerator.hashGenerator(data, key, hashSize));
                //把当前区块的hash存入临时变量，下次循环的时候赋值给下个区块的previousHash属性
                previousHash = block.getHash();
                boolean b = Receiver.receiveBlock(block, blockNumber);
                if (b) {
                    //System.err.println("success");
                }
                block.setIndex(i);
                System.out.println("SENDER:"+block);
            }

        }

    }
    /**
     * 用于生成区块
     */
    public void generateBlock(){
        boolean[] previousHash = new boolean[]{true,true,true,true};
        for (int i = 0; i < blockNumber; i++) {
            //创建随机数据部分
            byte[] data = getRandomString(10).getBytes();
            //创建一个区块对象
            Block block = new Block();
            //把前一个区块的hash存入当前区块
            block.setPreviousHash(previousHash);
            //设置当前区块的hash
            block.setHash(HashGenerator.hashGenerator(data,key,hashSize));
            //把当前区块的hash存入临时变量，下次循环的时候赋值给下个区块的previousHash属性
            previousHash = block.getHash();

        }

    }

    //生成随机字符串
    public static String getRandomString(int length){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // 创建随机数生成器
        Random random = new Random();
        // 创建一个空的字符串缓冲区
        StringBuilder buffer = new StringBuilder();
        //String buffer = null;
        // 随机生成字符串中的字符
        for (int i = 0; i < length; i++) {
            // 随机生成一个字符串中字符的索引
            int index = random.nextInt(characters.length());
            // 将该字符添加到字符串缓冲区
            buffer.append(characters.charAt(index));
        }
        // 将字符串缓冲区转换为字符串

        return String.valueOf(buffer);
    }


}
