package com.block.atm.sdk.tron;

import com.alibaba.fastjson.JSONObject;
import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Broadcast;
import com.block.atm.sdk.eth.Erc20Helper;
import org.apache.commons.lang3.StringUtils;
import org.spongycastle.util.encoders.Hex;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.utils.TronUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    public Address getPayoutAddress(String payoutBusinessAddress,String autoAddress) throws IOException {
        List<Type> inputArgs2 = new ArrayList<>();
        inputArgs2.add(new Address(autoAddress));
        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        outputArgs2.add(new TypeReference<Address>() {});
        Function nameFunction = new Function(BlockATMConstant.GET_PAYOUT, inputArgs2, outputArgs2);
        String value = callContract(payoutBusinessAddress,nameFunction);
        List<Type> list = FunctionReturnDecoder.decode(value, nameFunction.getOutputParameters());
        return (Address) list.get(0);
    }

    public Address getPayoutFeeToken(String payoutAddress) throws IOException {
        List<Type> inputArgs2 = new ArrayList<>();
        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        outputArgs2.add(new TypeReference<Address>() {});
        Function nameFunction = new Function(BlockATMConstant.FEE_TOKEN_ADDRESS, inputArgs2, outputArgs2);
        String value = callContract(payoutAddress,nameFunction);
        List<Type> list = FunctionReturnDecoder.decode(value, nameFunction.getOutputParameters());
        return (Address) list.get(0);
    }

    public Address getPayoutBusinessAddress(String payoutGatewayAddress) throws IOException {
        List<Type> inputArgs2 = new ArrayList<>();
        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        outputArgs2.add(new TypeReference<Address>() {});
        Function nameFunction = new Function(BlockATMConstant.GET_BUSINESS_ADDRESS, inputArgs2, outputArgs2);
        String value = callContract(payoutGatewayAddress,nameFunction);
        List<Type> list = FunctionReturnDecoder.decode(value, nameFunction.getOutputParameters());
        return (Address) list.get(0);
    }

    public String payout(String privateKey, String payoutContractAddress, String fromAddress, BigDecimal feeLimit, List<Address> tokenList, List<Uint256> amountList, List<Address> toList, List<Utf8String> business) throws IllegalAccessException, IOException, InstantiationException, NoSuchAlgorithmException {
        checkBalance(fromAddress,tokenList,amountList,payoutContractAddress);
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


    private void checkBalance(String fromAddress,List<Address> tokenList, List<Uint256> amountList,String payoutGatewayAddress) throws IOException {
        // 获取手续费
        BigInteger fee = BigInteger.valueOf(1000000).multiply( BigInteger.valueOf(Long.valueOf(tokenList.size())));
        Address payoutAddr = getPayoutAddress(getPayoutBusinessAddress(TronSDKUtils.convertAddressToEth(payoutGatewayAddress)).getValue(),TronSDKUtils.convertAddressToEth(fromAddress));
        Address feeToken = getPayoutFeeToken(payoutAddr.getValue());
        Erc20Helper erc20Helper = new Erc20Helper(web3j);
        boolean feeFlag = Boolean.FALSE;
        for (int i = 0;i<tokenList.size();i++){
            BigInteger temp = amountList.get(i).getValue();
            Address value = tokenList.get(i);
            if (StringUtils.equalsIgnoreCase(value.getValue(),feeToken.getValue())){
                temp = temp.add(fee);
                feeFlag = true;
            }
            BigInteger balance = erc20Helper.getBalance(value.getValue(),payoutAddr.getValue());
            if (balance.compareTo(temp) == -1){
                throw new RuntimeException("Insufficient balance : token  " + TronSDKUtils.convertAddressToTrx(value.getValue()));
            }
        }
        if (!feeFlag){
            BigInteger balance = erc20Helper.getBalance(feeToken.getValue(),payoutAddr.getValue());
            if (balance.compareTo(fee) == -1){
                throw new RuntimeException("Insufficient balance : token  " + TronSDKUtils.convertAddressToTrx(feeToken.getValue()));
            }
        }
    }



}
