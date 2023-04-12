package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

import static org.apache.commons.math3.util.ArithmeticUtils.pow;

/**
 * Author: wyq
 * Date  : 2023/3/2 19:39
 * Desc  :
 */
public class Variable {


    @Setter
    @Getter
    //区块数量
    private int blockNumber = 10;

    @Setter
    @Getter
    //默认hash的大小
    private int hashSize = 12;
    @Setter
    @Getter
    //默认攻击区块的数量
    private int attackNumber = pow(2,hashSize);
    @Setter
    @Getter
    private String valueName;
    @Getter
    @Setter
    private int value;

    public Variable(){

    }
    public Variable(String valueName,int value){
        switch (valueName){
            case "blockNumber":
                this.blockNumber = value;
                break;
            case "hashSize":
                this.hashSize = value;
                break;
            case "attackNumber":
                this.attackNumber = value;
                break;
        }
        this.valueName = valueName;
        this.value = value;
    }

}
