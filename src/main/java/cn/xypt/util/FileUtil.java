package cn.xypt.util;

import cn.dev33.satoken.util.SaResult;

import cn.hutool.core.util.IdUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作工具类
 *
 * @author jinhong
 * @date 2023/01/13
 */
@Component
public class FileUtil {


    @Resource
    private FileStorageService fileStorageServiceBean;

    private static FileStorageService fileStorageService;

    public static SaResult upload(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
            .setPlatform("local-1")
            .setPath("common/" + IdUtil.fastSimpleUUID() + "/")
            .setSaveFilename(file.getOriginalFilename())
            .upload();
        return SaResult.data(fileInfo);
    }


    @PostConstruct
    public void init() {
        fileStorageService = fileStorageServiceBean;
    }

    public static void download(HttpServletResponse response, String url) throws IOException {
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        if (fileInfo == null) {
            throw new RuntimeException("不存在该文件");
        }
        String filename = fileInfo.getOriginalFilename();
        // 会清空响应的一些信息，包括全局的跨域配置
        // response.reset();
        setDownloadHeader(response, filename);
        byte[] bytes = fileStorageService.download(fileInfo).bytes();
        response.getOutputStream().write(bytes);
    }

    public static void setDownloadHeader(HttpServletResponse response, String filename) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
            .attachment()
            .filename(filename, StandardCharsets.UTF_8)
            .build().toString());
    }
}
