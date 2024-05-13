package cn.xypt.mapper;

import cn.xypt.model.domain.BaseParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qqcn
 * @since 15:04:45
 */
public interface BaseParamMapper extends BaseMapper<BaseParam> {

    @Select("select * from base_param where base_name = #{baseName} order by priority asc")
    List<BaseParam> getParamListByBaseName(String baseName);
}
