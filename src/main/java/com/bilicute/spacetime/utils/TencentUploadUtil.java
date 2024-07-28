package com.bilicute.spacetime.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @描述: 腾讯云对象存储（COS）文件上传工具
 * @作者: i囡漫笔
 * @创建时间: 2023-12-14 13:28
 **/

@Configuration
@ConfigurationProperties(prefix = "cos")
public class TencentUploadUtil {

    private static String secretId;
    @Value("${cos.id}")
    private void setSecretId(String secretId){
        TencentUploadUtil.secretId=secretId;
    }

    private static String secretKey;
    @Value("${cos.key}")
    private void setSecretKey(String secretKey){
        TencentUploadUtil.secretKey=secretKey;
    }

    private static String bucketName;
    @Value("${cos.bucketName}")
    private void setBucketName(String bucketName){
        TencentUploadUtil.bucketName=bucketName;
    }

    private static String regionAddress;
    @Value("${cos.regionAddress}")
    private void setRegionAddress(String regionAddress){
        TencentUploadUtil.regionAddress=regionAddress;
    }

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();
    public TencentUploadUtil(){}
    static{
        getAllFileType(); // 初始化文件类型信息
    }
    private static void getAllFileType(){
        FILE_TYPE_MAP.put("jpg", "ffd8ff"); // JPEG (jpg)
        FILE_TYPE_MAP.put("png", "89504e470d0a1a0a"); // PNG (png)
        FILE_TYPE_MAP.put("webp", "52494646");
        FILE_TYPE_MAP.put("gif", "47494638"); // GIF (gif)47494638396126026f01
        FILE_TYPE_MAP.put("tif", "49492a00"); // TIFF (tif)
        FILE_TYPE_MAP.put("tiff","4d4d002a");
        FILE_TYPE_MAP.put("bmp", "424d"); //图(bmp)
    }

    /**
    * @描述: 主要实现类，返回Url
    * @Param: [folder（想要存放的路径）, fileName（文件名）, fileStream（字节流文件）, fileStreamCopy（再复制一份字节流文件）]
    * @返回: java.lang.String
    * @作者: i囡漫笔
    * @日期: 2023/12/14
    */
    public static String cosUpload(String folder, String fileName, InputStream fileStream, InputStream fileStreamCopy) throws IOException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        Region region = new Region(regionAddress);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传的文件
        String type = getType(fileName);
        System.out.println("HTTP添加的头部类型"+type);
        if(!isLegitimacy(fileName,fileStreamCopy)){
            return "不符合文件头部，文件类型校验失败！";
        }
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = folder+"/"+fileName;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        long size = fileStream.available();
        objectMetadata.setContentLength(size);//文件的大小
        objectMetadata.setContentType(type);//文件的类型
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, fileStream,objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult);
        return "https://"+bucketName+".cos."+ regionAddress +".myqcloud.com/"+key;
    }

    /**
    * @描述: 通过文件名获取HTTP头需要哪个字段
    * @Param: [fileName]
    * @返回: java.lang.String
    * @作者: i囡漫笔
    * @日期: 2023/12/14
    */
    private static String getType(String fileName){
        String typeSuffix = fileName.substring(fileName.lastIndexOf("."));
        return switch (typeSuffix) {
            case ".tif", ".tiff" -> "image/tiff";
            case ".jpeg", ".jpg" -> "image/jpeg";
            case ".gif" -> "image/gif";
            case ".png" -> "image/png";
            case ".bmp" -> "image/bmp";
            case ".svg" -> "image/svg+xml";
            case ".webp" -> "image/webp";
            default -> "";
        };
    }

    /**
    * @描述: 得到上传的文件头（十六进制文件头）
    * @Param: [src]
    * @返回: java.lang.String
    * @作者: i囡漫笔
    * @日期: 2023/12/14
    */
    private static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if(src == null || src.length <= 0){
            return null;
        }
        int tempLength = Math.min(src.length, 100);
        for(int i = 0; i < tempLength; i++){
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if(hv.length() < 2){
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /** 
    * @描述: 根据制定文件的文件头判断其文件类型
    * @Param: [fileType, fileBytes]
    * @返回: boolean
    * @作者: i囡漫笔
    * @日期: 2023/12/14
    */
    private static boolean getFileType(String fileType, byte[] fileBytes){
        boolean result = false;
        String fileCode = Objects.requireNonNull(bytesToHexString(fileBytes)).toLowerCase(Locale.ENGLISH);
        String fileSourceCode = FILE_TYPE_MAP.get(fileType.toLowerCase(Locale.ENGLISH));
        if(fileSourceCode != null){
            if(fileSourceCode.toLowerCase(Locale.ENGLISH).startsWith(fileCode.toLowerCase())
                    || fileCode.toLowerCase(Locale.ENGLISH).startsWith(fileSourceCode.toLowerCase())){
                result = true;
            }
        }
        return result;
    }

    /** 
    * @描述: 判断文件类型和文件头是否匹配（判断的主方法）
    * @Param: [fileName, fileStream]
    * @返回: boolean
    * @作者: i囡漫笔
    * @日期: 2023/12/14
    */
    private static boolean isLegitimacy(String fileName, InputStream fileStream) throws IOException {
        boolean result = false;
        String hz = fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println("后缀为："+hz);
        byte[] byt = new byte[fileStream.available()];
        fileStream.read(byt);
        if(TencentUploadUtil.getFileType(hz,byt)){ //文件头校验
            System.out.println("文件类型校验成功！");
            result=true;
        }
        return result;
    }
}
