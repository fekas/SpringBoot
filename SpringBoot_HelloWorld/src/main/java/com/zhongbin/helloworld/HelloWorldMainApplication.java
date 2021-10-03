package com.zhongbin.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
* @SpringBootApplication标注一个主程序说明这是一个springboot应用
* */
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {

        /*启动Spring应用*/
        SpringApplication.run(HelloWorldMainApplication.class,args);
    }
}
