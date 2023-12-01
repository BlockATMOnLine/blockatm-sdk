package com.block.atm.sdk.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lawrence
 * @description
 * @date 2023/12/1 下午1:47
 */
@Setter
@Getter
public class Broadcast {

    private boolean result;
    private String txid;
    private String code;
    private String message;
}
