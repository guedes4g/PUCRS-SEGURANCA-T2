package com.pucrs;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    private String key;
    private EncryptStrategyEnum strategyEnum;

    public Crypt(String key, EncryptStrategyEnum strategyEnum) {
        this.key = key;
        this.strategyEnum = strategyEnum;
    }

    public String encrypt(String value) {
        try {
            IvParameterSpec iv = getIvCrypt(value);
            SecretKeySpec secretKeySpec = getKey(this.key);

            Cipher cipher = Cipher.getInstance(strategyEnum.toString());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return hexToString(iv.getIV()) + hexToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = getIvDecrypt(encrypted);
            SecretKeySpec secretKeySpec = getKey(key);
            
            Cipher cipher = Cipher.getInstance(strategyEnum.toString());

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] bytes = stringToHex(encrypted.substring(32));
            byte[] original = cipher.doFinal(bytes);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private IvParameterSpec getIvDecrypt(String text) {
        String initVector = text.substring(0, 32);
        return new IvParameterSpec(stringToHex(initVector));
    }

    private IvParameterSpec getIvCrypt(String text) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return new IvParameterSpec(bytes);
    }

    private SecretKeySpec getKey(String key) throws NoSuchAlgorithmException {
        return new SecretKeySpec(stringToHex(key), "AES");
    }

    private byte[] stringToHex(String text) {
        return DatatypeConverter.parseHexBinary(text);
    }

    private String hexToString(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }
}