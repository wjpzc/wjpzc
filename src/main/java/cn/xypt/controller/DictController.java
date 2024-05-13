package cn.xypt.controller;

import static cn.xypt.util.RoleConstant.ADMIN;
import static cn.xypt.util.RoleConstant.STUDENT;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.Dict;
import cn.xypt.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangchao
 * @date 2024/04/13
 */
@Api(tags = "字典管理(系统配置)")
@RestController
@RequestMapping("/dict")
public class DictController {

  @Resource
  private DictService dictService;

  @ApiOperation("获取树状字典")
  @SaCheckRole(value = {ADMIN, STUDENT}, mode = SaMode.OR)
  @GetMapping("/tree")
  public SaResult getDictTree() {
    return dictService.getDictTree();
  }

  @ApiOperation("获取字典列表")
  @SaCheckRole(value = {ADMIN,  STUDENT}, mode = SaMode.OR)
  @GetMapping("/list")
  public SaResult getDictList() {
    return SaResult.data(dictService.list());
  }


  @ApiOperation("新增字典")
  @PostMapping("/add")
  @SaCheckRole(ADMIN)
  public SaResult addDict(@RequestBody Dict dict) {
    dictService.save(dict);
    return SaResult.data(dict.getId());
  }

  @ApiOperation("更新字典")
  @PostMapping("/update")
  @SaCheckRole(ADMIN)
  public SaResult updateDict(@RequestBody Dict dict) {
    dictService.updateById(dict);
    return SaResult.data(dict.getId());
  }

  @ApiOperation("删除字典")
  @PostMapping("delete")
  @SaCheckRole(ADMIN)
  public SaResult deleteDict(@RequestParam("ids") @NotEmpty List<Long> ids) {
    boolean flag = dictService.removeBatchByIds(ids);
    return SaResult.data(flag);
  }


}
