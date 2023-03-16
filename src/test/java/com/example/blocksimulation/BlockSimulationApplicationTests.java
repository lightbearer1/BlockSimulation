package com.example.blocksimulation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.blocksimulation.TaskOne.getPoissonRandom;

@SpringBootTest
class BlockSimulationApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testGetByte(){
        String s = "error";
        System.out.println(Arrays.toString(s.getBytes()));
    }

    @Test
    void testByteToBoolen(){
        byte[] input = "hello".getBytes();
        boolean[] result = new boolean[input.length * 8];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < 8; j++) {
                result[i * 8 + j] = (input[i] & (1 << j)) != 0;
            }
        }
        System.out.println(Arrays.toString(result));
    }

    @Test
    void testGenerateHash() throws NoSuchAlgorithmException {
        int size = 8;
        boolean[] result = new boolean[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            result[i] = random.nextBoolean();
        }
        System.out.println(Arrays.toString(result));
    }

    @Test
    void poissonTest() throws InterruptedException {
        int m = 10; // 执行程序一的次数
        int k = 10; // 执行程序二的次数
        int maxCount = m*k; // 程序二执行的最大次数
        int count = 0; // 已经执行程序二的次数
        Random random = new Random();

        for (int i = 0; i < m; i++) {
            int x = (int) getPoissonRandom(2, random); // 生成符合泊松分布的随机数
            System.out.println("程序一执行次数: " + (i + 1) + ", 程序二执行次数: " + x * k);
            TaskOne.taskOneCount++; // 每执行一次程序一，就增加 taskOneCount 的值
            TaskOne taskOne = new TaskOne();
            taskOne.run();

            count += x * k; // 计算程序二已经执行的总次数
            if (count >= maxCount) { // 判断是否达到了最大执行次数
                System.out.println("达到了最大执行次数，程序结束");
                break;
            }
        }



    }
    @Test
    void poissonTest2() throws InterruptedException {
        int m = 10; // 程序一执行的次数
        ProgramTwo programTwo = new ProgramTwo(10); // 创建程序二实例，泊松分布的参数为2

        for (int i = 0; i < m; i++) {
            // 程序一
            System.out.printf("Task One #%d start...\n", i + 1);
            System.out.printf("Task One #%d end.\n", i + 1);

            // 程序二
            programTwo.execute();
        }
    }

    @Test
    void drawGraph(){
        //DrawGraph.drawGraph();
    }

    @Test
    void loop(){
        int blockNumber = 10;
        int hashSizeStart = 6;
        int hashSizeEnd = 12;
        int attackNumber = 10;
        Thread attacker = null;
        Thread sender = null;
        Variable variable = new Variable();

        //控制每个不同数量攻击中样本的数量
        int numOfExecution = 1000;
        //存放循环后的结果
        double[] result = new double[numOfExecution];

        //错误链路的数量
        double numOfIllegalChain = 0;


        try {
            for (int hashSize = hashSizeStart; hashSize < hashSizeEnd; hashSize++) {
                //链路总数量
                double numOfAllChain = 0;
                //存放链路数据的集合
                List<Double> resultList = new ArrayList<>();
                double total = 0;
                for (int i = 1; i < numOfExecution; i++) {
                    //控制模拟的链路总数量保持在1000附近
                    if (numOfAllChain > 1000) {
                        System.out.println(i);
                        break;
                    }

                    //创建多线程
                    attacker = new Thread(new Attacker(blockNumber, hashSize, attackNumber), "Attacker");
                    sender = new Thread(new Sender(blockNumber, hashSize, attacker), "Sender");
                    sender.start();

                    Thread.sleep(30);
                    ////log.info("_______________________________________________________________________");
                    //System.out.println("_______________________________________________________________________");
                    //Thread.sleep(2000);
                    ////log.info("after attack,the received message is:");
                    //System.out.println("after attack,the received message is:");
                    //返回生成的链路数量
                    int numOfChain = Receiver.printBlockChain(variable.getBlockNumber());
                    numOfAllChain += numOfChain;


                    //log.warn(String.valueOf(numOfChain));

                    //初始化接收者数据---------------------------------------
                    Receiver.mainBlockChain = new CopyOnWriteArrayList<>();
                    Receiver.blockChainNumber = new CopyOnWriteArrayList<>();
                    Receiver.index = 0;
                    Receiver.previousHash = new boolean[]{true, true, true, true};

                    //total+=numOfChain;
                    //log.info(String.valueOf(total));
                    //result[i] = numOfChain;
                    if (numOfChain > 1) {
                        for (int j = 2; j <= numOfChain; j++) {
                            resultList.add((double) hashSize);
                        }
                    }
                }

                System.out.println(hashSize + ":" + resultList.size());
                System.out.println(hashSize + ":" + numOfAllChain);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (attacker != null) {
                //overThread = true;
                attacker.interrupt();
            }
            if (sender != null) {
                //overThread = true;
                sender.interrupt();
            }

        }
    }

    @Test
    void testLoop2(){

        int endNumber = 7;
        Variable variable = new Variable("hashSize",6);
        Thread attacker = null;
        Thread sender = null;


        //控制每个不同数量攻击中样本的数量
        int numOfExecution = 100;
        //存放循环后的结果
        //double[] result = new double[numOfExecution];

        /*//存放链路数据的集合
        List<Double> resultList = new ArrayList<>();*/

        //错误链路的数量
        double numOfIllegalChain = 0;




        try {

            for (int temp = variable.getValue(); temp < endNumber; temp++) {

                switch (variable.getValueName()){
                    case "blockNumber":
                        variable.setBlockNumber(temp);
                        break;
                    case "hashSize":
                        variable.setHashSize(temp);
                        break;
                    case "attackNumer":
                        variable.setAttackNumber(temp);
                        break;
                }

                //链路总数量
                double numOfAllChain = 0;

                //存放链路数据的集合
                List<Double> resultList = new ArrayList<>();
                double total = 0;
                for (int i = 1; i < numOfExecution; i++) {
                    //控制模拟的链路总数量保持在1000附近
                    if (numOfAllChain >= 1000) {
                        System.out.println(i);
                        break;
                    }

                    //创建多线程
                    //variable.getBlockNumber(), variable.getHashSize(), variable.getAttackNumber()
                    attacker = new Thread(new Attacker(variable.getBlockNumber(), variable.getHashSize(), variable.getAttackNumber()), "Attacker");
                    sender = new Thread(new Sender(variable.getBlockNumber(), variable.getHashSize(), attacker), "Sender");
                    sender.start();

                    Thread.sleep(50);

                    //返回生成的链路数量
                    int numOfChain = Receiver.printBlockChain(variable.getBlockNumber());
                    numOfAllChain += numOfChain;

                    //初始化接收者数据---------------------------------------
                    Receiver.mainBlockChain = new CopyOnWriteArrayList<>();
                    Receiver.blockChainNumber = new CopyOnWriteArrayList<>();
                    Receiver.index = 0;
                    Receiver.previousHash = new boolean[]{true, true, true, true};

                    //total+=numOfChain;
                    //log.info(String.valueOf(total));
                    //result[i] = numOfChain;
                    if (numOfChain > 1) {
                        numOfIllegalChain += numOfChain - 1;
                        for (int j = 2; j <= numOfChain; j++) {

                            resultList.add((double) temp);
                        }
                    }
                }

                System.out.println(variable.getValueName()+" is "+temp + ",resultList.size():" + resultList.size());
                System.out.println(variable.getValueName()+" is "+temp + ",numOfAllChain:" + numOfAllChain);
                System.out.println(numOfIllegalChain);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        } finally {
            if (attacker != null) {
                //overThread = true;
                attacker.interrupt();
            }
            if (sender != null) {
                //overThread = true;
                sender.interrupt();
            }

        }
    }
}
