package com.pucrs;

/**
 * EncryptStrategyEnum
 */
public enum EncryptStrategyEnum {
    CBC("AES/CBC/PKCS5Padding"),
    CTR("AES/CTR/NoPadding");

    private String mode;
    private EncryptStrategyEnum(String encryptMode){
        this.mode = encryptMode;
    };
    @Override
    public String toString(){
        return mode;
    }

}