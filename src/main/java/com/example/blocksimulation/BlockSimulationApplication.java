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
        int endNumber = 15;
        int value = 6;
        //hashSize的图像
        Variable variableHash = new Variable("hashSize",value);
        value = variableHash.getValue();
        double[] doubles = new BlockSimulationApplication().loopSimulation(variableHash, endNumber);
        DrawGraph.drawGraph(doubles,endNumber-1-value,variableHash.getValueName());

        //attackNumber的图像
        Variable variableAtt = new Variable("attackNumber",value);
        value = variableAtt.getValue();
        DrawGraph.drawGraph(new BlockSimulationApplication().loopSimulation(variableAtt, endNumber),endNumber-1-value,variableAtt.getValueName());

        //block的图像
        Variable variableBlock = new Variable("blockNumber",value);
        value = variableBlock.getValue();
        DrawGraph.drawGraph(new BlockSimulationApplication().loopSimulation(variableBlock, endNumber),endNumber-1-value,variableBlock.getValueName());

    }

    public double[] loopSimulation(Variable variable,int endNumber){


        Thread attacker = null;
        Thread sender = null;


        //控制每个不同数量攻击中样本的数量
        int numOfExecution = 1000;
        //存放循环后的结果
        //double[] result = new double[numOfExecution];

        //存放链路数据的集合
        List<Double> resultList = new ArrayList<>();

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
                    case "attackNum":
                        variable.setAttackNumber(temp);
                        break;
                }

                //链路总数量
                double numOfAllChain = 0;

                //存放链路数据的集合
                List<Double> resultListTest = new ArrayList<>();
                double total = 0;
                for (int i = 1; i < numOfExecution; i++) {
                    //控制模拟的链路总数量保持在1000附近
                    if (numOfAllChain >= 1000) {
                        System.out.println("Number of loops:"+i);
                        break;
                    }

                    //创建多线程
                    //variable.getBlockNumber(), variable.getHashSize(), variable.getAttackNumber()
                    attacker = new Thread(new Attacker(variable.getBlockNumber(), variable.getHashSize(), variable.getAttackNumber()), "Attacker");
                    sender = new Thread(new Sender(variable.getBlockNumber(), variable.getHashSize(), attacker), "Sender");
                    sender.start();

                    Thread.sleep(30);

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
                        for (int j = 2; j <= numOfChain; j++) {
                            resultList.add((double) temp);
                            resultListTest.add((double) temp);
                        }
                    }
                }

                System.out.println(variable.getValueName()+" is "+temp + ",number of error chains:" + resultListTest.size());
                System.out.println(variable.getValueName()+" is "+temp + ",number of all chains:" + numOfAllChain);

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
        return listToDoubleArray(resultList);
    }


    public double[] listToDoubleArray(List<Double> list){
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }



}
