package cn.xypt.util;

import io.minio.*;
import io.minio.http.Method;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinioUtils {
    @Resource
    private MinioClient minioClient;

    @Value("${my.minio.bucketName}")
    private String bucketName;
    @Value("${my.minio.expiry}")
    private Integer expiry;

    private String newFileName(String name){
        return (UUID.randomUUID()+"").replaceAll("-","")+name.substring(name.lastIndexOf("."));
    }

    /**
     * 文件上传
     * @param file
     * @return Map
     *   fileName: 上传后新文件名
     *   error: 错误信息，不为空则上传失败
     * @throws Exception
     */
    public Map<String,Object> upload(MultipartFile file) throws Exception{
        InputStream is = null;
        Map<String,Object> result = new HashMap<>();
        try {
            BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            if(!minioClient.bucketExists(existsArgs)) {
                // 桶不存在则创建
                MakeBucketArgs makeArgs = MakeBucketArgs.builder().bucket(bucketName).build();
                minioClient.makeBucket(makeArgs);
            }

            is = file.getInputStream();
            String fileName = this.newFileName(file.getOriginalFilename());

            // 文件上传
            minioClient.putObject(PutObjectArgs.builder().object(fileName)
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .stream(is, file.getSize(), -1).build());
            is.close();
            result.put("fileName",fileName);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error",e.getMessage());
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 根据文件名创建一个访问链接
     * @param fileName
     * @return
     */
    public String getUrl(String fileName){
        if(fileName == null || fileName.isBlank()) {

            return null;
        }
        if(fileName.startsWith("http") || fileName.startsWith("/")){
            return fileName;
        }
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .expiry(expiry, TimeUnit.HOURS)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getUrl(String fileName, Integer  expiry){
        if(fileName.startsWith("http") || fileName.startsWith("/")){
            return fileName;
        }
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .expiry(expiry, TimeUnit.HOURS)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 删除文件
    public void removeObject(String fileName) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(bucketName)
             .object(fileName)
                .build();
        minioClient.removeObject(args);
    }
}