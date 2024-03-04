package com.block.atm.sdk.eth;

import com.block.atm.sdk.BlockATMConstant;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/27 10:58
 */
public class EthHelper extends BaseHelper {

    BigInteger maxGasLimit = new BigInteger("10000000");
    /**
     * eth node url
     * @param url
     */
    public EthHelper(String url){
        super(url);
    }


    public EthHelper(Web3j web3j){
        super(web3j);
    }

    /**
     * query Transaction by txId
     * @param txId
     * @return
     * @throws IOException
     */
    public EthTransaction getTransaction(String txId) throws IOException {
        return web3j.ethGetTransactionByHash(txId).send();
    }

    /**
     * query Transaction by txId (Including transaction status, transaction gas usage, log events, etc.)
     * @param txId           txId
     * @return
     * @throws IOException
     */
    public EthGetTransactionReceipt getTransactionReceipt(String txId) throws IOException {
        return web3j.ethGetTransactionReceipt(txId).send();
    }

    /**
      * Determine whether the transaction is successful
     * @param txId
     * @return
     * @throws IOException
     */
    public boolean txIsSuccessful(String txId) throws IOException {
        EthGetTransactionReceipt receipt = getTransactionReceipt(txId);
        TransactionReceipt transactionReceipt = receipt.getTransactionReceipt().get();
        return  !EthUtils.isFail(transactionReceipt.getStatus());
    }

    /**
     * 加速交易
     * @param txId
     * @return
     * @throws IOException
     */
    public RawTransaction speedCreateRawTransaction(String from,String txId,int type) throws IOException {
        org.web3j.protocol.core.methods.response.Transaction old = web3j.ethGetTransactionByHash(txId).send().getTransaction().get();
        if (!StringUtils.equalsIgnoreCase(from,old.getFrom())){
            throw new RuntimeException("from address is error");
        }
        BigInteger gasPrice = getGasPrice(type);
        Transaction transaction = new Transaction(old.getFrom(),old.getNonce(),gasPrice,maxGasLimit,old.getTo(),old.getValue(),old.getInput());
        BigInteger gas;
        try {
            gas = web3j.ethEstimateGas(transaction).send().getAmountUsed();
        } catch (Exception ex){
            throw ex;
        }

        BigDecimal bGas = new BigDecimal(gas);
        return RawTransaction.createTransaction(
                old.getNonce(),
                gasPrice,
                bGas.toBigInteger(),
                old.getTo(),
                old.getValue(),
                old.getInput());
    }

    private BigInteger getGasPrice(int type) throws IOException {
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();

        switch (type){
            case BlockATMConstant.GAS_PRICE_TYPE_1:
                // 提高5%
                gasPrice = new BigDecimal(gasPrice).multiply(new BigDecimal("1.05")).toBigInteger();break;
            case BlockATMConstant.GAS_PRICE_TYPE_2:
                // 提高20%
                gasPrice = new BigDecimal(gasPrice).multiply(new BigDecimal("1.2")).toBigInteger();break;
        }

        return gasPrice;
    }

    /**
     * 创建ETH的交易
     * @param fromAddress
     * @param to
     * @param amount
     * @param data
     * @param type  0: 提高gasPrice, 1:提高5% 2:提高20%
     * @return
     * @throws IOException
     */
    public RawTransaction createRawTransactionGas(String fromAddress,String to,BigInteger amount,String data,int type) throws IOException {

        BigInteger gasPrice = getGasPrice(type);

        BigInteger nonce = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).send().getTransactionCount();
        Transaction transaction = new Transaction(fromAddress,nonce,gasPrice,maxGasLimit,to,amount,data);

        BigInteger gas;
        try {
            gas = web3j.ethEstimateGas(transaction).send().getAmountUsed();
        } catch (Exception ex){
            throw ex;
        }

        BigDecimal bGas = new BigDecimal(gas);
        return RawTransaction.createTransaction(
                nonce,
                gasPrice,
                bGas.toBigInteger(),
                to,
                amount,
                data);
    }

    /**
     *  创建ETH的交易， gasPrice 提高5%
     * @param fromAddress       Transaction initiation address
     * @param to                Deposit address
     * @param amount            Amount of the transaction
     * @param data
     * @return
     * @throws IOException
     */
    public RawTransaction createRawTransaction(String fromAddress,String to,BigInteger amount,String data) throws IOException {
        return createRawTransactionGas(fromAddress,to,amount,data,1);
    }

    /**
     *  sign eth transaction and Broadcast transaction
     * @param privateKey        The private key of the sending address
     * @param to
     * @param fromAmount        eth amount
     * @param data              transaction
     * @param chainId
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String sign(String privateKey, String to, BigInteger fromAmount, String data, int chainId) throws IOException, ExecutionException, InterruptedException {

        Credentials credentials = Credentials.create(privateKey);

        RawTransaction rawTransaction = createRawTransaction(credentials.getAddress(),to,fromAmount,data);
        // Sign transaction with private key
        String hexValue = Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction,chainId,credentials));
        // Broadcast signed transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction.getTransactionHash();
    }

    /**
     * 加速交易
     * @param privateKey
     * @param txId
     * @param chainId
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String speedTransaction(String privateKey,String txId,int chainId) throws IOException, ExecutionException, InterruptedException {
        Credentials credentials = Credentials.create(privateKey);
        RawTransaction rawTransaction = speedCreateRawTransaction(credentials.getAddress(),txId,1);
        String hexValue = Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction,chainId,credentials));
        // Broadcast signed transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction.getTransactionHash();
    }

    public String sendRawTransaction(String hexValue) throws IOException, ExecutionException, InterruptedException {
        // Broadcast signed transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction.getTransactionHash();
    }


}
