package com.pucrs;

public class App {
    public static void main(String[] args) throws Exception {
        boolean isCrypt = args[0].toLowerCase().equals("crypt") ? true : false;
        String strategy = args[1].toLowerCase();
        String key = args[2].trim();
        String text = args[3].trim();
        EncryptStrategyEnum strategyEnum = strategy.equals("ctr") ? EncryptStrategyEnum.CTR : EncryptStrategyEnum.CBC;
        System.out.println("------------------------");
        System.out.println(isCrypt ? "Encrypting" : "Decrypting");
        System.out.println("key " + key);
        System.out.println("text " + text);
        System.out.println("EncryptStrategy " + strategyEnum);
        Crypt crypt = new Crypt(key, strategyEnum);

        System.out.println("Output:\n");
        if (!isCrypt) {
            System.out.println(crypt.decrypt(text));
        } else {
            System.out.println(crypt.encrypt(text));
        }
    }
}