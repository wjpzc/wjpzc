package cn.xypt.service.impl;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import cn.xypt.model.domain.ExpressPickup;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.dto.QueryExpressPickupDTO;
import cn.xypt.service.ExpressPickupService;
import cn.xypt.mapper.ExpressPickupMapper;
import cn.xypt.util.MinioUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangchao
 * @description 针对表【express_pickup(快递代取)】的数据库操作Service实现
 * @createDate 2024-04-17 10:22:21
 */
@Service
public class ExpressPickupServiceImpl extends ServiceImpl<ExpressPickupMapper, ExpressPickup>
    implements ExpressPickupService {

    @Autowired
    private MinioUtils minioUtils;
    @Override
    public SaResult getPage(QueryExpressPickupDTO queryDTO) {
        Page<ExpressPickup> page = this.lambdaQuery()
            .eq(queryDTO.getId() != null, ExpressPickup::getId, queryDTO.getId())
            .eq(queryDTO.getStatus() != null, ExpressPickup::getStatus, queryDTO.getStatus())
            .eq(queryDTO.getPublisherId() != null, ExpressPickup::getPublisherId, queryDTO.getPublisherId())
            .like(StrUtil.isNotBlank(queryDTO.getTitle()), ExpressPickup::getTitle, queryDTO.getTitle())
            .like(StrUtil.isNotBlank(queryDTO.getReceiver()), ExpressPickup::getReceiver, queryDTO.getReceiver())
            .like(StrUtil.isNotBlank(queryDTO.getReceiverAddress()), ExpressPickup::getReceiverAddress, queryDTO.getReceiverAddress())
            .like(StrUtil.isNotBlank(queryDTO.getRemark()), ExpressPickup::getRemark,queryDTO.getRemark())
            .like(StrUtil.isNotBlank(queryDTO.getContactInfo()), ExpressPickup::getContactInfo, queryDTO.getContactInfo())
            .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        List<ExpressPickup> notices = page.getRecords();
        for (ExpressPickup notice : notices) {
            if (notice.getAvatar() != null) {
                notice.setAvatar(minioUtils.getUrl(notice.getAvatar())); // 生成头像的 URL
            }
        }
        return SaResult.data(page);
    }
}




