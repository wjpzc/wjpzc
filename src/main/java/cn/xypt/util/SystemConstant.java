package cn.xypt.util;

/**
 * 系统常量 约定：0-否，1-是
 *
 * @author zhangchao
 * @date 2024/04/17
 */
public class SystemConstant {


    /**
     * 初始密码
     */
    public static final String INITIAL_PASSWORD = "123456";

    /**
     * 学院名字典父级id
     */
    public static final int COLLEGE_NAME_DICT_PARENT_ID = 1;



    /**
     * 未上传
     */
    public static final int NOT_UPLOAD = 0;

    /**
     * 未确认
     */
    public static final int NOT_CONFIRMED = 0;

    /**
     * 未完成
     */
    public static final int NOT_FINISHED = 0;

    /**
     * 未通过
     */
    public static final int FAILED = -1;


    /**
     * 已通过
     */
    public static final int PASS = 1;

    /**
     * 已完成
     */
    public static final int FINISHED = 1;

    /**
     * 审核中
     */
    public static final int UNDER_REVIEW = 2;

    /**
     * 已确认不修改
     */
    public static final int CONFIRMED_NOT_MODIFY = 1;

    /**
     * 已确认修改
     */
    public static final int CONFIRMED_MODIFY = 2;


    /**
     * 系统插入 学生类型，成绩上传时不存在的学生信息自动插入
     */
    public static final String INSERT_BY_SYSTEM = "系统插入";

    /**
     * 导出文件正在生成
     */
    public static final Integer GENERATING = 0;

    /**
     * 导出文件已生成
     */
    public static final Integer GENERATED = 1;


    /**
     * 临时文件的目录
     */
    public static final String BASE_TEMP_DIR = "/root/gcrz/temp";

}