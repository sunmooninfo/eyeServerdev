package com.eye.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import jodd.util.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger在线文档配置<br>
 * 项目启动后可通过地址：http://host:ip/swagger-ui.html 查看在线文档
 *
 * @author enilu
 * @version 2018-07-24
 */

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class CommonSwagger2Configuration {
    @Bean
    public Docket commonDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("common")
                .apiInfo(cmsApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.eye.common.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo cmsApiInfo() {
        return new ApiInfoBuilder()
                .title("eye-common API")
                .description("付费知识平台 API")
                .termsOfServiceUrl("https://git.6eye9.com/eye/eyeServerdev.git")
                .contact("https://git.6eye9.com/eye/eyeServerdev.git")
                .version("1.0")
                .build();
    }
}
