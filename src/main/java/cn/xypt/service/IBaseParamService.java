package cn.xypt.service;

import cn.xypt.model.domain.BaseParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qqcn
 * @since 15:04:45
 */
public interface IBaseParamService extends IService<BaseParam> {

    List<BaseParam> getParamListByBaseName(String baseName);
}
