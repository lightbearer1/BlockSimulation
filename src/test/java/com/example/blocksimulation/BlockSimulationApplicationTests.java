package com.example.blocksimulation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

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
}
