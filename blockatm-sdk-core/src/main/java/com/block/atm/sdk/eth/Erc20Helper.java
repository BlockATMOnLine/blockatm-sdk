package com.block.atm.sdk.eth;

import com.block.atm.sdk.BlockATMConstant;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/27 13:58
 */
public class Erc20Helper extends EthHelper {

    public Erc20Helper(String url) {
        super(url);
    }

    public Erc20Helper(Web3j web3j) {
        super(web3j);
    }


    public BigInteger getBalance(String contractAddress, String who) throws IOException {
        List<Type> inputArgs2 = new ArrayList<>();
        inputArgs2.add(new Address(who));
        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        outputArgs2.add(new TypeReference<Uint256>() {});
        Function nameFunction = new Function(BlockATMConstant.GET_BALANCE_OF, inputArgs2, outputArgs2);
        String value = callContract(contractAddress,nameFunction);
        List<Type> list = FunctionReturnDecoder.decode(value, nameFunction.getOutputParameters());
        return new BigInteger(list.get(0).getValue().toString());
    }

    public BigInteger getDecimals(String contractAddress) throws IOException {
        List<Type> inputArgs2 = new ArrayList<>();
        List<TypeReference<?>> outputArgs2 = new ArrayList<>();
        outputArgs2.add(new TypeReference<Uint256>() {});
        Function nameFunction = new Function(BlockATMConstant.DECIMALS, inputArgs2, outputArgs2);
        String value = callContract(contractAddress,nameFunction);
        List<Type> list = FunctionReturnDecoder.decode(value, nameFunction.getOutputParameters());
        return new BigInteger(list.get(0).getValue().toString());
    }
}
