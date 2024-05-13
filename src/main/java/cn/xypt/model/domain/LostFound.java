package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 失物招领表
 * @TableName lost_found
 */
@TableName(value ="lost_found")
@Data
public class LostFound implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 发布者id
     */
    private Integer publisherId;

    /**
     * 标题
     */
    private String title;

    /**
     * 物品类型,多个类型用逗号隔开
     */
    private String types;

    /**
     * 物品地址
     */
    private String address;

    /**
     * 
     */
    private String remark;
    private String apartment;
    private String publisherName;
    private String avatar;

    @TableField(exist = false)
    private String avatarUrl;

    /**
     * 物品图片，多个图片用逗号隔开
     */
    @ApiModelProperty("物品图片，多个图片用逗号隔开")
    private String picsUrl;

    /**
     * 状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}