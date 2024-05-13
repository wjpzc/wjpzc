package cn.xypt.util;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author zhangchao
 * @date 2024/04/17
 */
public class PasswordUtil {

    private final static String SALT = "!@#erwe03";

    public static String getMD5Pwd(String origin) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(SALT + origin);
    }

    /**
     * 校验密码强度，校验规则：
     */
    public static void checkPassword(String password) {
        password = StrUtil.trim(password);
        if(password.length() < 6){
            throw new RuntimeException("密码长度不能小于6");
        }
        boolean containLetter = false;
        boolean containNumber = false;
        for (char ch : password.toCharArray()) {
            if(CharUtil.isLetter(ch)){
                containLetter = true;
            }
            if(CharUtil.isNumber(ch)){
                containNumber = true;
            }
        }
        if(!containLetter){
            throw new RuntimeException("密码需要包含字母");
        }
        if(!containNumber){
            throw new RuntimeException("密码需要包含数字");
        }
    }
}
