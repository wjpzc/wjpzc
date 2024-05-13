package cn.xypt.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinhong
 * @date 2023/01/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryUserDTO extends BaseQueryDTO {

    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("学校")
    private String school;
    @ApiModelProperty("用户角色（管理员，学生），传入角色字符串即可")
    private String role;
}
