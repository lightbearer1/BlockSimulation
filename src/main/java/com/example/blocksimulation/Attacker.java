package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.example.blocksimulation.BlockSimulationApplication.overThread;

/**
 * 作者:wyq
 * 日期:2023/2/8 18:47
 * 描述:
 */
public class Attacker extends Thread{


    private  int index = 0;
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

    //用于控制攻击线程的状态
    static volatile boolean shouldExecute = false;

    //用于控制攻击线程的状态的方法
    public void execute() {
        shouldExecute = true;
    }

    //是否打印攻击结果
    private boolean isPrint;

    public Attacker() {
    }

    public Attacker(byte[] data) {
        this.data = data;
    }

    public Attacker(int blockNumber,int hashSize,int attackNum) {
        this.blockNumber = blockNumber;
        this.hashSize = hashSize;
        this.attackNum = attackNum;
    }

    public Attacker(int blockNumber,int hashSize,int attackNum,boolean isPrint) {
        this.blockNumber = blockNumber;
        this.hashSize = hashSize;
        this.attackNum = attackNum;
        this.isPrint = isPrint;
    }

    public void run() {
        try {
            int actualIndex = 0;
            while (shouldExecute) {

                boolean[] previousHash = generateHash(hashSize);

                //此处理论上应该采用泊松分布来分配攻击次数，但是由于多线程的特性也近似的达到了想要的效果
                for (int i = 0; i < blockNumber - 1; i++) {
                    for (int j = 0; j < attackNum; j++) {
                        //创建随机数据部分
                        byte[] data = "Error Block".getBytes();
                        //创建一个区块对象
                        Block block = new Block(data);
                        //把前一个区块的hash存入当前区块
                        block.setPreviousHash(previousHash);
                        //设置当前区块的hash
                        block.setHash(generateHash(hashSize));
                        //设置当前区块的index
                        block.setActualIndex(actualIndex++);
                        //把当前区块的hash存入临时变量，下次循环的时候赋值给下个区块的previousHash属性
                        previousHash = block.getHash();
                        //发送区块
                        String b = Receiver.receiveBlock(block, blockNumber);
                        //判断是否攻击成功
                        if (b.equals("false")) {
                            //q: isPrint的值为多少
                            //a: 0
                            //q: isPrint的值是否会改变
                            //
                            if (isPrint) {
                                block.setIndex(index);
                                //System.out.println("ATTACKER: No." + index++ + " attack FAILED:" + block);
                            }

                        } else {
                            //System.out.println("No." + index++ + " attack SUCCESS:" + block);
                        }
                    }
                    //q: 这个变量的作用是什么？
                    //a: 用于控制攻击线程的状态
                    //q:如何控制状态
                    //a:当攻击线程的状态为false时，攻击线程会停止攻击
                    shouldExecute = false;
                    if (overThread)
                        break;
                /*if (Receiver.isBeginReceive){
                    this.start();
                }*/

                }
            }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


    }

    /**
     * 用于生成区块(已弃用)
     */
    public void generateAttackBlock() throws NoSuchAlgorithmException {
        //生成前一个区块的hash
        boolean[] previousHash = generateHash(hashSize);
        //此处理论上应该采用泊松分布来分配攻击次数，但是由于多线程的特性也近似的达到了想要的效果
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
