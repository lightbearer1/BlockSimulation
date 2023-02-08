package com.example.blocksimulation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

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
}
