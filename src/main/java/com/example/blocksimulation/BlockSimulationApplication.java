package com.example.blocksimulation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class BlockSimulationApplication {



    public static void main(String[] args) throws InterruptedException {
        //TODO 最外围添加一个循环，测试在不同参数下产生的不同的链路数量情况
        int blockNumber = 10;
        int hashSize = 6;
        int attackNumber = 10;
        boolean isPrint = false;
        Thread attacker = null;
        Thread sender = null;

        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Please input block number , hash size , attack number:");
        blockNumber = scanner.nextInt();
        hashSize = scanner.nextInt();
        attackNumber = scanner.nextInt();
        Scanner scanner1 = new Scanner(System.in);
        //打印攻击结果
        System.out.println("Please choose print the message of attacker or not:(y/n)");
        String s = scanner1.nextLine();
        if ("y".equals(s)||"Y".equals(s)) {
            isPrint=true;
        }*/
        //控制每个不同数量攻击中样本的数量
        int numOfExecution = 5;
        //每次攻击中样本的平均数据
        double[] AveQuantity = new double[20];
        try {
            for (attackNumber = 0;attackNumber<numOfExecution;attackNumber++){
                double total = 0;
                for (int i = 1;i<numOfExecution;i++){
                    //创建多线程
                    attacker = new Thread(new Attacker(blockNumber,hashSize,attackNumber,isPrint),"Attacker");
                    sender = new Thread(new Sender(blockNumber,hashSize,attacker),"Sender");
                    sender.start();

                    //Thread.sleep(100);
                    ////log.info("_______________________________________________________________________");
                    //System.out.println("_______________________________________________________________________");
                    //Thread.sleep(2000);
                    ////log.info("after attack,the received message is:");
                    //System.out.println("after attack,the received message is:");
                    //返回生成的链路数量
                    int numOfChain = Receiver.printBlockChain();
                    ////log.info(String.valueOf(numOfChain));
                    total+=numOfChain;
                }
                AveQuantity[attackNumber] = total/numOfExecution;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (attacker!=null)
                attacker.interrupt();
            if (sender!=null)
                sender.interrupt();
        }

        System.out.println(Arrays.toString(AveQuantity));
        DrawGraph.drawGraph(AveQuantity);

    }

}
