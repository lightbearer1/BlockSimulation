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



    @Getter
    //区块数量
    private int blockNumber = 10;


    @Getter
    //默认hash的大小
    private int hashSize = 12;

    @Getter
    //默认攻击区块的数量
    private int attackNumber  = pow(2,hashSize-3);;
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
                //this.attackNumber = pow(2,hashSize);
                break;
            case "hashSize":
                this.hashSize = value;
                //this.attackNumber = pow(2,hashSize);
                break;
            case "attackNumber":
                this.attackNumber = value;;
                break;
        }
        this.valueName = valueName;
        this.value = value;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
        //this.attackNumber = pow(2,hashSize);
    }

    public void setHashSize(int hashSize) {
        this.hashSize = hashSize;
        //this.attackNumber = pow(2,hashSize);
    }

    public void setAttackNumber(int attackNumber) {
        this.attackNumber = attackNumber;
    }
}
