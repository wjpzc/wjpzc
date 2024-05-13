package cn.xypt.model.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * 分页参数
 *
 * @author jinhong
 * @date 2023/01/13
 */
@Data
public class BaseQueryDTO {

    @ApiModelProperty("当前页，默认为1")
    @Min(value = 1, message = "current最小为1")
    private Integer current = 1;

    @ApiModelProperty("页大小，默认为4")
    @Min(value = 1, message = "size最小为1")
    private Integer size = 4;

    @ApiModelProperty("主键")
    private Integer id;
}
