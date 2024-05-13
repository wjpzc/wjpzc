package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qqcn
 * @since 15:04:45
 */
@TableName("base_param")
public class BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 参数
     */
    private String baseName;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数描述
     */
    private String paramDesc;

    /**
     * 排序
     */
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "BaseParam{" +
            "id = " + id +
            ", baseName = " + baseName +
            ", paramName = " + paramName +
            ", paramValue = " + paramValue +
            ", paramDesc = " + paramDesc +
            ", priority = " + priority +
        "}";
    }
}
