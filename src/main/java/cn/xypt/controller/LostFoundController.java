package cn.xypt.controller;

import static cn.xypt.util.RoleConstant.ADMIN;
import static cn.xypt.util.RoleConstant.STUDENT;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.util.SaResult;
import cn.xypt.common.utils.JwtUtil;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.domain.LostFound;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.QueryLostFoundDTO;
import cn.xypt.service.LostFoundService;
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
@Api(tags = "失物招领")
@RestController
@RequestMapping("/lostFound")
public class LostFoundController {

    @Resource
    private LostFoundService lostFoundService;
    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation("分页查询")
    @SaCheckRole(value = {ADMIN,  STUDENT}, mode = SaMode.OR)
    @GetMapping("/page")
    public SaResult getPage(@Validated QueryLostFoundDTO queryLostFoundDTO) {
        return lostFoundService.getPage(queryLostFoundDTO);
    }

@Autowired
private MinioUtils minioUtils;

    @ApiOperation("根据id查询详情")
    @GetMapping("/{id}")

    public SaResult getById(@PathVariable("id") Long id) {
        LostFound lostFound = lostFoundService.getById(id);
        lostFound.setAvatar(minioUtils.getUrl(lostFound.getAvatar()));
        lostFound.setPicsUrl(minioUtils.getUrl(lostFound.getPicsUrl()));
        return SaResult.data(lostFound);
    }

    @PostMapping
    public SaResult add(@RequestBody LostFound lostFound, @RequestHeader("Authorization") String token) {
        User user = jwtUtil.parseJwt(token,User.class);
        lostFound.setAvatar(user.getAvatar());
        lostFound.setPublisherId(user.getId());
        lostFound.setPublisherName(user.getNickname());
        lostFoundService.save(lostFound);
        return SaResult.data(lostFound.getId());
    }

    @PutMapping
    public SaResult update(@RequestBody LostFound lostFound) {
        boolean update = lostFoundService.updateById(lostFound);
        return SaResult.data(update);
    }

    @DeleteMapping
    public SaResult delete(@Validated @RequestParam("ids") @NotEmpty List<Long> ids) {
        boolean remove = lostFoundService.removeBatchByIds(ids);
        return SaResult.data(remove);
    }
}
