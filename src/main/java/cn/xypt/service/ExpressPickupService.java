package cn.xypt.service;

import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.ExpressPickup;
import cn.xypt.model.dto.QueryExpressPickupDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangchao
* @description 针对表【express_pickup(快递代取)】的数据库操作Service
* @createDate 2024-04-17 10:22:21
*/
public interface ExpressPickupService extends IService<ExpressPickup> {

    SaResult getPage(QueryExpressPickupDTO queryExpressPickupDTO);
}
