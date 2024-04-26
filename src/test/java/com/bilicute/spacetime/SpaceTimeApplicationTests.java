package com.bilicute.spacetime;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.Scanner;

@SpringBootTest
class SpaceTimeApplicationTests {


    public static String stringEncryptor(String secretKey, String message, boolean isEncrypt) {
        PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
        pooledPBEStringEncryptor.setConfig(getSimpleStringPBEConfig(secretKey));
        String result = isEncrypt ? pooledPBEStringEncryptor.encrypt(message) : pooledPBEStringEncryptor.decrypt(message);
        return result;
    }
    private static SimpleStringPBEConfig getSimpleStringPBEConfig(String secretKey) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secretKey);
        config.setPoolSize("1");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        return config;
    }
    @Test
    public void contextLoads() {
        System.out.println("请输入密钥");
        Scanner p= new Scanner(System.in);
        String password=p.next();
        while (true){
            System.out.println("请输入要加密的文字");
            Scanner s= new Scanner(System.in);
            String message=s.next();
            if (Objects.equals(message, "abc")){
                break;
            }
            //一个同样的密码和秘钥，每次执行加密，密文都是不一样的。但是解密是没问题的。
            String jasyptEncrypt = stringEncryptor(password, message, true);
            System.out.println(jasyptEncrypt);
        }
    }
}
