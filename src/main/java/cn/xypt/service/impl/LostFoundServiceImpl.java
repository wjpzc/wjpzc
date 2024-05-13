package cn.xypt.service.impl;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.dto.QueryLostFoundDTO;
import cn.xypt.util.MinioUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xypt.model.domain.LostFound;
import cn.xypt.service.LostFoundService;
import cn.xypt.mapper.LostFoundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangchao
* @description 针对表【lost_found(失物招领表)】的数据库操作Service实现
* @createDate 2024-04-17 10:22:21
*/
@Service
public class LostFoundServiceImpl extends ServiceImpl<LostFoundMapper, LostFound>
    implements LostFoundService{
    @Autowired
    private MinioUtils minioUtils;

    @Override
    public SaResult getPage(QueryLostFoundDTO queryDTO) {
        Page<LostFound> page = this.lambdaQuery()
            .eq(queryDTO.getId() != null, LostFound::getId, queryDTO.getId())
            .eq(queryDTO.getStatus() != null, LostFound::getStatus, queryDTO.getStatus())
            .eq(queryDTO.getPublisherId() != null, LostFound::getPublisherId, queryDTO.getPublisherId())
            .like(StrUtil.isNotBlank(queryDTO.getTitle()), LostFound::getTitle, queryDTO.getTitle())
            .like(StrUtil.isNotBlank(queryDTO.getType()), LostFound::getTypes, queryDTO.getType())
            .like(StrUtil.isNotBlank(queryDTO.getRemark()), LostFound::getRemark, queryDTO.getRemark())
            .like(StrUtil.isNotBlank(queryDTO.getAddress()), LostFound::getAddress, queryDTO.getAddress())
            .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        List<LostFound> notices = page.getRecords();
        for (LostFound notice : notices) {
            if (notice.getAvatar() != null) {
                notice.setAvatar(minioUtils.getUrl(notice.getAvatar())); // 生成头像的 URL
            }
            if (notice.getPicsUrl() != null) {
                notice.setPicsUrl(minioUtils.getUrl(notice.getPicsUrl())); // 生成图片的 URL
            }
        }
        return SaResult.data(page);
    }
}




