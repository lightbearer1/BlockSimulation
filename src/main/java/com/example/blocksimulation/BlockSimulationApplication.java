package com.example.blocksimulation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
@Slf4j
public class BlockSimulationApplication {
    static boolean overThread = false;


    public static void main(String[] args) throws InterruptedException {
        log.debug("<---------------New Function--------------->");
        //TODO 最外围添加一个循环，测试在不同参数下产生的不同的链路数量情况
        int endNumber =8;
        int value = 7;
        //创建集合存放要模拟的区间,作为图表的x轴
        List<Integer> listXAxis = new ArrayList<>();
        for (int i = value;i < endNumber;i++){
            listXAxis.add(i);
        }
        //创建存放数据集合的集合，作为图表的y轴
        Map<String,List<Double>> listYAxis = new HashMap<>();

        //hashSize的数据
        Variable variableHash = new Variable("hashSize",value);
        //调用模拟函数,返回结果集，包含了不同参数下的错误链路概率
        List<Double> listForHashSize = new BlockSimulationApplication().loopSimulation(variableHash, endNumber);
        //将模拟结果存放到集合中
        listYAxis.put(variableHash.getValueName(), listForHashSize);

        //attackNumber的数据
        Variable variableAtt = new Variable("attackNumber",value);
        List<Double> listAttackNumber = new BlockSimulationApplication().loopSimulation(variableAtt, endNumber);
        listYAxis.put(variableAtt.getValueName(), listAttackNumber);

        //block的数据
        Variable variableBlock = new Variable("blockNumber",value);
        List<Double> listBlockNumber = new BlockSimulationApplication().loopSimulation(variableBlock, endNumber);
        listYAxis.put(variableBlock.getValueName(), listBlockNumber);

        //调用画图函数
        DrawGraph.drawGraph2(listXAxis,listYAxis);
        log.debug("          End of the function\n\n");
        //DrawGraph.drawGraph(new BlockSimulationApplication().loopSimulation(variableBlock, endNumber),endNumber-1-value,variableBlock.getValueName());

    }

    /**
     * 攻击模拟
     * @param variable 一个特殊对象，存放了各个属性变量
     * @param endNumber 攻击模拟的上限
     * @return 出现错误链路的概率
     */
    public List<Double> loopSimulation(Variable variable, int endNumber) {


        //创建一个线程，用于发送区块和攻击
        Thread attacker = null;
        Thread sender = null;


        //控制循环的最大次数
        int numOfExecution = 1;
        //存放循环后的结果
        //double[] result = new double[numOfExecution];

        //存放所有的链路数据的集合
        //List<Double> resultList = new ArrayList<>();

        //错误链路的数量
        double numOfIllegalChain = 0;


        //作为结果集存放不通参数下的错误链路概率
        List<Double> probabilityList = null;
        try {
            //TODO 把每条链路的详情打印到日志
            probabilityList = new ArrayList<>();

            //判断选择的是什么变量
            for (int temp = variable.getValue(); temp < endNumber; temp++) {

                switch (variable.getValueName()) {
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

                //存放每一次循环的链路数据
                List<Double> resultListTest = new ArrayList<>();
                double total = 0;
                for (int i = 1; i <= numOfExecution; i++) {
                    //控制模拟的链路总数量
                    /*
                      使用蒙特卡洛方法计算该次数：
                      期望值：E(x) = (x1 + x2 + ... + xn) / n ，n为模拟次数
                      标准差：SE = s / sqrt(n)
                      期望次数N = (z * SE / ε) ^ 2，z表示正态分布的置信水平
                      如果置信水平为90%，那么相应的z值为1.645。
                      因此，期望次数N可以计算为：
                      N = (1.645 * s / (0.01 * E(x))) ^ 2
                      将给定的标准差s=7.544和期望值E(x)=0.479代入公式中，可得：
                      N = [(1.645 * 7.544) / 0.479]^2 = 429.54
                      ________________________________________
                      Calculate the number using the Monte Carlo method:
                      Expected value: E (x)=(x1+x2+...+xn)/n, where n is the number of simulations
                      Standard deviation: SE=s/sqrt (n)
                      Expected number of times N=(z * SE/ ε) ^  2, z represents the confidence level of the normal distribution
                      If the confidence level is 90%, the corresponding z value is 1.645.
                      Therefore, the expected number N can be calculated as:
                      N = (1.645 * s / (0.01 * E(x))) ^ 2
                      By substituting the given standard deviation s=7.544 and the expected value E (x)=0.479 into the formula, it can be obtained that:
                      N = [(1.645 * 7.544) / 0.479]^2 = 429.54
                     */
                    if (numOfAllChain >= 430) {
                        //求得每次模拟结果的数学期望
                        double ex = ((double) resultListTest.size())/(i-1);
                        //计算每次模拟的标准差
                        double variance = calculateVariance(listToDoubleArray(resultListTest), ex);
                        System.out.println("__________________________");
                        System.out.println("Number of loops:" + (i-1));
                        System.out.println("Expected value is:" + ex);
                        System.out.println("Variance is :"+variance);
                        break;
                    }

                    //创建多线程
                    //variable.getBlockNumber(), variable.getHashSize(), variable.getAttackNumber()
                    attacker = new Thread(new Attacker(variable.getBlockNumber(), variable.getHashSize(), variable.getAttackNumber()), "Attacker");
                    sender = new Thread(new Sender(variable.getBlockNumber(), variable.getHashSize(), attacker), "Sender");
                    sender.start();

                    Thread.sleep(30);

                    //返回结果集，里面包含非法链路数量以及链路数组数据
                    Map<String, Object> result = Receiver.printBlockChain(variable.getBlockNumber());
                    //获得当前链路数组里所有链路的数量
                    int numOfChain = (int)result.get("numberOfAllChain");
                    //List<List<Block>> chainList = (List) result.get("chain");
                    //累计总链路数量
                    numOfAllChain += numOfChain;

                    //初始化接收者数据---------------------------------------
                    Receiver.mainBlockChain = new CopyOnWriteArrayList<>();
                    Receiver.blockChainNumber = new CopyOnWriteArrayList<>();
                    Receiver.index = 0;
                    Receiver.previousHash = new boolean[]{true, true, true, true};

                    //total+=numOfChain;
                    //log.info(String.valueOf(total));
                    //result[i] = numOfChain;

                   /* //对链路进行再次迭代
                    for (List<Block> list:chainList) {
                        //判断链路数组中的链路是否存在非法区块(检查链路的第一个区块isLegal属性)
                        if (!list.get(0).isLegal()){
                            resultListTest.add((double) temp);
                        }
                    }
                    *//*if (numOfChain >= 1) {
                        if (numOfChain==1){


                        }
                        for (int j = 2; j <= numOfChain; j++) {
                            //resultList.add((double) temp);
                            resultListTest.add((double) temp);
                        }
                    }*/
                    //检查当前参数出现了多少条非法链路
                    for (int j = 0; j < (int)result.get("illegalChainNumber"); j++) {
                        resultListTest.add((double) temp);
                    }
                }

                //计算错误链路的概率
                double probability = 0;
                if(resultListTest.size()==0&&numOfAllChain==0){
                    probability = 0;
                }
                else
                    probability = resultListTest.size() / numOfAllChain;
                probabilityList.add(probability);

                System.out.println(variable.getValueName() + " is " + temp + ",number of error chains:" + resultListTest.size());
                System.out.println(variable.getValueName() + " is " + temp + ",number of all chains:" + numOfAllChain);
                System.out.println(variable.getValueName() + " is " + temp + ",Probability of erroneous links :" + probability);
                System.out.println("——————————————————————————————");

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
        return probabilityList;
    }


    //将List<Double>转换为double[]
    public double[] listToDoubleArray(List<Double> list){
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * 用于计算模拟结果的标准差
     * @param results results是一个double类型的数组，包含了n次模拟的结果
     * @param mean mean是模拟结果的期望值
     * @return 标准差
     */
    public double calculateVariance(double[] results, double mean) {
        double variance = 0;
        for (double result : results) {
            variance += Math.pow(result - mean, 2);
        }
        return Math.sqrt(variance / (results.length - 1));
    }



}
