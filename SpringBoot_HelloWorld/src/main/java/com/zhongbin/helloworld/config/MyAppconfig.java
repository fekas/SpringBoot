package com.zhongbin.helloworld.config;

import com.zhongbin.helloworld.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot推荐用配置类来代替Spring中的配置文件，配置类用@Configuration标注
 * 在配置文件中用bean标签添加组件，在配置类中用@bean
 *
 */
@Configuration
public class MyAppconfig {

    //@Bean:将方法的返回值添加到容器中，容器中组件的默认id就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("配置类给容器中添加组件");
        return new HelloService();
    }

}
