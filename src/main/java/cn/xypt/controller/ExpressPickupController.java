package cn.xypt.controller;

import static cn.xypt.util.RoleConstant.ADMIN;
import static cn.xypt.util.RoleConstant.STUDENT;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.util.SaResult;
import cn.xypt.common.utils.JwtUtil;
import cn.xypt.model.domain.ExpressPickup;
import cn.xypt.model.domain.LostFound;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.QueryExpressPickupDTO;
import cn.xypt.service.ExpressPickupService;
import cn.xypt.util.MinioUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangchao
 * @date 2024/04/13
 */
@Api(tags = "快递代取")
@RestController
@RequestMapping("/expressPickup")
public class ExpressPickupController {

    @Autowired
    private JwtUtil jwtUtil;


    @Resource
    private ExpressPickupService expressPickupService;

    @ApiOperation("分页查询")
    @SaCheckRole(value = {ADMIN,  STUDENT}, mode = SaMode.OR)
    @GetMapping("/page")
    public SaResult getPage(@Validated QueryExpressPickupDTO queryExpressPickupDTO) {
        return expressPickupService.getPage(queryExpressPickupDTO);
    }


    @Autowired
    private MinioUtils minioUtils;

    @ApiOperation("根据id查询详情")
    @GetMapping("/{id}")

    public SaResult getById(@PathVariable("id") Long id) {
        ExpressPickup expressPickup= expressPickupService.getById(id);
        expressPickup.setAvatar(minioUtils.getUrl(expressPickup.getAvatar()));

        return SaResult.data(expressPickup);
    }

    @PostMapping
    public SaResult add(@RequestBody ExpressPickup expressPickup,@RequestHeader("Authorization") String token) {
        User user = jwtUtil.parseJwt(token,User.class);
        expressPickup.setPublisherId(user.getId());
        expressPickup.setPublisherName(user.getNickname());
        expressPickup.setAvatar(user.getAvatar());
        expressPickupService.save(expressPickup);
        return SaResult.data(expressPickup.getId());
    }

    @PutMapping
    public SaResult update(@RequestBody ExpressPickup expressPickup) {
        boolean update = expressPickupService.updateById(expressPickup);
        return SaResult.data(update);
    }

    @DeleteMapping
    public SaResult delete(@Validated @RequestParam("ids") @NotEmpty List<Long> ids) {
        boolean remove = expressPickupService.removeBatchByIds(ids);
        return SaResult.data(remove);
    }
}
