package cn.xypt.controller;

import cn.xypt.model.domain.BaseParam;
import cn.xypt.service.IBaseParamService;
import cn.xypt.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qqcn
 * @since 15:04:45
 */
@Api(tags = "基础模块")
@RestController
@RequestMapping("/base")
public class BaseParamController {
    @Autowired
    private IBaseParamService baseParamService;

    @GetMapping
    public Result<List<BaseParam>> getAll(){
        List<BaseParam> list = baseParamService.list();
        return Result.success(list);
    }

    @ApiOperation("查询参数列表")
    @GetMapping("/{baseName}")
    public Result<List<BaseParam>> getParamListByBaseName(@PathVariable("baseName") String baseName){
        List<BaseParam> list = baseParamService.getParamListByBaseName(baseName);
        return Result.success(list);
    }
}
