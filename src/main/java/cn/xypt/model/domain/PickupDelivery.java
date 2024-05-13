package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 校内取送
 * @TableName pickup_delivery
 */
@TableName(value ="pickup_delivery")
@Data
public class PickupDelivery implements Serializable {
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
     * 
     */
    private String title;

    /**
     * 类型，有多个则用逗号隔开
     */
    private String types;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 源地址
     */
    private String sourceAddress;

    /**
     * 目标地址
     */
    private String targetAddress;
    private String publisherName;
    private String avatar;

    @TableField(exist = false)
    private String avatarUrl;

    /**
     * 备注信息
     */
    private String remark;
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private String picsUrl;

    /**
     * 
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}