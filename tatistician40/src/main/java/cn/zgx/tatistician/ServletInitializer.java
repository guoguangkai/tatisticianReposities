package cn.zgx.tatistician;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/*继承自SpringBootServletInitializer方可正常部署至常规tomcat下，其主要起到web.xml的作用*/
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TatisticianApplication.class);
    }

}
