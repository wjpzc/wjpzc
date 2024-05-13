package cn.xypt.mapper;

import cn.xypt.model.domain.User;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
* @author zhangchao
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-04-17 11:50:07
* @Entity generator.domain.User
*/
@CacheNamespace(flushInterval = 3600, size = 100)
public interface UserMapper extends MPJBaseMapper<User> {
    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);


    @Update("update user set avatar = #{avatar} where id = #{userId}")
    void updateUserAvatar(Integer userId,String avatar);
    @Select("select * from user where openid = #{openid}")
    User getUSerByOpenid(String openid);
}




