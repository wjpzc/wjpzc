package cn.xypt.model.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author jinhong
 * @date 2023/01/11
 */
@Data
public class LoginDTO {

    @ApiModelProperty("用户名")
    @NotBlank(message = "username 用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "password 密码不能为空")
    private String password;
}
