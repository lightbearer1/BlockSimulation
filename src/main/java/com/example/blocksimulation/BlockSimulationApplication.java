package com.example.blocksimulation;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockSimulationApplication {



    public static void main(String[] args) throws InterruptedException {
        int blockNumber = 10;
        int hashSize = 8;
        Thread sender = new Thread(new Sender(blockNumber,hashSize),"Sender");
        Thread attacker = new Thread(new Attacker(blockNumber,hashSize),"Attacker");
        sender.start();
        attacker.join(10);
        Thread.sleep(100);
        Receiver.printBlockChain();

    }

}
