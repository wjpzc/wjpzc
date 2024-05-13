package cn.xypt.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文件记录表
 * @TableName file_detail
 */
@TableName(value ="file_detail")
@Data
public class FileDetail implements Serializable {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件访问地址
     */
    private String url;

    /**
     * 文件大小，单位字节
     */
    private Long size;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 原始文件名
     */
    private String originalFilename;

    /**
     * 基础存储路径
     */
    private String basePath;

    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * MIME类型
     */
    private String contentType;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上传人id
     */
    private Integer uploaderId;

    /**
     * 上传人名字
     */
    private String uploaderName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
