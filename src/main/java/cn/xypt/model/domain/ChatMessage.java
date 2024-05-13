package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class ChatMessage {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long uid;
    private Long fid;
    private String content;
    private Integer format;
    private Integer isread;
    private Date addtime;
}
