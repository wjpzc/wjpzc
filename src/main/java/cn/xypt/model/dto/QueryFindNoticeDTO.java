package cn.xypt.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangchao
 * @date 2024/04/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryFindNoticeDTO extends BaseQueryDTO{

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
     * 物品地址
     */
    private String address;

    /**
     *
     */
    private String remark;

    /**
     * 联系方式，电话/微信/QQ
     */
    private String contactInfo;

    /**
     * 状态
     */
    private String status;
    private String publisherName;
    private String avatar;

    @TableField(exist = false)
    private String avatarUrl;
}
