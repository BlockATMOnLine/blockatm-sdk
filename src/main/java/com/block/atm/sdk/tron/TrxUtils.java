package com.block.atm.sdk.tron;

import com.block.atm.sdk.tron.core.Base58;
import com.block.atm.sdk.tron.core.ByteArray;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
