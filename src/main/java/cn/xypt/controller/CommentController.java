package cn.xypt.controller;

import cn.xypt.util.MinioUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xypt.model.domain.Comment;
import cn.xypt.service.ICommentService;
import cn.xypt.common.vo.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qqcn
 * @since 15:03:44
 */

@Api(tags = "评论功能")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @ApiOperation("发表评论")
    @PostMapping
    public Result<Object> addComment(@RequestBody Comment comment){
        commentService.save(comment);
        return Result.success();
    }

    @Autowired
    private MinioUtils minioUtils;

    @ApiOperation("分页查询评论列表")
    @GetMapping("/page")
    public Result<Map<String,Object>> getCommentList(@RequestParam("foundId") Long foundId,
                                                     @RequestParam("current") Long current,
                                                     @RequestParam("size") Long size){
        Page<Comment> page = new Page<>(current,size);

        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getFoundId,foundId);
        wrapper.orderByDesc(Comment::getId);

        commentService.page(page,wrapper);

        List<Comment> commentList = page.getRecords().stream().map(item -> {
            item.setAvatar(minioUtils.getUrl(item.getAvatar()));
            return item;
        }).collect(Collectors.toList());

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",commentList);


        return Result.success(data);
    }


}
