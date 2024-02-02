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

    String GET_BUSINESS_ADDRESS = "getBusinessAddress";

    String GET_PAYOUT = "getPayout";
    String FEE_TOKEN_ADDRESS = "feeTokenAddress";

    /**
     * 交易状态成功
     */
    String TRANSACTION_SUCCESS = "0x1";
    /**
     * 交易状态失败
     */
    String TRANSACTION_FAIL = "0x0";


    /**
     * tron 创建交易的接口
     */
    String TRIGGERSMARTCONTRACT = "/wallet/triggersmartcontract";
    // 广播交易的接口
    String BROADCASTHEX = "/wallet/broadcasthex";
    String GETNOWBLOCK = "/wallet/getnowblock";
    // TUxNbLNpNxQafkHSMTUQf7AqdasdgeDSyp
    // ab56e9660036a20189ba763ca4175dcbb315092f722e8bc746181e62065c897b
    String TRON_PAYOUT_TOKEN = "payoutToken(bool,address[],uint256[],address[],string[])";

    // 提高5%
    int GAS_PRICE_TYPE_1 = 1;
    // 提高20%
    int GAS_PRICE_TYPE_2 = 2;
}
