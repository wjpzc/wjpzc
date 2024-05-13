//package cn.xypt.controller;
//
//import static cn.xypt.util.RoleConstant.ADMIN;
//
//import static cn.xypt.util.RoleConstant.STUDENT;
//
//
//import cn.dev33.satoken.annotation.SaCheckRole;
//import cn.dev33.satoken.annotation.SaMode;
//import cn.dev33.satoken.util.SaResult;
//import cn.xypt.model.domain.FileDetail;
//import cn.xypt.service.FileDetailService;
//import cn.xypt.util.FileUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import java.io.IOException;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.constraints.NotEmpty;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * @author zhangchao
// * @date 2024/04/13
// */
//@Api(tags = "文件管理")
//@RestController
//@RequestMapping("/file")
//public class FileDetailController {
//
//    @Resource
//    private FileDetailService fileDetailService;
//
//    @ApiOperation("获取文件信息")
//    @SaCheckRole(value = {ADMIN, STUDENT}, mode = SaMode.OR)
//    @GetMapping("/info")
//    public SaResult getFileInfo(
//        @Validated @NotEmpty @RequestParam("urlList") List<String> urlList) {
//        List<FileDetail> fileDetails = fileDetailService.lambdaQuery()
//            .in(FileDetail::getUrl, urlList)
//            .orderByDesc(FileDetail::getCreateTime)
//            .list();
//        return SaResult.data(fileDetails);
//    }
//
//    @ApiOperation(value = "下载文件", produces = "application/octet-stream")
//    @SaCheckRole(value = {ADMIN, STUDENT}, mode = SaMode.OR)
//    @GetMapping("/download")
//    public void download(@ApiParam("文件url") @RequestParam(value = "url") String url,
//        HttpServletResponse response)
//        throws IOException {
//        FileUtil.download(response, url);
//    }
//
//    @ApiOperation(value = "上传文件", produces = "application/octet-stream",
//        notes = "上传文件之后记得要保留url，以便后续将文件与对应的帖子关联起来，文件访问的方式为："
//            + "http:ip:port/file/url，比如说：http://localhost:8080/file/common/7b37f0d7c1244f97afc425bc936094e7/YOLOv1.pdf")
//    @SaCheckRole(value = {ADMIN, STUDENT}, mode = SaMode.OR)
//    @PostMapping("/upload")
//    public SaResult upload(@ApiParam("文件") @RequestPart MultipartFile file) {
//        return FileUtil.upload(file);
//    }
//}
