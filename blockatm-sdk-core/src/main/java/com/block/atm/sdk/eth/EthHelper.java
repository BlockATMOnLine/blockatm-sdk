package com.block.atm.sdk.eth;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
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


    public EthHelper(String url) {
        super(url);
    }

    public EthHelper(Web3j web3j) {
        super(web3j);
    }


    public EthTransaction getTransaction(String txId) throws IOException {
        return getTransactionBase(txId);
    }


    public EthGetTransactionReceipt getTransactionReceipt(String txId) throws IOException {
        return getTransactionReceiptBase(txId);
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

        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        gasPrice = new BigDecimal(gasPrice).multiply(new BigDecimal("1.3")).toBigInteger();

        BigInteger nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send().getTransactionCount();

        Transaction transaction = new Transaction(credentials.getAddress(),nonce,gasPrice,new BigInteger("3333333"),to,fromAmount,data);
        BigInteger gas;
        try {
            gas = web3j.ethEstimateGas(transaction).send().getAmountUsed();
        } catch (Exception ex){
            throw ex;
        }

        BigDecimal bGas = new BigDecimal(gas);
        bGas = bGas.add(bGas.multiply(new BigDecimal("0.3")));

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                bGas.toBigInteger(),
                to,
                fromAmount,
                data);
        // Sign transaction with private key
        String hexValue = Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction,chaiId,credentials));
        // Broadcast signed transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction.getTransactionHash();
    }

}
