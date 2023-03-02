package com.example.blocksimulation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
@Slf4j
public class BlockSimulationApplication {
    static boolean overThread = false;


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
        int numOfExecution = 20;
        //每次攻击中样本的平均数据
        double[] AveQuantity = new double[numOfExecution];

        try {
            for (int j = 0;j<attackNumber;j++){
                double total = 0;
                for (int i = 1;i<numOfExecution;i++){

                    //创建多线程
                    attacker = new Thread(new Attacker(blockNumber,hashSize,attackNumber,isPrint),"Attacker");
                    sender = new Thread(new Sender(blockNumber,hashSize,attacker),"Sender");
                    sender.start();

                    Thread.sleep(50);
                    ////log.info("_______________________________________________________________________");
                    //System.out.println("_______________________________________________________________________");
                    //Thread.sleep(2000);
                    ////log.info("after attack,the received message is:");
                    //System.out.println("after attack,the received message is:");
                    //返回生成的链路数量
                    int numOfChain = Receiver.printBlockChain();
                    //log.warn(String.valueOf(numOfChain));
                    Receiver.mainBlockChain=new CopyOnWriteArrayList<>();
                    Receiver.blockChainNumber=new CopyOnWriteArrayList<>();
                    Receiver.index=0;
                    Receiver.previousHash= new boolean[]{true, true, true, true};
                    //total+=numOfChain;
                    //log.info(String.valueOf(total));
                    AveQuantity[i] = numOfChain;
                }
                //AveQuantity[j] = total/numOfExecution;
                System.out.println(Arrays.toString(AveQuantity));
                DrawGraph.drawGraph(AveQuantity,j+1);
                //log.debug(String.valueOf(AveQuantity[attackNumber]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (attacker!=null){
                overThread = true;
                attacker.interrupt();
            }
            if (sender!=null){
                overThread = true;
                sender.interrupt();
            }

        }

        //System.out.println(Arrays.toString(AveQuantity));
        //DrawGraph.drawGraph(AveQuantity,attackNumber);
        /*Thread thread = Thread.currentThread();
        int nbThreads =  Thread.getAllStackTraces().keySet().size();
        System.out.println(thread);
        System.out.println(nbThreads);
        int nbRunning = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getState()==Thread.State.RUNNABLE) nbRunning++;
        }
        System.out.println(nbRunning);
        System.out.println(attacker.isInterrupted());
        System.out.println(sender.isInterrupted());*/

    }

    public double[] loopSimulation(){
        int blockNumber = 10;
        int hashSize = 6;
        int attackNumber = 10;
        boolean isPrint = false;
        Thread attacker = null;
        Thread sender = null;

        //控制每个不同数量攻击中样本的数量
        int numOfExecution = 1000;
        //每次攻击中样本的平均数据
        double[] AveQuantity = new double[numOfExecution];
        //错误链路的数量
        double numOfIllegalChain = 0;
        //存放链路数据的集合
        List<Double> result = new ArrayList();

        try {
            //for (int j = 0;j<attackNumber;j++){
                double total = 0;
                for (int i = 1;i<numOfExecution;i++){

                    //创建多线程
                    attacker = new Thread(new Attacker(blockNumber,hashSize,attackNumber,isPrint),"Attacker");
                    sender = new Thread(new Sender(blockNumber,hashSize,attacker),"Sender");
                    sender.start();

                    Thread.sleep(50);
                    ////log.info("_______________________________________________________________________");
                    //System.out.println("_______________________________________________________________________");
                    //Thread.sleep(2000);
                    ////log.info("after attack,the received message is:");
                    //System.out.println("after attack,the received message is:");
                    //返回生成的链路数量
                    int numOfChain = Receiver.printBlockChain();
                    //log.warn(String.valueOf(numOfChain));

                    //初始化接收者数据---------------------------------------
                    Receiver.mainBlockChain=new CopyOnWriteArrayList<>();
                    Receiver.blockChainNumber=new CopyOnWriteArrayList<>();
                    Receiver.index=0;
                    Receiver.previousHash= new boolean[]{true, true, true, true};

                    //total+=numOfChain;
                    //log.info(String.valueOf(total));
                    AveQuantity[i] = numOfChain;
                    if (numOfChain>1){
                        for (int j = 1; j <= numOfChain; j++) {
                            result.add((double) hashSize);
                        }
                    }
                }
                //AveQuantity[j] = total/numOfExecution;
                System.out.println(result);
                //DrawGraph.drawGraph(AveQuantity,j+1);
                //log.debug(String.valueOf(AveQuantity[attackNumber]));
            //}
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (attacker!=null){
                overThread = true;
                attacker.interrupt();
            }
            if (sender!=null){
                overThread = true;
                sender.interrupt();
            }

        }
        return AveQuantity;
    }

}
