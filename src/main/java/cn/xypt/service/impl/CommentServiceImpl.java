package cn.xypt.service.impl;

import cn.xypt.model.domain.Comment;
import cn.xypt.mapper.CommentMapper;
import cn.xypt.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qqcn
 * @since 15:03:44
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
