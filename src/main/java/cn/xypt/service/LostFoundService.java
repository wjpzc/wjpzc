package cn.xypt.service;

import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.LostFound;
import cn.xypt.model.dto.QueryLostFoundDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangchao
* @description 针对表【lost_found(失物招领表)】的数据库操作Service
* @createDate 2024-04-17 10:22:21
*/
public interface LostFoundService extends IService<LostFound> {

    SaResult getPage(QueryLostFoundDTO queryDTO);
}
