package cn.xypt.service.impl;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.dto.QueryFindNoticeDTO;
import cn.xypt.util.MinioUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xypt.service.FindNoticeService;
import cn.xypt.mapper.FindNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangchao
 * @description 针对表【find_notice(寻物启事)】的数据库操作Service实现
 * @createDate 2024-04-17 10:22:21
 */
@Service
public class FindNoticeServiceImpl extends ServiceImpl<FindNoticeMapper, FindNotice>
    implements FindNoticeService {
@Autowired
private MinioUtils minioUtils;

    @Override

    public SaResult getPage(QueryFindNoticeDTO queryDTO) {
        Page<FindNotice> page = this.lambdaQuery()
                .eq(queryDTO.getId() != null, FindNotice::getId, queryDTO.getId())
                .eq(queryDTO.getStatus() != null, FindNotice::getStatus, queryDTO.getStatus())
                .eq(queryDTO.getPublisherId() != null, FindNotice::getPublisherId, queryDTO.getPublisherId())
                .eq(queryDTO.getType() != null, FindNotice::getTypes, queryDTO.getType())
//                .like(StrUtil.isNotBlank(queryDTO.getType()), FindNotice::getTypes, queryDTO.getType())
                .like(StrUtil.isNotBlank(queryDTO.getTitle()), FindNotice::getTitle, queryDTO.getTitle())
                .like(StrUtil.isNotBlank(queryDTO.getRemark()), FindNotice::getRemark, queryDTO.getRemark())
                .like(StrUtil.isNotBlank(queryDTO.getContactInfo()), FindNotice::getContactInfo, queryDTO.getContactInfo())
                .orderByDesc(FindNotice::getId)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        List<FindNotice> notices = page.getRecords();
        for (FindNotice notice : notices) {
            if (notice.getAvatar() != null) {
                notice.setAvatar(minioUtils.getUrl(notice.getAvatar())); // 生成头像的 URL
            }
            if (notice.getPicsUrl() != null) {
                notice.setPicsUrl(minioUtils.getUrl(notice.getPicsUrl())); // 生成图片的 URL
            }
        }
        return SaResult.data(page);
    }

    @Override
    public List<FindNotice> getPageByPublisher(Integer publisherId) {
        LambdaQueryWrapper<FindNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(FindNotice::getPublisherId, publisherId);
        wrapper.orderByDesc(FindNotice::getId);
        List<FindNotice> notices = this.list(wrapper);

        for (FindNotice notice : notices) {
            if (notice.getAvatar() != null) {
                notice.setAvatar(minioUtils.getUrl(notice.getAvatar())); // 生成头像的 URL
            }
            if (notice.getPicsUrl() != null) {
                notice.setPicsUrl(minioUtils.getUrl(notice.getPicsUrl())); // 生成图片的 URL
            }
        }
//        list.forEach(item -> {
//
//        };
        return notices;
    }


}