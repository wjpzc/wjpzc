package cn.xypt.service.impl;

import cn.xypt.model.domain.BaseParam;
import cn.xypt.mapper.BaseParamMapper;
import cn.xypt.service.IBaseParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qqcn
 * @since 15:04:45
 */
@Service
public class BaseParamServiceImpl extends ServiceImpl<BaseParamMapper, BaseParam> implements IBaseParamService {

    @Override
    public List<BaseParam> getParamListByBaseName(String baseName) {
        return this.baseMapper.getParamListByBaseName(baseName);
    }
}
