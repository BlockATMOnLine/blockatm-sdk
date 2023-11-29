package com.block.atm.sdk.eth;

import com.block.atm.sdk.BlockATMConstant;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/28 下午4:22
 */
public class EthUtils {

    public static boolean isFail(String status){
        if (BlockATMConstant.TRANSACTION_FAIL.equals(status)){
            return true;
        } else if (BlockATMConstant.TRANSACTION_SUCCESS.equals(status)){
            return false;
        }
        return true;
    }
}
