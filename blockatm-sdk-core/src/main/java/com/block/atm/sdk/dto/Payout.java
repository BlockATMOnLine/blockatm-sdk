package com.block.atm.sdk.dto;

import lombok.Getter;
import lombok.Setter;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

/**
 * @author Lawrence
 * @description
 * @date 2023/9/26 22:22
 */
@Setter
@Getter
public class Payout extends StaticStruct {

    public String tokenAddress;

    public BigInteger amount;

    public String toAddress;


    public Payout(String tokenAddress, BigInteger amount, String toAddress){
        super(new Address(tokenAddress), new Uint256(amount),new Address(toAddress));
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.toAddress = toAddress;
    }

    public Payout(Address tokenAddress, Uint256 amount, Address to){
        super(tokenAddress, amount,to);
        this.tokenAddress = tokenAddress.getValue();
        this.amount = amount.getValue();
        this.toAddress = to.getValue();
    }
}
