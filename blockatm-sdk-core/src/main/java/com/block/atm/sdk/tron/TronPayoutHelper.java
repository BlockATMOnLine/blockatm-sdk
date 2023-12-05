package com.block.atm.sdk.tron;

import com.alibaba.fastjson.JSONObject;
import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Broadcast;
import org.spongycastle.util.encoders.Hex;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.utils.TronUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 15:13
 */
public class TronPayoutHelper extends TronHelper {

    public TronPayoutHelper(String url, String httpUrl) {
        super(url, httpUrl);
    }

    public String payout(String privateKey, String payoutContractAddress, String fromAddress, BigDecimal feeLimit, List<Address> tokenList, List<Uint256> amountList, List<Address> toList, List<Utf8String> business) throws IllegalAccessException, IOException, InstantiationException, NoSuchAlgorithmException {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Bool(Boolean.FALSE));
        inputParameters.add(new DynamicArray(tokenList));
        inputParameters.add(new DynamicArray(amountList));
        inputParameters.add(new DynamicArray(toList));
        inputParameters.add(new DynamicArray(business));
        String parameter = FunctionEncoder.encodeConstructor(inputParameters);
        String trans =  createTransaction(payoutContractAddress, BlockATMConstant.TRON_PAYOUT_TOKEN,parameter,fromAddress,feeLimit);
        JSONObject transaction = JSONObject.parseObject(trans);
        Protocol.Transaction tx = org.tron.utils.TronUtils.packTransaction(transaction.get("transaction").toString());
        // 签署交易
        byte[] bytes = TronUtils.signTransactionByte(tx.toByteArray(), ByteArray.fromHexString(privateKey));
        String raw = Hex.toHexString(bytes);
        Broadcast broadcast = sendRawTransaction(raw);
        return broadcast.getTxid();
    }




}
