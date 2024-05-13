package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 寻物启事
 * @TableName find_notice
 */
@TableName(value ="find_notice")
@Data
public class FindNotice implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer publisherId;

    /**
     * 标题
     */
    private String title;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 物品类型，多个类型用逗号隔开
     */
    private String types;
    private String publisherName;
    private String avatar;

    @TableField(exist = false)
    private String avatarUrl;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 联系方式，电话/微信/QQ
     */
    private String contactInfo;

    /**
     * 图片地址，有多个则用逗号隔开
     */
    private String picsUrl;

    /**
     * 
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private String deleted = "0";
}