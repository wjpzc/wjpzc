package cn.xypt.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinhong
 * @date 2024/04/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPickupDeliveryDTO extends BaseQueryDTO{

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
    @ApiModelProperty("物品类型，模糊查询")
    private String type;

    /**
     * 源地址
     */
    private String sourceAddress;

    /**
     * 目标地址
     */
    private String targetAddress;


    /**
     *
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;
}
