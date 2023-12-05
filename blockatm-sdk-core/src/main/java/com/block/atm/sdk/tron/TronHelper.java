package com.block.atm.sdk.tron;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Broadcast;
import com.block.atm.sdk.eth.BaseHelper;
import com.block.atm.sdk.tron.core.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.tron.utils.TronUtils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;

import java.io.IOException;
import java.math.BigDecimal;


/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 15:06
 */
@Slf4j
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

    public static  <T> T postJson(String url, Object jsonBody, Class<T> clz) throws IOException, IllegalAccessException, InstantiationException {
        String result = HttpClientUtils.postJson(url, JSON.toJSONString(jsonBody));
        if (clz.newInstance() instanceof String){
            return (T) result;
        }
        return JSON.parseObject(result, clz);
    }


    public EthTransaction getTransaction(String txId) throws IOException {
        return getTransactionBase(TronSDKUtils.convertTxIdToEth(txId));
    }


    public EthGetTransactionReceipt getTransactionReceipt(String txId) throws IOException {
        return getTransactionReceiptBase(TronSDKUtils.convertTxIdToEth(txId));
    }



    public String createTransaction(String contractAddress,String function_selector,String parameter,String fromAddress,BigDecimal feeLimit) throws IllegalAccessException, IOException, InstantiationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contract_address", TronUtils.toHexAddress(contractAddress));
        jsonObject.put("function_selector", function_selector);
        jsonObject.put("parameter", parameter);
        jsonObject.put("owner_address", TronUtils.toHexAddress(fromAddress));
        jsonObject.put("call_value", 0);
        jsonObject.put("fee_limit", TronSDKUtils.trxToSun(feeLimit).longValue());
        String trans = postJson(httpUrl + BlockATMConstant.TRIGGERSMARTCONTRACT,jsonObject,String.class);
        return trans;
    }


    public Broadcast sendRawTransaction(String raw) throws IllegalAccessException, IOException, InstantiationException {
        JSONObject param = new JSONObject();
        param.put("transaction", raw);
        Broadcast broadcast = postJson(httpUrl + BlockATMConstant.BROADCASTHEX, param, Broadcast.class);
        return broadcast;
    }


}
