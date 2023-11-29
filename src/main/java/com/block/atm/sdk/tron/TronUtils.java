package com.block.atm.sdk.tron;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 下午3:44
 */
public class TronUtils {

    public static String convertTxIdToEth(String txId){
        return "0x" + txId;
    }

    public static String convertAddressToEth(String trxAddress){
        String address = TrxUtils.toHexAddress(trxAddress);
        String tempUserAddress ="0x" + address.substring(2);
        return tempUserAddress;
    }

}
