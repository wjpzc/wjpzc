package cn.xypt.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinhong
 * @date 2024/04/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryExpressPickupDTO extends BaseQueryDTO {

    /**
     * 发布者id
     */
    private Integer publisherId;

    /**
     * 标题
     */
    private String title;

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

    /**
     * 状态
     */
    private Integer status;
}
