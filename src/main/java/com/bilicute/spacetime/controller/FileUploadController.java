package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.utils.StringUtilsFromTime;
import com.bilicute.spacetime.utils.TencentUploadUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        String uuidFileName = UUID.randomUUID().toString() +fileName.substring(fileName.lastIndexOf('.'));
        InputStream fileStream = file.getInputStream();
        InputStream fileStreamCopy = file.getInputStream();
        String url = TencentUploadUtil.cosUpload("img",uuidFileName,fileStream,fileStreamCopy);
        log.info("用户"+ QuickMethods.getLoggedInUserName() +"\t上传文件："+url);
        return Result.success(url);
    }
}
