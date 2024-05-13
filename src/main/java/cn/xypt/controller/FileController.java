package cn.xypt.controller;


import cn.xypt.common.vo.Result;
import cn.xypt.util.MinioUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/file")
@RestController
@Api(tags = "文件服务")
public class FileController {

    @Autowired
    private MinioUtils minioUtils;



    @ApiOperation("上传文件")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Result<Map<String,Object>> upload(@RequestPart("file") MultipartFile file){
        if(file == null || file.getSize() ==0){
            return Result.fail(20010, "文件不能为空");
        }
        try {
            Map<String, Object> uploadResult = minioUtils.upload(file);
            return Result.success(uploadResult);
        } catch (Exception e) {

            return Result.fail(20010, "上传文件失败");
        }
    }


    @ApiOperation("获取文件访问url")
    @GetMapping("/url/{filename}")
    public Result<String> getFileUrl(@PathVariable("filename") String filename){

        String url = minioUtils.getUrl(filename);
        return Result.success(url,"success");

    }


}
