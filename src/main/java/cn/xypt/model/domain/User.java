package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user", autoResultMap = true)
@Data
public class User implements Serializable {
    public static Class<? extends User> calss = User.class;

    //userserviceimpl中使用

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 学校
     */
    private String school;

    private Integer type;

    private Integer level;



    /**
     * 性别，M：男，F：女,X：保密
     */
    private String sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    @TableField(exist = false)
    private String avatarUrl;

    private String openid;



    /**
     * 角色列表，存储json数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> roleList;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
