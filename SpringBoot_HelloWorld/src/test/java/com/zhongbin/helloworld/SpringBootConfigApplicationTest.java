package com.zhongbin.helloworld;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zhongbin.helloworld.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/*
*SpringBoot单元测试
* 可以在测试期间自动注入
* */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootConfigApplicationTest {

    @Autowired
    ApplicationContext ioc;

    @Autowired
    Person person;

    //得到记录器
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test01(){
        System.out.println(person);
    }

    @Test
    public void test02(){
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }

    @Test
    public void test03(){

        //日志级别trace<debug<info<error
        //日志级别的作用：可以供程序员调整日志输出的级别
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        logger.info("这是warning日志..");
        logger.error("这是error日志..");
    }
}
