package cn.xypt.service.impl;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.domain.PickupDelivery;
import cn.xypt.model.dto.QueryPickupDeliveryDTO;
import cn.xypt.util.MinioUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xypt.service.PickupDeliveryService;
import cn.xypt.mapper.PickupDeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangchao
* @description 针对表【pickup_delivery(校内取送)】的数据库操作Service实现
* @createDate 2024-04-17 10:22:21
*/
@Service
public class PickupDeliveryServiceImpl extends ServiceImpl<PickupDeliveryMapper, PickupDelivery>
    implements PickupDeliveryService{
    @Autowired
    private MinioUtils minioUtils;
    @Override
    public SaResult getPage(QueryPickupDeliveryDTO queryDTO) {
        Page<PickupDelivery> page = this.lambdaQuery()
            .eq(queryDTO.getId() != null, PickupDelivery::getId, queryDTO.getId())
            .eq(queryDTO.getStatus() != null, PickupDelivery::getStatus, queryDTO.getStatus())
            .eq(queryDTO.getPublisherId() != null, PickupDelivery::getPublisherId, queryDTO.getPublisherId())
            .like(StrUtil.isNotBlank(queryDTO.getTitle()), PickupDelivery::getTitle, queryDTO.getTitle())
            .like(StrUtil.isNotBlank(queryDTO.getType()), PickupDelivery::getTypes, queryDTO.getType())
            .like(StrUtil.isNotBlank(queryDTO.getRemark()), PickupDelivery::getRemark, queryDTO.getRemark())
            .like(StrUtil.isNotBlank(queryDTO.getSourceAddress()), PickupDelivery::getSourceAddress, queryDTO.getSourceAddress())
            .like(StrUtil.isNotBlank(queryDTO.getTargetAddress()), PickupDelivery::getTargetAddress, queryDTO.getTargetAddress())
            .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        List<PickupDelivery> notices = page.getRecords();
        for (PickupDelivery notice : notices) {
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




