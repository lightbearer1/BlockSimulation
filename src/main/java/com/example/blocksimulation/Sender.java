package com.example.blocksimulation;

import com.sun.xml.internal.bind.v2.TODO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

import static com.example.blocksimulation.BlockSimulationApplication.overThread;

/**
 * 作者:wyq
 * 日期:2023/2/8 18:45
 * 描述:
 */
@Slf4j
public class Sender extends Thread{


    //用于控制攻击者线程什么时候开启
    private  Thread attacker;
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

    public Sender(int blockNumber, int hashSize,Thread attacker) {
        this.blockNumber = blockNumber;
        this.hashSize = hashSize;
        this.attacker = attacker;
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
        //while (!Attacker.shouldExecute) {
            try {

                // code to run in Thread A
                boolean[] previousHash = new boolean[]{true, true, true, true};
                //System.out.println("Message of sender is:\n");
                for (int i = 0; i < blockNumber; i++) {
                    //synchronized (this) {
                    //创建随机数据部分
                    byte[] data = getRandomString(10).getBytes();
                    //创建一个区块对象
                    Block block = new Block();
                    //把前一个区块的hash存入当前区块
                    block.setPreviousHash(previousHash);
                    if (i == blockNumber - 1) {
                        block.setData("End Chain".getBytes());
                    } else {
                        block.setData(data);
                    }
                    boolean[] thisHash = HashGenerator.hashGenerator(data, key, hashSize);
                    //如果两个连续的区块hash值相同，那么更改当前的区块hash
                    if (Arrays.equals(thisHash, previousHash)){
                        thisHash[hashSize-1] = !thisHash[hashSize-1];
                    }
                    //设置当前区块的hash
                    block.setHash(thisHash);

                    //把当前区块的hash存入临时变量，下次循环的时候赋值给下个区块的previousHash属性
                    previousHash = block.getHash();
                    String b = Receiver.receiveBlock(block, blockNumber);

                    /*if (b) {
                        //System.err.println("success");
                    }*/
                    block.setIndex(i);
                    //log.info("SENDER:" + block);
                    //System.out.println("SENDER:" + block);
                    if ("End".equals(b)){
                        break;
                    }
                    if (i == 0 && "true".equals(b)) {
                        //Attacker.start();
                        attacker.start();
                    }
                    Attacker.shouldExecute = true;
                    while (Attacker.shouldExecute){
                        if (overThread){
                            break;
                        }

                        if (!Attacker.shouldExecute)
                            break;
                    }
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
       // }

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
