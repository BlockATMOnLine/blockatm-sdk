package com.block.atm.sdk.eth;


import com.alibaba.fastjson.JSON;
import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Payout;
import com.block.atm.sdk.eth.EthUtils;
import com.block.atm.sdk.eth.PayoutHelper;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class PayoutHelperTest {


    static String PRIVATEKEY = "6a2172d67536375ac4f97c75e857c49bfa764f68e6e326511c89468c9379b285";
    // Decimals 6
    static String USDT = "0x92eFDFa35c75B259375eBe0F84ee1d95db0489b6";
    // Decimals 6
    static String USDC = "0x2f96275bbb4a54714ef0251226c42811fb9f98aa";


     static void payout() throws InterruptedException, ExecutionException, IOException {

         PayoutHelper payout = new PayoutHelper("https://goerli.infura.io/v3/0c1f1f766ccb421289ada96e03e062b4");
//         String payoutGatewayAddress = "0x2bbe32650867682af3bc956c52395ad06dbfef7d";
         // 代付网关合约地址
         String payoutGatewayAddress = "0x8E5dF55ac224DB7424Fa8536edA9356F44474936";

         List<Payout> payoutList = new ArrayList<>();

         //  1 USDT = 1000000
         //
         String toAddress = "0x2d7FF2DC166aE09542C749bE052028e43825cde7";
//         String toAddress = "0x57609702E66D6deE9D1f3a9FaB376B95b9Ec9e02";
         Payout payoutUsdt = new Payout(USDT,new BigInteger("1000"),toAddress);
         Payout payoutUsdc = new Payout(USDC,new BigInteger("1000"),toAddress);

         payoutList.add(payoutUsdt);
         payoutList.add(payoutUsdc);
         List<Utf8String> business = new ArrayList<>();
         business.add(new Utf8String("q.no1"));
         business.add(new Utf8String("q.no2"));
         // testnet 1， mainnet 5
         int chainId = 5;

        String txId = payout.payout(PRIVATEKEY,payoutGatewayAddress,payoutList,business,chainId);
        System.out.println("txId->" + txId);
    }

    static void getTransaction() throws IOException {
        PayoutHelper payout = new PayoutHelper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        EthTransaction eth = payout.getTransaction("0x3de4eaaa624faba03aa52ffc978ddc34389560bf47a67df16412ec50833a8759");
        System.out.println("EthTransaction->" + eth.getTransaction().toString());

    }

    static void getTransactionReceipt() throws IOException {
        PayoutHelper payout = new PayoutHelper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        EthGetTransactionReceipt eth = payout.getTransactionReceipt("0x3de4eaaa624faba03aa52ffc978ddc34389560bf47a67df16412ec50833a8759");
        String status = eth.getTransactionReceipt().get().getStatus();
        System.out.println("txId is fail ->" + EthUtils.isFail(status));

    }
    static void txIsSuccessful() throws IOException {
        PayoutHelper payout = new PayoutHelper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        Boolean eth = payout.txIsSuccessful("0xedde8d2a32f30968fec6eebbdbb5b59765abda00cb9351c1f5e29eb65f91cef2");
        System.out.println("txId is fail ->" + eth);

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
         payout();
//        getPayoutBusinessAddress();
//        getTransaction();
//        getTransactionReceipt();
//        txIsSuccessful();

//        sendRawTransaction();
    }



    public static void getPayoutBusinessAddress() throws IOException {
        PayoutHelper payout = new PayoutHelper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
        //String payoutGatewayAddress = "0x2bbe32650867682af3bc956c52395ad06dbfef7d";
        // 代付网关合约地址
        String payoutGatewayAddress = "0x8E5dF55ac224DB7424Fa8536edA9356F44474936";
        Address address = payout.getPayoutBusinessAddress(payoutGatewayAddress);
        System.out.println(address.getValue());

        Address payoutAddr = payout.getPayoutAddress(address.getValue(),"0x2d7FF2DC166aE09542C749bE052028e43825cde7");
        System.out.println(payoutAddr.getValue());


    }


    public static RawTransaction createRawTransaction() throws IOException {
        PayoutHelper payout = new PayoutHelper("https://goerli.infura.io/v3/0c1f1f766ccb421289ada96e03e062b4");

        String payoutGatewayAddress = "0x8E5dF55ac224DB7424Fa8536edA9356F44474936";

        List<Payout> payoutList = new ArrayList<>();

        //  1 USDT = 1000000
        //
        String toAddress = "0x2d7FF2DC166aE09542C749bE052028e43825cde7";
//         String toAddress = "0x57609702E66D6deE9D1f3a9FaB376B95b9Ec9e02";
        Payout payoutUsdt = new Payout(USDT,new BigInteger("1000"),toAddress);
        Payout payoutUsdc = new Payout(USDC,new BigInteger("1000"),toAddress);

        payoutList.add(payoutUsdt);
        payoutList.add(payoutUsdc);
        List<Utf8String> business = new ArrayList<>();
        business.add(new Utf8String("q.no1"));
        business.add(new Utf8String("q.no2"));
        // testnet 1， mainnet 5
        int chainId = 5;

        List<Type> inputArgs = new ArrayList<>();
        inputArgs.add(new Bool(Boolean.TRUE));
        inputArgs.add(new DynamicArray(payoutList));
        inputArgs.add(new DynamicArray(business));

        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        Function nameFunction = new Function(BlockATMConstant.AUTO_PAYOUT_TOKEN, inputArgs, outputArgs2);
        String data = FunctionEncoder.encode(nameFunction);
        Credentials credentials = Credentials.create(PRIVATEKEY);

        RawTransaction rawTransaction = payout.createRawTransaction(credentials.getAddress(),payoutGatewayAddress, BigInteger.ZERO,data);
        System.out.println(JSON.toJSONString(rawTransaction));
        return rawTransaction;
    }

    public static void sendRawTransaction() throws IOException, ExecutionException, InterruptedException {
        PayoutHelper payout = new PayoutHelper("https://goerli.infura.io/v3/0c1f1f766ccb421289ada96e03e062b4");
        Credentials credentials = Credentials.create(PRIVATEKEY);
        // 使用创建交易的列子创建出交易
        RawTransaction rawTransaction = createRawTransaction();
        // Sign transaction with private key
        String hexValue = Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction,5,credentials));
        String txId = payout.sendRawTransaction(hexValue);
        System.out.println("txId is  ->" + txId);

    }
}