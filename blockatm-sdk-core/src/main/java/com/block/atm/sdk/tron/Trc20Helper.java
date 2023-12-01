package com.block.atm.sdk.tron;

import com.block.atm.sdk.eth.Erc20Helper;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 下午3:39
 */
public class Trc20Helper extends Erc20Helper {

    public Trc20Helper(String url) {
        super(url);
    }

    public Trc20Helper(Web3j web3j) {
        super(web3j);
    }


    @Override
    public BigInteger getBalance(String contractAddress, String who) throws IOException {
        return super.getBalance(TronSDKUtils.convertAddressToEth(contractAddress), TronSDKUtils.convertAddressToEth(who));
    }

    @Override
    public BigInteger getDecimals(String contractAddress) throws IOException {
        return super.getDecimals(TronSDKUtils.convertAddressToEth(contractAddress));
    }
}
