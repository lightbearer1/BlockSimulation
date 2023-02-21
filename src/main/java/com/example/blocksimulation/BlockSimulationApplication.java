package com.example.blocksimulation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class BlockSimulationApplication {



    public static void main(String[] args) throws InterruptedException {
        //TODO 最外围添加一个循环，测试在不同参数下产生的不同的链路数量情况
        int blockNumber;
        int hashSize;
        int attackNumber;
        boolean isPrint = false;
        Thread attacker;
        Thread sender;

        Scanner scanner = new Scanner(System.in);
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
        }

        //创建多线程
        attacker = new Thread(new Attacker(blockNumber,hashSize,attackNumber,isPrint),"Attacker");
        sender = new Thread(new Sender(blockNumber,hashSize,attacker),"Sender");
        sender.start();


        Thread.sleep(100);
        log.info("_______________________________________________________________________");
        //System.out.println("_______________________________________________________________________");
        //Thread.sleep(2000);
        log.info("after attack,the received message is:");
        //System.out.println("after attack,the received message is:");
        int i = Receiver.printBlockChain();

    }

}
