package cn.xypt.model.dto;

import cn.xypt.handler.SexConverter;
import cn.xypt.util.AddGroup;
import cn.xypt.util.UpdateGroup;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jinhong
 * @date 2023/01/12
 */
@ApiModel
@Data
public class UserDTO {
    /**
     * 主键
     */
    @ExcelIgnore
    @ApiModelProperty(value = "新增时非必填，修改时必填")
    @NotNull(groups = UpdateGroup.class, message = "id 不能为空")
    private Integer id;

    @ExcelProperty("注册类型")
    private Integer type;

    /**
     * 真实姓名
     */
    @ExcelProperty("昵称")
    @ApiModelProperty(value = "新增时必填")
    @NotNull(groups = AddGroup.class, message = "nickname 昵称不能为空")
    private String nickname;

    /**
     * 性别，0：男，1：女
     */
    @ExcelProperty(value = "性别", converter = SexConverter.class)
    @ApiModelProperty("性别，0：男，1：女")
    private Integer sex;

    /**
     * 用户名
     */
    @ExcelProperty("用户名\n（不允许重复）")
    @ApiModelProperty(value = "新增时必填")
    @NotNull(groups = AddGroup.class, message = "username 用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ExcelIgnore
    @ApiModelProperty(value = "新增时必填")
    @NotBlank(groups = AddGroup.class, message = "password 密码不能为空")
    private String password;

    /**
     * 学校
     */
    @ExcelProperty("学校")
    private String school;



    /**
     * 手机号码
     */
    @ExcelProperty("手机号码")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 角色列表，存储json数组
     */
    @ExcelIgnore
    @ApiModelProperty(value = "新增时必填")
    @NotEmpty(groups = AddGroup.class, message = "roleList 角色列表不能为空")
    private List<String> roleList;
}
