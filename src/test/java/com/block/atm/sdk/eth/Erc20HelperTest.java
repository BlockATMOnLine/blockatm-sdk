package com.block.atm.sdk.eth;


import java.io.IOException;
import java.math.BigInteger;

class Erc20HelperTest {

    static void getBalance() throws IOException {
        Erc20Helper eth = new Erc20Helper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        //Erc20Helper eth = new Erc20Helper("https://goerli.infura.io/v3/007531226b37457c8e7ed5761729b7d9");
        BigInteger balance = eth.getBalance("0x92eFDFa35c75B259375eBe0F84ee1d95db0489b6","0x688911155d10155C77e0cDBe99dd9A7cD99dE7ff");
        System.out.println(balance.toString());
    }


    public static void main(String[] args) throws IOException {
        getDecimals();
    }

    static void getDecimals() throws IOException {

        Erc20Helper eth = new Erc20Helper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        //Erc20Helper eth = new Erc20Helper("https://goerli.infura.io/v3/007531226b37457c8e7ed5761729b7d9");
        BigInteger balance = eth.getDecimals("0x92eFDFa35c75B259375eBe0F84ee1d95db0489b6");
        System.out.println(balance.toString());
    }
}