package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 字典表(拟将学院名，角色名，开课学期等一些常量存放在此表)
 * @TableName dict
 */
@ApiModel
@TableName(value ="dict")
@Data
public class Dict implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    /**
     * 常量名
     */
    @ApiModelProperty(value = "常量名", required = true)
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
