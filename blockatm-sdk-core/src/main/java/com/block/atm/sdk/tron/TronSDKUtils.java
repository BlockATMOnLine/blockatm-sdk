package com.block.atm.sdk.tron;

import org.tron.utils.TronUtils;

import java.math.BigDecimal;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 下午3:44
 */
public class TronSDKUtils {

    private static BigDecimal decimal = new BigDecimal("1000000");


    public static String convertTxIdToEth(String txId){
        return "0x" + txId;
    }

    public static String convertAddressToEth(String trxAddress){
        String address = TronUtils.toHexAddress(trxAddress);
        String tempUserAddress ="0x" + address.substring(2);
        return tempUserAddress;
    }

    public static String convertAddressToTrx(String ethAddress){
        String address = ethAddress.substring(2);
        address = TronUtils.hexToAddress("41" + address);
        return address;
    }

    public static BigDecimal trxToSun(BigDecimal trx) {
        return trx.multiply(decimal);
    }

}
