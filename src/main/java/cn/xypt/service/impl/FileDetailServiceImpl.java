package cn.xypt.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.recorder.FileRecorder;
import cn.xypt.mapper.FileDetailMapper;
import cn.xypt.model.domain.FileDetail;
import cn.xypt.model.domain.User;
import cn.xypt.service.FileDetailService;
import cn.xypt.util.UserUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhangchao
 */
@Slf4j
@Service
public class FileDetailServiceImpl extends ServiceImpl<FileDetailMapper, FileDetail> implements
    FileRecorder, FileDetailService {

    /**
     * 保存文件信息到数据库
     */
    @Override
    public boolean record(FileInfo info) {
        FileDetail detail = BeanUtil.copyProperties(info, FileDetail.class);
        try {
            User currentUser = UserUtil.getCurrentUser();
            detail.setUploaderId(currentUser.getId());
            detail.setUploaderName(currentUser.getNickname());
        } catch (Exception e) {
            log.info("以管理员身份插入文件记录...");
            detail.setUploaderId(1);
            detail.setUploaderName("admin");
        }
        return save(detail);
    }

    /**
     * 根据 url 查询文件信息
     */
    @SneakyThrows
    @Override
    public FileInfo getByUrl(String url) {
        FileDetail detail = lambdaQuery().eq(FileDetail::getUrl, url).one();
        return BeanUtil.copyProperties(detail, FileInfo.class);
    }

    /**
     * 根据 url 删除文件信息
     */
    @Override
    public boolean delete(String url) {
        FileDetail fileDetail = lambdaQuery().eq(FileDetail::getUrl, url).one();
        return removeById(fileDetail.getId());
    }
}





