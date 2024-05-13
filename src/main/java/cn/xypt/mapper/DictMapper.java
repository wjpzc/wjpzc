package cn.xypt.mapper;

import cn.xypt.model.domain.Dict;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;


/**
* @author zhangchao
* @description 针对表【dict(字典表(拟将学院名，角色名，开课学期等一些常量存放在此表))】的数据库操作Mapper
* @createDate 2024-04-16 11:50:07
* @Entity generator.domain.Dict
*/
@CacheNamespace(flushInterval = 86400, size = 100)
public interface DictMapper extends MPJBaseMapper<Dict> {

}




