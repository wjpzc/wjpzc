package cn.xypt.controller;



import cn.dev33.satoken.util.SaResult;
import cn.xypt.common.utils.JwtUtil;
import cn.xypt.common.vo.Result;
import cn.xypt.model.domain.FindNotice;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.QueryFindNoticeDTO;
import cn.xypt.service.FindNoticeService;
import cn.xypt.util.MinioUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
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
@Api(tags = "寻物启事")
@RestController
@RequestMapping("/findNotice")
public class FindNoticeController {

    @Resource
    private FindNoticeService findNoticeService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MinioUtils minioUtils;

    @ApiOperation("分页查询")
    @GetMapping("/page")

    public SaResult getPage(@Validated QueryFindNoticeDTO queryFindNoticeDTO) {
        return findNoticeService.getPage(queryFindNoticeDTO);
    }

    @ApiOperation("根据用户查询发布列表")
    @GetMapping("/user/page")

    public SaResult getPageByPublisher(@RequestParam("publisherId") Integer publisherId) {
        List<FindNotice> findNoticeList = findNoticeService.getPageByPublisher(publisherId);

        return SaResult.data(findNoticeList);
    }

    @ApiOperation("根据id查询详情")
    @GetMapping("/{id}")

    public SaResult getById(@PathVariable("id") Long id) {
        FindNotice findNotice = findNoticeService.getById(id);
        findNotice.setAvatar(minioUtils.getUrl(findNotice.getAvatar()));
        findNotice.setPicsUrl(minioUtils.getUrl(findNotice.getPicsUrl()));
        return SaResult.data(findNotice);
    }

    @PostMapping
    public SaResult add(@RequestBody FindNotice findNotice,@RequestHeader("Authorization") String token) {
        User user = jwtUtil.parseJwt(token,User.class);
        findNotice.setPublisherId(user.getId());
        findNotice.setPublisherName(user.getNickname());
        findNotice.setAvatar(user.getAvatar());
        findNoticeService.save(findNotice);
        return SaResult.data(findNotice.getId());
    }

    @PutMapping
    public SaResult update(@RequestBody FindNotice findNotice) {
        boolean update = findNoticeService.updateById(findNotice);
        return SaResult.data(update);
    }
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public SaResult delete(@PathVariable("id") Integer id) {
        boolean delete = findNoticeService.removeById(id);
        return SaResult.data(delete);
    }


}
