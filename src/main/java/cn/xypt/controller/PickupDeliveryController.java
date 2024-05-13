package cn.xypt.controller;

import static cn.xypt.util.RoleConstant.ADMIN;
import static cn.xypt.util.RoleConstant.STUDENT;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.util.SaResult;
import cn.xypt.common.utils.JwtUtil;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.domain.PickupDelivery;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.QueryPickupDeliveryDTO;
import cn.xypt.service.PickupDeliveryService;
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
@Api(tags = "校内取送")
@RestController
@RequestMapping("/pickupDelivery")
public class PickupDeliveryController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MinioUtils minioUtils;

    
    @Resource
    private PickupDeliveryService pickupDeliveryService;

    @ApiOperation("分页查询")
    @SaCheckRole(value = {ADMIN,  STUDENT}, mode = SaMode.OR)
    @GetMapping("/page")
    public SaResult getPage(@Validated QueryPickupDeliveryDTO queryPickupDeliveryDTO) {
        return pickupDeliveryService.getPage(queryPickupDeliveryDTO);
    }

    @ApiOperation("根据id查询详情")
    @GetMapping("/{id}")

    public SaResult getById(@PathVariable("id") Long id) {
        PickupDelivery pickupDelivery = pickupDeliveryService.getById(id);
        pickupDelivery.setAvatar(minioUtils.getUrl(pickupDelivery.getAvatar()));
        pickupDelivery.setPicsUrl(minioUtils.getUrl(pickupDelivery.getPicsUrl()));
        return SaResult.data(pickupDelivery);
    }

    @PostMapping
    public SaResult add(@RequestBody PickupDelivery pickupDelivery,@RequestHeader("Authorization") String token) {
        User user = jwtUtil.parseJwt(token,User.class);
        pickupDelivery.setPublisherId(user.getId());
        pickupDelivery.setPublisherName(user.getNickname());
        pickupDelivery.setAvatar(user.getAvatar());
        pickupDeliveryService.save(pickupDelivery);
        return SaResult.data(pickupDelivery.getId());
    }

    @PutMapping
    public SaResult update(@RequestBody PickupDelivery pickupDelivery) {
        boolean update = pickupDeliveryService.updateById(pickupDelivery);
        return SaResult.data(update);
    }

    @DeleteMapping
    public SaResult delete(@Validated @RequestParam("ids") @NotEmpty List<Long> ids) {
        boolean remove = pickupDeliveryService.removeBatchByIds(ids);
        return SaResult.data(remove);
    }
}
