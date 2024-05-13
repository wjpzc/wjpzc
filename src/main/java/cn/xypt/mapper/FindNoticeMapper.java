package cn.xypt.mapper;

import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author jinhong
* @description 针对表【find_notice(寻物启事)】的数据库操作Mapper
* @createDate 2024-04-17 10:22:21
* @Entity cn.xypt.model.domain.FindNotice
*/
public interface FindNoticeMapper extends BaseMapper<FindNotice> {
    @Select("select * from find_notice where types = #{types}")
    String getFindNoticeByTypes(String types);
}




