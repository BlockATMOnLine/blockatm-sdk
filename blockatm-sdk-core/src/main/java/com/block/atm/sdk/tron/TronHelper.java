package com.block.atm.sdk.tron;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Broadcast;
import com.block.atm.sdk.eth.BaseHelper;
import com.block.atm.sdk.eth.EthUtils;
import com.block.atm.sdk.tron.core.HttpClientUtils;
import com.block.atm.sdk.tron.core.TrxBlock;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.ByteUtil;
import org.tron.protos.Protocol;
import org.tron.protos.contract.SmartContractOuterClass;
import org.tron.utils.TronUtils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;


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

    public <T> T doGet(String url, Map<String,String> header, Class<T> clz){
        String result = null;
        try {
            result = HttpClientUtils.doGet(url,header);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return JSON.parseObject(result, clz);
    }


    public EthTransaction getTransaction(String txId) throws IOException {
        return getTransactionBase(TronSDKUtils.convertTxIdToEth(txId));
    }


    public EthGetTransactionReceipt getTransactionReceipt(String txId) throws IOException {
        return getTransactionReceiptBase(TronSDKUtils.convertTxIdToEth(txId));
    }


    public TrxBlock getNowBlock(){
        return doGet(httpUrl + BlockATMConstant.GETNOWBLOCK,null,TrxBlock.class);
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

    public Protocol.Transaction createSmartTransaction(String from, String  data,String function_selector, String contract, BigDecimal feeLimit, TrxBlock trxBlock) {
        Protocol.BlockHeader.raw raw = getBlockHeader(trxBlock);
        Protocol.Transaction.Builder transactionBuilder = Protocol.Transaction.newBuilder();
        org.tron.protos.Protocol.Transaction.Contract.Builder contractBuilder = Protocol.Transaction.Contract.newBuilder();
        org.tron.protos.contract.SmartContractOuterClass.TriggerSmartContract.Builder triggerSmartContractBuilder = SmartContractOuterClass.TriggerSmartContract.newBuilder();

        byte[] input = Hex.decode(AbiUtil.parseMethod(function_selector, data, true));
        triggerSmartContractBuilder.setContractAddress(ByteString.copyFrom(TronUtils.decodeFromBase58Check(contract)));
        triggerSmartContractBuilder.setOwnerAddress(ByteString.copyFrom(TronUtils.decodeFromBase58Check(from)));
        triggerSmartContractBuilder.setData(ByteString.copyFrom(input));
        triggerSmartContractBuilder.setCallValue(0L);
        triggerSmartContractBuilder.setTokenId(Long.parseLong("0"));
        triggerSmartContractBuilder.setCallTokenValue(0L);
        Any any = Any.pack(triggerSmartContractBuilder.build());
        contractBuilder.setParameter(any);
        contractBuilder.setType(Protocol.Transaction.Contract.ContractType.TriggerSmartContract);
        transactionBuilder.getRawDataBuilder().addContract(contractBuilder).setTimestamp(System.currentTimeMillis()).setFeeLimit(TronSDKUtils.trxToSun(feeLimit).longValue()).setExpiration(System.currentTimeMillis() + 36000000L);
        byte[] blockHash = Sha256Sm3Hash.of(raw.toByteArray()).getBytes();
        byte[] blockNum = ByteArray.fromLong(raw.getNumber());
        System.out.println("block::::" + ByteArray.toHexString(blockHash));
        transactionBuilder.getRawDataBuilder().setRefBlockHash(ByteString.copyFrom(ByteArray.subArray(blockHash, 8, 16))).setRefBlockBytes(ByteString.copyFrom(ByteArray.subArray(blockNum, 6, 8)));
        return transactionBuilder.build();
    }

    public Broadcast sendRawTransaction(String raw) throws IllegalAccessException, IOException, InstantiationException {
        JSONObject param = new JSONObject();
        param.put("transaction", raw);
        Broadcast broadcast = postJson(httpUrl + BlockATMConstant.BROADCASTHEX, param, Broadcast.class);
        return broadcast;
    }


    private Protocol.BlockHeader.raw getBlockHeader(TrxBlock trxBlock){
        Protocol.BlockHeader.raw.Builder rawBuidler = Protocol.BlockHeader.raw.newBuilder();

        TrxBlock.BlockHeaderBean blockHeaderBean = trxBlock.getBlock_header();
        TrxBlock.BlockHeaderBean.RawDataBean rawDataBean = blockHeaderBean.getRaw_data();

        rawBuidler.setNumber(rawDataBean.getNumber().longValue());
        rawBuidler.setTxTrieRoot(ByteString.copyFrom(org.tron.common.utils.ByteArray.fromHexString(rawDataBean.getTxTrieRoot())));
        rawBuidler.setWitnessAddress(ByteString.copyFrom(org.tron.common.utils.ByteArray.fromHexString(rawDataBean.getWitness_address())));
        rawBuidler.setParentHash(ByteString.copyFrom(org.tron.common.utils.ByteArray.fromHexString(rawDataBean.getParentHash())));
        rawBuidler.setTimestamp(rawDataBean.getTimestamp());
        rawBuidler.setVersion(rawDataBean.getVersion());
        return rawBuidler.build();
    }
}
