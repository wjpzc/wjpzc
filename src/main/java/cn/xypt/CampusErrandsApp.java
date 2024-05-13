package cn.xypt;



import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableAsync(proxyTargetClass = true)
@EnableFileStorage
@SpringBootApplication
public class CampusErrandsApp {

  public static void main(String[] args) {
    SpringApplication.run(CampusErrandsApp.class, args);
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  //1.开放全部页面
  //2.权限不足问题没有解决
  //3.错误代码没有处理
  //4.更换头像有问题 userId没有传入


}
