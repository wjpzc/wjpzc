package cn.xypt.model.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jinhong
 * @date 2023/01/12
 */
@Data
public class ChangePwdDTO {
    @ApiModelProperty(required = true)
    @NotNull(message = "userId 用户id不能为空")
    private Integer userId;
    @ApiModelProperty(required = true)
    @NotEmpty(message = "oldPassword 旧密码不能为空")
    private String oldPassword;
    @ApiModelProperty(required = true)
    @NotEmpty(message = "newPassword 新密码不能为空")
    private String newPassword;
}
