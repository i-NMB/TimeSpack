package com.bilicute.spacetime.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sha256 {
    private static String salt;
    @Value("${encoding.salt}")
    private void setSalt(String salt){
        Sha256.salt =salt;
    }

    public static String addSalt(String plaintext){
        String encodingKey = DigestUtils.sha256Hex(plaintext);
        return DigestUtils.sha256Hex(encodingKey+salt);
    }

    public static String getKey(){
        return DigestUtils.sha256Hex(salt);
    }

}
