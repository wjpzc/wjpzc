package cn.xypt.service;

import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangchao
* @description 针对表【dict(字典表(拟将学院名，角色名，开课学期等一些常量存放在此表))】的数据库操作Service
* @createDate 2024-04-17 11:50:07
*/
public interface DictService extends IService<Dict> {

    SaResult getDictTree();
}
