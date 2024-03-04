package com.block.atm.sdk.eth;


import java.io.IOException;
import java.math.BigInteger;

public class Erc20HelperTest {

    public static void getBalance() throws IOException {
        Erc20Helper erc20Helper = new Erc20Helper("https://goerli.infura.io/v3/9aa3d95b3bc440fa88ea12eaa4456161");
        BigInteger balance = erc20Helper.getBalance("0x92eFDFa35c75B259375eBe0F84ee1d95db0489b6", "0x2d7FF2DC166aE09542C749bE052028e43825cde7");
        System.out.println(balance.toString());
    }

    public static void getDecimals() throws IOException {
        Erc20Helper erc20Helper = new Erc20Helper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        BigInteger decimals = erc20Helper.getDecimals("0x92eFDFa35c75B259375eBe0F84ee1d95db0489b6");
        System.out.println(decimals.toString());

    }

    public static void main(String[] args) throws IOException {
        getBalance();
        //getDecimals();
    }
}
