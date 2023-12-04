package com.block.atm.sdk.eth;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
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
import org.web3j.protocol.http.HttpService;
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
public class EthHelper {

    private Web3j web3j;
    /**
     * eth node url
     * @param url
     */
    public EthHelper(String url){
        this(Web3j.build(new HttpService(url)));
    }


    public EthHelper(Web3j web3j){
        this.web3j = web3j;
    }

    /**
     *
     * @param contractAddress
     * @param function
     * @return
     * @throws IOException
     */
    protected String callContract(String contractAddress, Function function) throws IOException {
        Transaction transaction = Transaction.createEthCallTransaction(null, contractAddress, FunctionEncoder.encode(function));
        return web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send().getValue();
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
     *  Create an unsigned transaction
     * @param fromAddress       Transaction initiation address
     * @param to                Deposit address
     * @param amount            Amount of the transaction
     * @param data
     * @return
     * @throws IOException
     */
    public RawTransaction createRawTransaction(String fromAddress,String to,BigInteger amount,String data) throws IOException {
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();

        BigInteger nonce = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).send().getTransactionCount();
        Transaction transaction = new Transaction(fromAddress,nonce,gasPrice,new BigInteger("3333333"),to,amount,data);

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
     *  sign eth transaction and Broadcast transaction
     * @param privateKey        The private key of the sending address
     * @param to
     * @param fromAmount        eth amount
     * @param data              transaction
     * @param chaiId
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String sign(String privateKey, String to, BigInteger fromAmount, String data, int chaiId) throws IOException, ExecutionException, InterruptedException {

        Credentials credentials = Credentials.create(privateKey);

        RawTransaction rawTransaction = createRawTransaction(credentials.getAddress(),to,fromAmount,data);
        // Sign transaction with private key
        String hexValue = Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction,chaiId,credentials));
        // Broadcast signed transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction.getTransactionHash();
    }

}
