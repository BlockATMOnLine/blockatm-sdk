package com.block.atm.sdk.tron;

import com.block.atm.sdk.eth.Erc20Helper;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class Trc20HelperTest {
    public static void getBalance() throws IOException {
        Trc20Helper trc20Helper = new Trc20Helper("https://api.shasta.trongrid.io/jsonrpc");
        BigInteger balance = trc20Helper.getBalance("TEYKWmKvdCHX2NuX4tVmhxdN4P3PVjyMcu", "TUxNbLNpNxQafkHSMTUQf7AqdasdgeDSyp");
        System.out.println(balance.toString());
    }

    public static void getDecimals() throws IOException {
        Trc20Helper trc20Helper = new Trc20Helper("https://api.shasta.trongrid.io/jsonrpc");
        BigInteger decimals = trc20Helper.getDecimals("TEYKWmKvdCHX2NuX4tVmhxdN4P3PVjyMcu");
        System.out.println(decimals.toString());

    }

    public static void main(String[] args) throws IOException {
        getBalance();
        getDecimals();
    }
}
