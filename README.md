# TimeSpack时光漫步

![Maintainer](https://img.shields.io/badge/maintainer-i%E5%9B%A1%E6%BC%AB%E7%AC%94-%23f38181?style=flat&link=https%3A%2F%2Fi-nmb.cn)![GitHub commit activity](https://img.shields.io/github/commit-activity/m/i-NMB/TimeSpack)![GitHub last commit](https://img.shields.io/github/last-commit/i-NMB/TimeSpack)

[![AGPL License](https://badgen.net/github/license/i-NMB/TimeSpack)](http://www.gnu.org/licenses/agpl-3.0)[![Demo](https://img.shields.io/badge/Demo-time.bilicute.com-%2311999e?style=flat)](https://time.bilicute.com)

《时光漫步》是一个创新的博客/个人空间平台，旨在为用户提供一个自由、开放、互动的网络空间。本项目基于先进的Web技术构建，使用户能够轻松创建、发布和管理自己的内容，同时与其他用户互动和分享。

## 项目特点

界面友好：采用简洁、直观的设计，使新手用户也能快速上手。

功能丰富：支持文章发布、评论互动、图片上传、标签分类等功能，满足用户多样化的需求。

自定义能力强：提供丰富的主题和插件，用户可以根据自己的喜好进行个性化设置。

社交互动：支持关注、点赞、评论等社交功能，促进用户之间的互动和交流。

开源免费：本项目完全开源，用户可以免费使用和修改，共同推动项目的发展。

## 项目目标

为用户提供一个自由、开放、互动的网络空间，让每个人都能表达自己的声音。

通过不断优化和升级，提升用户体验，打造一个功能强大、易于使用的博客/个人空间平台。

汇聚更多热爱写作、分享的用户，形成一个积极、健康的网络社区。

我们欢迎广大开发者积极参与本项目，共同推动《时光漫步》的发展。如果您有任何建议或意见，请随时联系我们。让我们一起打造一个更好的博客/个人空间平台！

## 项目使用

### 后端构建

由于项目使用`jasypt`依赖进行敏感数据加密，因此在本项目中需要修改测试用例的`src/main/resources/application.yml`配置文件

#### 方法一

1.使用不经加密的数据替换带有`加密()`包裹的数据以及其他自定义数据

2.正常使用maven构建（`mvn clean package`）

#### 方法二(推荐)

1.使用`下面提供的`测试类，运行后输入你的**<u>加密密钥</u>**

```java
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

    @Test
    public void contextLoads() {
        System.out.print("请输入密钥:");
        Scanner p = new Scanner(System.in);
        String password = p.next();
        System.out.print("是否为加密（输入true为加密，输入false为解密）:");
        Scanner enc = new Scanner(System.in);
        boolean isEncrypt = Boolean.parseBoolean(enc.next());
        while (true) {
            System.out.println("请输入要加密/解密的文字，输入exit退出");
            Scanner s = new Scanner(System.in);
            String message = s.next();
            if (Objects.equals(message, "exit")) {
                break;
            }
            //一个同样的密码和秘钥，每次执行加密，密文都是不一样的。但是解密是没问题的。
            //若解密时报错，请确保密钥正确
            String jasyptEncrypt = stringEncryptor(password, message, isEncrypt);
            System.out.println(jasyptEncrypt);
        }
    }
}

```

![测试类使用方法](https://img1.i-nmb.cn/img/image-20240729112152900.png)

2.将生成的密文替换`src/main/resources/application.yml`文件中`加密()`包裹的数据，如下所示

```yaml
    username: 加密(QBOMpdU/hj2sj22R+ZyplW2BCIlY/3Sc)
    password: 加密(OW0ima68qs1eHteZLt3FoMkSfb+PkAetofIVqDHEKTPEooj3D/D4mg==)
```

如果你不喜欢这个前后缀可以修改配置

```yaml
jasypt:
  encryptor:
    algorithm: PBEWithMD5AndDES
    property:
      prefix: 加密(
      suffix: )
```

3.构建时使用命令

```shell
clean package -Djasypt.encryptor.password=你的加密密钥 -Dmaven.test.skip=true
```

4.运行时额外添加命令`-Djasypt.encryptor.password=你的加密密钥`

例如

```shell
java -jar -Djasypt.encryptor.password=xbmu2024 SpaceTime-1.104.102-SNAPSHOT.jar
```

如果需要更多帮助可自行搜索`Jasypt`的使用资料以及官方文档

### 前端构建

前端的`Vue3`以一个单独 JS 文件的形式使用，无需构建步骤！前端逻辑并不复杂，不需要构建步骤，这是最简单的使用 Vue 的方式。

### 前后端结合

**注意事项**：本项目使用前后端分离，但暂无配置跨域请求，建议前后端设置在同一域名下以便cookie传递。或者可以根据需求自行完善源代码的跨域请求

## 示例

### Demo在线演示

在线演示：[时光漫步在线演示](https://time.bilicute.com)

### 文章界面

![文章界面](https://img1.i-nmb.cn/img/image-20240729115134345.png)

### 后台界面

![后台界面](https://img1.i-nmb.cn/img/image-20240729114558786.png)

![文章管理](https://img1.i-nmb.cn/img/image-20240729114754792.png)

![数据统计](https://img1.i-nmb.cn/img/image-20240729114955229.png)



## 开源许可

[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)

## 作者

- [@时光漫步开发团队](https://gitee.com/TimeSpack)
- [@i囡漫笔](https://gitee.com/inmb)
- [@mjlyb](https://gitee.com/mjlyb)
- [@yu-tingtingt](https://gitee.com/yu-tingtingt)
- [@王雅妮sinnie](https://gitee.com/wang-yani-sinie)
- [@sjzm](https://gitee.com/sjzm)
- [@myd](https://gitee.com/myd1145)

## 反馈

如果你有任何反馈，请联系我们：note@bilicute.com



## 技术栈

**前端:** Vue3, UIkitCSS, JS, HTML

**服务端:** SpringBoot3 + MyBatis

![Logo](https://time.bilicute.com/img/logo.png)

## 相关

以下是一些相关项目

[Libman现代化界面的图书管理系统](https://gitee.com/inmb/libman)

