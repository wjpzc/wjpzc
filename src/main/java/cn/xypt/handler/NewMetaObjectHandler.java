package cn.xypt.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangchao
 * @date 2024/4/17
 */
@Component
public class NewMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
      this.setFieldValByName("updateTime", new Date(), metaObject);
      this.setFieldValByName("createTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
      this.setFieldValByName("updateTime", new Date(), metaObject);
    }


}
