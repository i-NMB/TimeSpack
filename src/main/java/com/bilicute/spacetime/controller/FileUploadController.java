package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.ImgUpdate;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.utils.StringUtilsFromTime;
import com.bilicute.spacetime.utils.TencentUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class  FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        if (file == null) {
            return Result.errorData("失败，文件为空");
        }
        String fileName = file.getOriginalFilename();
        if (StringUtilsFromTime.isEmptyString(fileName)) {
            String s="失败，文件名为空";
            return Result.errorData(s);
        }
        String uuidFileName = UUID.randomUUID() +fileName.substring(fileName.lastIndexOf('.'));
        InputStream fileStream = file.getInputStream();
        InputStream fileStreamCopy = file.getInputStream();
        String url = TencentUploadUtil.cosUpload("img",uuidFileName,fileStream,fileStreamCopy);
        log.info("用户{}\t上传文件：{}", QuickMethods.getLoggedInUserName(), url);
        if (url.equals("不符合文件头部，文件类型校验失败！")) return Result.errorData(url);
        return Result.success(url);
    }

    @PostMapping("/upImg")
    public ImgUpdate<?> uploadImg(MultipartFile file) throws IOException {
        if (file == null) {
            return ImgUpdate.error("失败，文件为空");
        }
        String fileName = file.getOriginalFilename();
        if (StringUtilsFromTime.isEmptyString(fileName)) {
            String s="失败，文件名为空";
            return ImgUpdate.error(s);
        }
        String uuidFileName = UUID.randomUUID() +fileName.substring(fileName.lastIndexOf('.'));
        InputStream fileStream = file.getInputStream();
        InputStream fileStreamCopy = file.getInputStream();
        String url = TencentUploadUtil.cosUpload("img",uuidFileName,fileStream,fileStreamCopy);
        log.info("用户{}\t上传文件：{}至文章中", QuickMethods.getLoggedInUserName(), url);
        if (url.equals("不符合文件头部，文件类型校验失败！")) return ImgUpdate.error(url);
        Map<String, String> myMap = new HashMap<>();
        myMap.put("url", url);
        myMap.put("alt", "文章图片");
//        myMap.put("href", url);
        return ImgUpdate.success(myMap);
    }
}
