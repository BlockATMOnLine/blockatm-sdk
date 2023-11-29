package com.block.atm.sdk.eth;

import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Payout;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
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
    public String payout(String privateKey,String payoutContractAddress,List<Payout> payoutList, List<Utf8String> business,int chainId) throws InterruptedException, ExecutionException, IOException {
        List<Type> inputArgs = new ArrayList<>();
        inputArgs.add(new Bool(Boolean.TRUE));
        inputArgs.add(new DynamicArray(payoutList));
        inputArgs.add(new DynamicArray(business));

        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        Function nameFunction = new Function(BlockATMConstant.PAYOUT_TOKEN, inputArgs, outputArgs2);
        String data = FunctionEncoder.encode(nameFunction);
        String txId = sign(privateKey,payoutContractAddress, BigInteger.ZERO,data,chainId);
        return txId;
    }
}
