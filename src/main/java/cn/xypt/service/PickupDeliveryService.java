package cn.xypt.service;

import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.PickupDelivery;
import cn.xypt.model.dto.QueryPickupDeliveryDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangchao
* @description 针对表【pickup_delivery(校内取送)】的数据库操作Service
* @createDate 2024-04-17 10:22:21
*/
public interface PickupDeliveryService extends IService<PickupDelivery> {

    SaResult getPage(QueryPickupDeliveryDTO queryPickupDeliveryDTO);
}
