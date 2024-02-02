package com.block.atm.sdk.tron;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.tron.protos.Protocol;
import com.block.atm.sdk.BlockATMConstant;
import com.block.atm.sdk.dto.Broadcast;
import com.block.atm.sdk.eth.PayoutHelper;
import org.spongycastle.util.encoders.Hex;
import org.tron.common.utils.ByteArray;
import org.tron.utils.TronUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class TronHelperTest {


    public static void payout() throws NoSuchAlgorithmException, IOException, InstantiationException, IllegalAccessException {
        String jsonRpc = "https://api.shasta.trongrid.io/jsonrpc";
        String http = "https://api.shasta.trongrid.io";

        TronPayoutHelper payoutHelper = new TronPayoutHelper(jsonRpc,http);
        String privateKey = "ab56e9660036a20189ba763ca4175dcbb315092f722e8bc746181e62065c897b";
        // payout contract Test
        //String payoutAddress = "TVdDeQJnibVBY3gWVjkiTf84Wxbf8tB2oZ";
        // payoutgateway
        String payoutAddress = "TEPxhToBFhzBok6UN8TRvHxf7FQDFcEcwP";
        String fromAddress = "TUxNbLNpNxQafkHSMTUQf7AqdasdgeDSyp";
        //
        //List<Address> tokenList, List<Uint256 > amountList, List<Address> toList, List< Utf8String > business
        List<Address> tokenList = new ArrayList<>();
        // usdt
        tokenList.add(new Address(TronSDKUtils.convertAddressToEth("TEYKWmKvdCHX2NuX4tVmhxdN4P3PVjyMcu")));
        // usdc
        tokenList.add(new Address(TronSDKUtils.convertAddressToEth("TWjJj93GX51rJ8GRFihPVNA15ieLoheKaj")));
        List<Uint256 > amountList = new ArrayList<>();
        // 1 USDT
        amountList.add(new Uint256(1000L));
        // 1 USDC
        amountList.add(new Uint256(1000L));

        // 自己转入地址
        List<Address> toList = new ArrayList<>();
        // usdt 转入地址
        toList.add(new Address(TronSDKUtils.convertAddressToEth("TYVY9go3sYDQrt6QybU5iHhHfRtdxXkxjp")));
        // usdc 转入地址
        toList.add(new Address(TronSDKUtils.convertAddressToEth("TYVY9go3sYDQrt6QybU5iHhHfRtdxXkxjp")));
        // 业务编号
        List< Utf8String > business = new ArrayList<>();
        business.add(new Utf8String("test1.NO1"));
        business.add(new Utf8String("test2.NO1"));

        String txId = payoutHelper.payout(privateKey,payoutAddress,fromAddress,new BigDecimal(100),tokenList,amountList,toList,business);
        System.out.println("本次交易ID->" + txId);
    }


    public static void getTransaction() throws NoSuchAlgorithmException, IOException, InstantiationException, IllegalAccessException {
        String jsonRpc = "https://api.shasta.trongrid.io/jsonrpc";
        String http = "https://api.shasta.trongrid.io";

        TronPayoutHelper payoutHelper = new TronPayoutHelper(jsonRpc,http);
        EthTransaction ethTransaction = payoutHelper.getTransaction("abd1f2549021239ecdc8e5b8db069216fa46771d3147806d00a0be840b85db0c");
        System.out.println("本次交易ID->" + ethTransaction.getTransaction().get().getHash());
    }


    public static void getTransactionReceipt() throws NoSuchAlgorithmException, IOException, InstantiationException, IllegalAccessException {
        String jsonRpc = "https://api.shasta.trongrid.io/jsonrpc";
        String http = "https://api.shasta.trongrid.io";

        TronPayoutHelper payoutHelper = new TronPayoutHelper(jsonRpc,http);
        EthGetTransactionReceipt ethTransaction = payoutHelper.getTransactionReceipt("abd1f2549021239ecdc8e5b8db069216fa46771d3147806d00a0be840b85db0c");
        System.out.println("本次交易ID->" + ethTransaction.getTransactionReceipt().get().getStatus());
    }

    static void txIsSuccessful() throws IOException {
        String jsonRpc = "https://api.shasta.trongrid.io/jsonrpc";
        String http = "https://api.shasta.trongrid.io";

        TronPayoutHelper payoutHelper = new TronPayoutHelper(jsonRpc,http);
        Boolean eth = payoutHelper.txIsSuccessful("abd1f2549021239ecdc8e5b8db069216fa46771d3147806d00a0be840b85db0c");
        System.out.println("txId is fail ->" + eth);

    }






    public static void main(String[] args) throws Exception {
        /*for (int i = 0; i < 5; i++){
            sendRawTransaction();
        }*/
        //createTransaction();
        payout();
        /*getTransaction();
        getTransactionReceipt();
        txIsSuccessful();*/
    }

    static String createTransaction() throws IOException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException {
        String jsonRpc = "https://api.shasta.trongrid.io/jsonrpc";
        String http = "https://api.shasta.trongrid.io";

        TronPayoutHelper payoutHelper = new TronPayoutHelper(jsonRpc,http);

        //String privateKey = "ab56e9660036a20189ba763ca4175dcbb315092f722e8bc746181e62065c897b";
        String privateKey = "ab56e9660036a20189ba763ca4175dcbb315092f722e8bc746181e62065c897";


        String payoutAddress = "TEPxhToBFhzBok6UN8TRvHxf7FQDFcEcwP";
        String fromAddress = "TUxNbLNpNxQafkHSMTUQf7AqdasdgeDSyp";

        List<Address> tokenList = new ArrayList<>();
        // usdt
        tokenList.add(new Address(TronSDKUtils.convertAddressToEth("TEYKWmKvdCHX2NuX4tVmhxdN4P3PVjyMcu")));
        // usdc
        tokenList.add(new Address(TronSDKUtils.convertAddressToEth("TWjJj93GX51rJ8GRFihPVNA15ieLoheKaj")));
        List<Uint256 > amountList = new ArrayList<>();
        // 1 USDT
        amountList.add(new Uint256(1000L));
        // 1 USDC
        amountList.add(new Uint256(1000L));

        // 自己转入地址
        List<Address> toList = new ArrayList<>();
        // usdt 转入地址
        toList.add(new Address(TronSDKUtils.convertAddressToEth("TYVY9go3sYDQrt6QybU5iHhHfRtdxXkxjp")));
        // usdc 转入地址
        toList.add(new Address(TronSDKUtils.convertAddressToEth("TYVY9go3sYDQrt6QybU5iHhHfRtdxXkxjp")));
        // 业务编号
        List< Utf8String > business = new ArrayList<>();
        business.add(new Utf8String("test1.NO1"));
        business.add(new Utf8String("test2.NO1"));

        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Bool(Boolean.FALSE));
        inputParameters.add(new DynamicArray(tokenList));
        inputParameters.add(new DynamicArray(amountList));
        inputParameters.add(new DynamicArray(toList));
        inputParameters.add(new DynamicArray(business));
        String parameter = FunctionEncoder.encodeConstructor(inputParameters);
        String trans =  payoutHelper.createTransaction(payoutAddress, BlockATMConstant.TRON_PAYOUT_TOKEN,parameter,fromAddress,new BigDecimal(100));
        System.out.println("trans is fail ->" + trans);
        JSONObject transaction = JSONObject.parseObject(trans);
        Protocol.Transaction tx = org.tron.utils.TronUtils.packTransaction(transaction.get("transaction").toString());
        // 签署交易
        byte[] bytes = TronUtils.signTransactionByte(tx.toByteArray(), ByteArray.fromHexString(privateKey));
        String raw = Hex.toHexString(bytes);
        System.out.println("raw is  ->" + raw);
        System.out.println("raw is  ->" + raw);
        Broadcast eth = payoutHelper.sendRawTransaction(raw);
        System.out.println("send is  ->" + JSONObject.toJSONString(eth));
        return trans;
    }


    static void sendRawTransaction() throws Exception {
        String jsonRpc = "https://api.shasta.trongrid.io/jsonrpc";
        String http = "https://api.shasta.trongrid.io";

        TronPayoutHelper payoutHelper = new TronPayoutHelper(jsonRpc,http);
        String privateKey = "ab56e9660036a20189ba763ca4175dcbb315092f722e8bc746181e62065c897b";
        // 使用上面的列子创建离线交易
        /*String trans = createTransaction();
        JSONObject transaction = JSONObject.parseObject(trans);
        Protocol.Transaction tx = org.tron.utils.TronUtils.packTransaction(transaction.get("transaction").toString());*/
        String payoutAddress = "TEPxhToBFhzBok6UN8TRvHxf7FQDFcEcwP";
        String fromAddress = "TUxNbLNpNxQafkHSMTUQf7AqdasdgeDSyp";

        List<Address> tokenList = new ArrayList<>();
        // usdt
        tokenList.add(new Address(TronSDKUtils.convertAddressToEth("TEYKWmKvdCHX2NuX4tVmhxdN4P3PVjyMcu")));
        // usdc
        tokenList.add(new Address(TronSDKUtils.convertAddressToEth("TWjJj93GX51rJ8GRFihPVNA15ieLoheKaj")));
        List<Uint256 > amountList = new ArrayList<>();
        // 1 USDT
        amountList.add(new Uint256(1000L));
        // 1 USDC
        amountList.add(new Uint256(1000L));

        // 自己转入地址
        List<Address> toList = new ArrayList<>();
        // usdt 转入地址
        toList.add(new Address(TronSDKUtils.convertAddressToEth("TYVY9go3sYDQrt6QybU5iHhHfRtdxXkxjp")));
        // usdc 转入地址
        toList.add(new Address(TronSDKUtils.convertAddressToEth("TYVY9go3sYDQrt6QybU5iHhHfRtdxXkxjp")));
        // 业务编号
        List< Utf8String > business = new ArrayList<>();
        business.add(new Utf8String("test1.NO1"));
        business.add(new Utf8String("test2.NO1"));

        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Bool(Boolean.FALSE));
        inputParameters.add(new DynamicArray(tokenList));
        inputParameters.add(new DynamicArray(amountList));
        inputParameters.add(new DynamicArray(toList));
        inputParameters.add(new DynamicArray(business));
        String parameter = FunctionEncoder.encodeConstructor(inputParameters);

        Protocol.Transaction tx = payoutHelper.createSmartTransaction(fromAddress,parameter,BlockATMConstant.TRON_PAYOUT_TOKEN,payoutAddress,new BigDecimal(100),payoutHelper.getNowBlock());
        // 签署交易
        byte[] bytes = TronUtils.signTransactionByte(tx.toByteArray(), ByteArray.fromHexString(privateKey));
        String raw = Hex.toHexString(bytes);
        //Thread.sleep(180000l);
        Broadcast eth = payoutHelper.sendRawTransaction(raw);
        System.out.println("txId is fail ->" + JSONObject.toJSONString(eth));

    }
}
