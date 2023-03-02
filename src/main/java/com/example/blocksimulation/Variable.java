package com.example.blocksimulation;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: wyq
 * Date  : 2023/3/2 19:39
 * Desc  :
 */
public class Variable {

    @Setter
    @Getter
    //攻击区块的数量
    private int attackNumber = 10;
    @Setter
    @Getter
    //区块数量
    private int blockNumber = 10;
    @Setter
    @Getter
    //hash的大小
    private int hashSize = 8;
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
            case "attackNumer":
                this.attackNumber = value;
                break;
        }
        this.valueName = valueName;
        this.value = value;
    }

}
