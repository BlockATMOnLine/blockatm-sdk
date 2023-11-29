package com.block.atm.sdk.tron;

import com.block.atm.sdk.eth.BaseHelper;
import com.block.atm.sdk.eth.EthHelper;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;

import java.io.IOException;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 15:06
 */
public class TronHelper extends BaseHelper {

    private String httpUrl;

    /**
     *
     * @param url       json rpc url
     * @param httpUrl   http api url
     */
    public TronHelper(String url,String httpUrl) {
        super(url);
        this.httpUrl = httpUrl;
    }

    public TronHelper(Web3j web3j,String httpUrl) {
        super(web3j);
        this.httpUrl = httpUrl;
    }


    public EthTransaction getTransaction(String txId) throws IOException {
        return getTransactionBase(TronUtils.convertTxIdToEth(txId));
    }


    public EthGetTransactionReceipt getTransactionReceipt(String txId) throws IOException {
        return getTransactionReceiptBase(TronUtils.convertTxIdToEth(txId));
    }



}
