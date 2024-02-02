package com.block.atm.sdk.eth;

import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Payout;
import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 代付工具类
 * @author Lawrence
 * @description
 * @date 2023/11/27 下午2:37
 */
public class PayoutHelper extends EthHelper {

    public PayoutHelper(String url) {
        super(url);
    }

    public PayoutHelper(Web3j web3j) {
        super(web3j);
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

    /**
     * 代付合约批量出金
     * @param privateKey                财务人员地址的私钥
     * @param payoutContractAddress     代付合约地址
     * @param payoutList                代付指令
     * @param business                  业务编号（和指令数组大小需要保持一样的大小）
     * @param chainId                   链ID，如 1
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
   /* public String payout(String privateKey,String payoutContractAddress,List<Payout> payoutList, List<Utf8String> business,int chainId) throws InterruptedException, ExecutionException, IOException {
        List<Type> inputArgs = new ArrayList<>();
        inputArgs.add(new Bool(Boolean.TRUE));
        inputArgs.add(new DynamicArray(payoutList));
        inputArgs.add(new DynamicArray(business));

        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        Function nameFunction = new Function(BlockATMConstant.PAYOUT_TOKEN, inputArgs, outputArgs2);
        String data = FunctionEncoder.encode(nameFunction);
        String txId = sign(privateKey,payoutContractAddress, BigInteger.ZERO,data,chainId);
        return txId;
    }*/

    /**
     * 代付合约批量出金
     * @param privateKey                财务人员地址的私钥
     * @param payoutGatewayAddress     代付合约网关地址
     * @param payoutList                代付指令
     * @param business                  业务编号（和指令数组大小需要保持一样的大小）
     * @param chainId                   链ID，如 1
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public String payout(String privateKey,String payoutGatewayAddress,List<Payout> payoutList, List<Utf8String> business,int chainId) throws InterruptedException, ExecutionException, IOException {
        checkBalance(privateKey,payoutList,payoutGatewayAddress);
        List<Type> inputArgs = new ArrayList<>();
        inputArgs.add(new Bool(Boolean.TRUE));
        inputArgs.add(new DynamicArray(payoutList));
        inputArgs.add(new DynamicArray(business));

        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        Function nameFunction = new Function(BlockATMConstant.AUTO_PAYOUT_TOKEN, inputArgs, outputArgs2);
        String data = FunctionEncoder.encode(nameFunction);
        String txId = sign(privateKey,payoutGatewayAddress, BigInteger.ZERO,data,chainId);
        return txId;
    }

    private void checkBalance(String privateKey,List<Payout> payoutList,String payoutGatewayAddress) throws IOException {
        // 获取手续费
        BigInteger fee = BigInteger.valueOf(1000000).multiply( BigInteger.valueOf(Long.valueOf(payoutList.size())));
        Credentials credentials = Credentials.create(privateKey);
        Address payoutAddr = getPayoutAddress(getPayoutBusinessAddress(payoutGatewayAddress).getValue(),credentials.getAddress());
        Address feeToken = getPayoutFeeToken(payoutAddr.getValue());
        Erc20Helper erc20Helper = new Erc20Helper(web3j);
        boolean feeFlag = Boolean.FALSE;
        for (Payout value : payoutList){
            BigInteger temp = value.getAmount();
            if (StringUtils.equalsIgnoreCase(value.getTokenAddress(),feeToken.getValue())){
                temp = temp.add(fee);
                feeFlag = true;
            }
            BigInteger balance = erc20Helper.getBalance(value.getTokenAddress(),payoutAddr.getValue());
            if (balance.compareTo(temp) == -1){
                throw new RuntimeException("Insufficient balance : token  " + value.getTokenAddress());
            }
        }
        if (!feeFlag){
            BigInteger balance = erc20Helper.getBalance(feeToken.getValue(),payoutAddr.getValue());
            if (balance.compareTo(fee) == -1){
                throw new RuntimeException("Insufficient balance : token  " + feeToken.getValue());
            }
        }
    }
}
