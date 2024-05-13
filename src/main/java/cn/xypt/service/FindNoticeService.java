package cn.xypt.service;


import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.dto.QueryFindNoticeDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangchao
* @description 针对表【find_notice(寻物启事)】的数据库操作Service
* @createDate 2024-04-17 10:22:21
*/
public interface FindNoticeService extends IService<FindNotice> {

    SaResult getPage(QueryFindNoticeDTO queryFindNoticeDTO);

    List<FindNotice> getPageByPublisher(Integer publisherId);
}
