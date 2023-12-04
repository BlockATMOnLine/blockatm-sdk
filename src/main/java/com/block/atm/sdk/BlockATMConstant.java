package com.block.atm.sdk;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/27 下午2:11
 */
public interface BlockATMConstant {


    String GET_BALANCE_OF = "balanceOf";

    String DECIMALS = "decimals";


    String PAYOUT_TOKEN = "payoutToken";
    String AUTO_PAYOUT_TOKEN = "payoutToken";


    /**
     * 交易状态成功
     */
    String TRANSACTION_SUCCESS = "0x1";
    /**
     * 交易状态失败
     */
    String TRANSACTION_FAIL = "0x0";


}
