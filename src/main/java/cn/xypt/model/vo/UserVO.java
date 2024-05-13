package cn.xypt.model.vo;

import java.util.List;
import lombok.Data;

/**
 * @author jinhong
 * @date 2023/01/11
 */
@Data
public class UserVO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 所属学院（从字典中取）
     */
    private String school;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 性别，0：男，1：女
     */
    private Integer sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    private Integer type;

    /**
     * 角色列表，存储json数组
     */
    private List<String> roleList;

}
