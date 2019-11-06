package cn.zgx.tatistician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.zgx.tatistician.dao")
@ComponentScan(basePackages = {"cn.zgx.tatistician.*"})
public class TatisticianApplication {
    /*extends SpringBootServletInitializer*/
    /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TatisticianApplication.class);
    }*/
    public static void main(String[] args) {
        SpringApplication.run(TatisticianApplication.class, args);
    }

}
