package com.project.properity;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//注解为TK包下的
//@MapperScan(basePackages = "com.project.properity.pojo")
@SpringBootApplication
public class ProperityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProperityApplication.class, args);
    }

}
