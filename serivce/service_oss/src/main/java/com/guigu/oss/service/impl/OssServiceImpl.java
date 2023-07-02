package com.guigu.oss.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.atguigu.servicebase.exception.GuliException;
import com.guigu.oss.service.OssService;
import com.guigu.oss.utils.ConstanPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstanPropertiesUtils.END_POINT;
        String accessKeyId = ConstanPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstanPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstanPropertiesUtils.BUCKET_NAME;
        String uploadUrl = null;
        OSS ossClient = null;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            String dataPath = new DateTime().toString("yyyy/MM/dd");
            fileName=dataPath+"/"+uuid+fileName;
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
          
            uploadUrl="https://"+bucketName+"."+endPoint+"/"+fileName;
            return uploadUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            ossClient.shutdown();
        }
    }
}
