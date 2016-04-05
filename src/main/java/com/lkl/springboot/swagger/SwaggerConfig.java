package com.lkl.springboot.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger ui 配置
 * visit api docs : http://localhost:8080/swagger-ui.html 
 * doc: http://springfox.github.io/springfox/docs/current/
 * @author lkl
 * @version $Id: SwaggerConfig.java, v 0.1 2015年8月4日 下午2:54:21 lkl Exp $
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    public static final String PROJECT_NAME    = "springboot";
    public static final String PROJECT_VERSION = "v1";

    /**
     * 
     * Docket ： 提供swagger与springmvc整合的构建器
     * @return
     */
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiInfo apiInfo = new ApiInfo("springboot-api", "spring boot by liaokailin.", PROJECT_VERSION, "u51",
            "test@163.com", "lkl auth", "http://www.baidu.com");
        Docket docket = new Docket(DocumentationType.SWAGGER_2); //指定使用的swagger 版本
        docket.groupName(PROJECT_NAME) //组名
            .select() //构建选择器
            .apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex("/(" + PROJECT_NAME + "/).*")) //指定匹配路径
            .build().apiInfo(apiInfo) //指定api信息
            .useDefaultResponseMessages(false); //取消默认的返回信息
        return docket;
    }

}
