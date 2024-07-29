package com.bilicute.spacetime;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

//@SpringBootTest
class SpaceTimeApplicationTests {


    public static String stringEncryptor(String secretKey, String message, boolean isEncrypt) {
        PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
        pooledPBEStringEncryptor.setConfig(getSimpleStringPBEConfig(secretKey));
        return isEncrypt ? pooledPBEStringEncryptor.encrypt(message) : pooledPBEStringEncryptor.decrypt(message);
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

//    @Test
//    public void contextLoads() {
//        System.out.print("请输入密钥:");
//        Scanner p = new Scanner(System.in);
//        String password = p.next();
//        System.out.print("是否为加密（输入true为加密，输入false为解密）:");
//        Scanner enc = new Scanner(System.in);
//        boolean isEncrypt = Boolean.parseBoolean(enc.next());
//        while (true) {
//            System.out.println("请输入要加密/解密的文字，输入exit退出");
//            Scanner s = new Scanner(System.in);
//            String message = s.next();
//            if (Objects.equals(message, "exit")) {
//                break;
//            }
//            //一个同样的密码和秘钥，每次执行加密，密文都是不一样的。但是解密是没问题的。
//            //若解密时报错，请确保密钥正确
//            String jasyptEncrypt = stringEncryptor(password, message, isEncrypt);
//            System.out.println(jasyptEncrypt);
//        }
//    }
}
