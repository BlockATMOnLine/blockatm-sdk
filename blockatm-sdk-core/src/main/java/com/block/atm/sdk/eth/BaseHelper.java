package com.block.atm.sdk.eth;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 15:40
 */
public class BaseHelper {

    protected Web3j web3j;
    /**
     * eth node url
     * @param url
     */
    public BaseHelper(String url){
        this(Web3j.build(new HttpService(url)));
    }


    public BaseHelper(Web3j web3j){
        this.web3j = web3j;
    }


    public String callContract(String contractAddress, Function function) throws IOException {
        Transaction transaction = Transaction.createEthCallTransaction(null, contractAddress, FunctionEncoder.encode(function));
        return web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send().getValue();
    }

    /**
     * query Transaction by txId
     * @param txId
     * @return
     * @throws IOException
     */
    protected EthTransaction getTransactionBase(String txId) throws IOException {
        return web3j.ethGetTransactionByHash(txId).send();
    }


    protected EthGetTransactionReceipt getTransactionReceiptBase(String txId) throws IOException {
        return web3j.ethGetTransactionReceipt(txId).send();
    }
}
