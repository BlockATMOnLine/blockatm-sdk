package com.block.atm.sdk.tron;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Any;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.common.utils.JsonFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Lawrence
 * @description
 * @date 2023/11/29 15:50
 */
public class TrxUtils {
/*
    private static final Logger log = LoggerFactory.getLogger(TrxUtils.class);
    static int ADDRESS_SIZE = 21;
    private static byte addressPreFixByte = 65;
    private static BigDecimal decimal = new BigDecimal("1000000");

    public TrxUtils() {
    }

    public static BigDecimal getContactToAmount(String input, int decimel) {
        BigDecimal amountDecimal = new BigDecimal(new BigInteger(input.substring(72), 16));
        BigDecimal amount = amountDecimal.divide(new BigDecimal(Math.pow(10.0D, (double)decimel)), decimel, 1);
        return amount;
    }

    public static BigDecimal trxToSun(BigDecimal trx) {
        return trx.multiply(decimal);
    }

    public static BigDecimal sunTotrx(BigDecimal sun) {
        return sun.divide(decimal);
    }

    public static String hexToAddress(String hex) {
        return encode58Check(ByteArray.fromHexString(hex));
    }

    public static String toHexAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("Address is empty");
        } else if (!address.startsWith("T")) {
            throw new IllegalArgumentException("Address is illegal:" + address);
        } else {
            return Hex.toHexString(decodeFromBase58Check(address));
        }
    }

    public static String encode58Check(byte[] input) {
        try {
            byte[] hash0 = hash(true, input);
            byte[] hash1 = hash(true, hash0);
            byte[] inputCheck = new byte[input.length + 4];
            System.arraycopy(input, 0, inputCheck, 0, input.length);
            System.arraycopy(hash1, 0, inputCheck, input.length, 4);
            return Base58.encode(inputCheck);
        } catch (Throwable var4) {
            log.error(String.format("data error:%s", Hex.toHexString(input)), var4);
            return null;
        }
    }

    public static byte[] decodeFromBase58Check(String addressBase58) {
        try {
            byte[] address = decode58Check(addressBase58);
            return !addressValid(address) ? null : address;
        } catch (Throwable var2) {
            log.error(String.format("decodeFromBase58Check-error:" + addressBase58), var2);
            return null;
        }
    }

    private static byte[] decode58Check(String input) throws Exception {
        byte[] decodeCheck = Base58.decode(input);
        if (decodeCheck.length <= 4) {
            return null;
        } else {
            byte[] decodeData = new byte[decodeCheck.length - 4];
            System.arraycopy(decodeCheck, 0, decodeData, 0, decodeData.length);
            byte[] hash0 = hash(true, decodeData);
            byte[] hash1 = hash(true, hash0);
            return hash1[0] == decodeCheck[decodeData.length] && hash1[1] == decodeCheck[decodeData.length + 1] && hash1[2] == decodeCheck[decodeData.length + 2] && hash1[3] == decodeCheck[decodeData.length + 3] ? decodeData : null;
        }
    }

    private static boolean addressValid(byte[] address) {
        if (ArrayUtils.isEmpty(address)) {
            return false;
        } else if (address.length != ADDRESS_SIZE) {
            return false;
        } else {
            byte preFixbyte = address[0];
            return preFixbyte == addressPreFixByte;
        }
    }

    public static byte[] hash(boolean isSha256, byte[] input) throws NoSuchAlgorithmException {
        return hash(isSha256, input, 0, input.length);
    }

    public static byte[] hash(boolean isSha256, byte[] input, int offset, int length) throws NoSuchAlgorithmException {
        if (isSha256) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(input, offset, length);
            return digest.digest();
        } else {
            SM3Digest digest = new SM3Digest();
            digest.update(input, offset, length);
            byte[] eHash = new byte[digest.getDigestSize()];
            digest.doFinal(eHash, 0);
            return eHash;
        }
    }


    public static Transaction packTransaction(String strTransaction) {
        JSONObject jsonTransaction = JSONObject.parseObject(strTransaction);
        JSONObject rawData = jsonTransaction.getJSONObject("raw_data");
        JSONArray contracts = new JSONArray();
        JSONArray rawContractArray = rawData.getJSONArray("contract");

        for(int i = 0; i < rawContractArray.size(); ++i) {
            try {
                JSONObject contract = rawContractArray.getJSONObject(i);
                JSONObject parameter = contract.getJSONObject("parameter");
                String contractType = contract.getString("type");
                Any any = null;
                byte var11 = -1;
                switch(contractType.hashCode()) {
                    case -1705044092:
                        if (contractType.equals("WithdrawBalanceContract")) {
                            var11 = 13;
                        }
                        break;
                    case -1485407205:
                        if (contractType.equals("AssetIssueContract")) {
                            var11 = 6;
                        }
                        break;
                    case -703089577:
                        if (contractType.equals("FreezeBalanceContract")) {
                            var11 = 10;
                        }
                        break;
                    case -651921570:
                        if (contractType.equals("UnfreezeBalanceContract")) {
                            var11 = 11;
                        }
                        break;
                    case -544448037:
                        if (contractType.equals("SmartContract")) {
                            var11 = 15;
                        }
                        break;
                    case -492394392:
                        if (contractType.equals("AccountUpdateContract")) {
                            var11 = 9;
                        }
                        break;
                    case -439997029:
                        if (contractType.equals("AccountCreateContract")) {
                            var11 = 0;
                        }
                        break;
                    case 16441433:
                        if (contractType.equals("ParticipateAssetIssueContract")) {
                            var11 = 8;
                        }
                        break;
                    case 336992568:
                        if (contractType.equals("VoteAssetContract")) {
                            var11 = 3;
                        }
                        break;
                    case 706457047:
                        if (contractType.equals("TransferAssetContract")) {
                            var11 = 2;
                        }
                        break;
                    case 710366781:
                        if (contractType.equals("TransferContract")) {
                            var11 = 1;
                        }
                        break;
                    case 1252602738:
                        if (contractType.equals("UnfreezeAssetContract")) {
                            var11 = 12;
                        }
                        break;
                    case 1286958708:
                        if (contractType.equals("WitnessUpdateContract")) {
                            var11 = 7;
                        }
                        break;
                    case 1339356071:
                        if (contractType.equals("WitnessCreateContract")) {
                            var11 = 5;
                        }
                        break;
                    case 1421429571:
                        if (contractType.equals("TriggerSmartContract")) {
                            var11 = 16;
                        }
                        break;
                    case 1699052801:
                        if (contractType.equals("VoteWitnessContract")) {
                            var11 = 4;
                        }
                        break;
                    case 1892384185:
                        if (contractType.equals("UpdateAssetContract")) {
                            var11 = 14;
                        }
                }

                switch(var11) {
                    case 0:
                        AccountContract.AccountCreateContract.Builder accountCreateContractBuilder = AccountContract.AccountCreateContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), accountCreateContractBuilder);
                        any = Any.pack(accountCreateContractBuilder.build());
                        break;
                    case 1:
                        org.tron.protos.contract.BalanceContract.TransferContract.Builder transferContractBuilder = BalanceContract.TransferContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), transferContractBuilder);
                        any = Any.pack(transferContractBuilder.build());
                        break;
                    case 2:
                        org.tron.protos.contract.AssetIssueContractOuterClass.TransferAssetContract.Builder transferAssetContractBuilder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), transferAssetContractBuilder);
                        any = Any.pack(transferAssetContractBuilder.build());
                        break;
                    case 3:
                        org.tron.protos.contract.VoteAssetContractOuterClass.VoteAssetContract.Builder voteAssetContractBuilder = VoteAssetContractOuterClass.VoteAssetContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), voteAssetContractBuilder);
                        any = Any.pack(voteAssetContractBuilder.build());
                        break;
                    case 4:
                        org.tron.protos.contract.WitnessContract.VoteWitnessContract.Builder voteWitnessContractBuilder = WitnessContract.VoteWitnessContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), voteWitnessContractBuilder);
                        any = Any.pack(voteWitnessContractBuilder.build());
                        break;
                    case 5:
                        org.tron.protos.contract.WitnessContract.WitnessCreateContract.Builder witnessCreateContractBuilder = WitnessContract.WitnessCreateContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), witnessCreateContractBuilder);
                        any = Any.pack(witnessCreateContractBuilder.build());
                        break;
                    case 6:
                        org.tron.protos.contract.AssetIssueContractOuterClass.AssetIssueContract.Builder assetIssueContractBuilder = AssetIssueContractOuterClass.AssetIssueContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), assetIssueContractBuilder);
                        any = Any.pack(assetIssueContractBuilder.build());
                        break;
                    case 7:
                        org.tron.protos.contract.WitnessContract.WitnessUpdateContract.Builder witnessUpdateContractBuilder = WitnessContract.WitnessUpdateContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), witnessUpdateContractBuilder);
                        any = Any.pack(witnessUpdateContractBuilder.build());
                        break;
                    case 8:
                        org.tron.protos.contract.AssetIssueContractOuterClass.ParticipateAssetIssueContract.Builder participateAssetIssueContractBuilder = AssetIssueContractOuterClass.ParticipateAssetIssueContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), participateAssetIssueContractBuilder);
                        any = Any.pack(participateAssetIssueContractBuilder.build());
                        break;
                    case 9:
                        org.tron.protos.contract.AccountContract.AccountUpdateContract.Builder accountUpdateContractBuilder = AccountContract.AccountUpdateContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), accountUpdateContractBuilder);
                        any = Any.pack(accountUpdateContractBuilder.build());
                        break;
                    case 10:
                        org.tron.protos.contract.BalanceContract.FreezeBalanceContract.Builder freezeBalanceContractBuilder = BalanceContract.FreezeBalanceContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), freezeBalanceContractBuilder);
                        any = Any.pack(freezeBalanceContractBuilder.build());
                        break;
                    case 11:
                        org.tron.protos.contract.BalanceContract.UnfreezeBalanceContract.Builder unfreezeBalanceContractBuilder = BalanceContract.UnfreezeBalanceContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), unfreezeBalanceContractBuilder);
                        any = Any.pack(unfreezeBalanceContractBuilder.build());
                        break;
                    case 12:
                        org.tron.protos.contract.AssetIssueContractOuterClass.UnfreezeAssetContract.Builder unfreezeAssetContractBuilder = AssetIssueContractOuterClass.UnfreezeAssetContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), unfreezeAssetContractBuilder);
                        any = Any.pack(unfreezeAssetContractBuilder.build());
                        break;
                    case 13:
                        org.tron.protos.contract.BalanceContract.WithdrawBalanceContract.Builder withdrawBalanceContractBuilder = BalanceContract.WithdrawBalanceContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), withdrawBalanceContractBuilder);
                        any = Any.pack(withdrawBalanceContractBuilder.build());
                        break;
                    case 14:
                        org.tron.protos.contract.AssetIssueContractOuterClass.UpdateAssetContract.Builder updateAssetContractBuilder = AssetIssueContractOuterClass.UpdateAssetContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), updateAssetContractBuilder);
                        any = Any.pack(updateAssetContractBuilder.build());
                        break;
                    case 15:
                        org.tron.protos.contract.SmartContractOuterClass.SmartContract.Builder smartContractBuilder = SmartContractOuterClass.SmartContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), smartContractBuilder);
                        any = Any.pack(smartContractBuilder.build());
                        break;
                    case 16:
                        org.tron.protos.contract.SmartContractOuterClass.TriggerSmartContract.Builder triggerSmartContractBuilder = SmartContractOuterClass.TriggerSmartContract.newBuilder();
                        JsonFormat.merge(parameter.getJSONObject("value").toString(), triggerSmartContractBuilder);
                        any = Any.pack(triggerSmartContractBuilder.build());
                }

                if (any != null) {
                    String value = Hex.toHexString(any.getValue().toByteArray());
                    parameter.put("value", value);
                    contract.put("parameter", parameter);
                    contracts.add(contract);
                }
            } catch (Exception var30) {
                var30.printStackTrace();
            }
        }

        rawData.put("contract", contracts);
        jsonTransaction.put("raw_data", rawData);
        org.tron.protos.Protocol.Transaction.Builder transactionBuilder = Transaction.newBuilder();

        try {
            JsonFormat.merge(jsonTransaction.toString(), transactionBuilder);
            return transactionBuilder.build();
        } catch (Exception var29) {
            return null;
        }
    }*/
}
