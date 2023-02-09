package com.example.blocksimulation;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BlockSimulationApplication {



    public static void main(String[] args) throws InterruptedException {
        int blockNumber = 10;
        int hashSize = 8;
        int attackNumber = 10;
        boolean isPrint = false;

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
        Thread sender = new Thread(new Sender(blockNumber,hashSize),"Sender");
        Thread attacker = new Thread(new Attacker(blockNumber,hashSize,attackNumber,isPrint),"Attacker");
        sender.start();
        attacker.start();
        Thread.sleep(100);
        System.out.println("_______________________________________________________________________");
        System.out.println("after attack,the received meassage is:");
        Receiver.printBlockChain();

    }

}
