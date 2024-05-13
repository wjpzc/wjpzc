package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 快递代取
 * @TableName express_pickup
 */
@TableName(value ="express_pickup")
@Data
public class ExpressPickup implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @ApiModelProperty("发布者id")
    private Integer publisherId;

    @ApiModelProperty("标题")
    private String title ;

    /**
     * 收件人
     */
    @ApiModelProperty("收件人")
    private String receiver;

    /**
     * 收件人地址
     */
    @ApiModelProperty("收件人地址")
    private String receiverAddress;

    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;

    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String contactInfo;

    /**
     * 取件码
     */
    @ApiModelProperty("取件码")
    private String pickupCode;
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String apartment;
    private String publisherName;
    private String avatar;

    @TableField(exist = false)
    private String avatarUrl;
    /**
     * 
     */
    @ApiModelProperty("状态")
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}