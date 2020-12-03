package com.eye.admin.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import jodd.util.StringUtil;
import springfox.documentation.RequestHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class AdminSwagger2Configuration {

    private static final String SPLITOR = ";";
    @Bean
    public Docket adminDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(adminApiInfo())
                .select()
                .apis(scanBasePackage("com.eye.admin.web"+SPLITOR))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("eye-admin API")
                .description("付费知识平台 API")
                .termsOfServiceUrl("https://git.6eye9.com/eye/eyeServerdev.git")
                .contact("https://git.6eye9.com/eye/eyeServerdev.git")
                .version("1.0")
                .build();
    }


    /**
     * 切割扫描的包生成Predicate<RequestHandler>
     * @param basePackage
     * @return
     */
    public static Predicate<RequestHandler> scanBasePackage(final String basePackage) {
        if(StringUtil.isBlank(basePackage)){
            throw new NullPointerException("basePackage不能为空，多个包扫描使用"+SPLITOR+"分隔");
        }
        String[] controllerPack = basePackage.split(SPLITOR);
        Predicate<RequestHandler> predicate = null;
        for (int i = controllerPack.length -1; i >= 0 ; i--) {
            String strBasePackage = controllerPack[i];
            if(StringUtil.isNotBlank(strBasePackage)){
                Predicate<RequestHandler> tempPredicate = RequestHandlerSelectors.basePackage(strBasePackage);
                predicate = predicate == null ? tempPredicate : Predicates.or(tempPredicate,predicate);
            }
        }
        if(predicate == null){
            throw new NullPointerException("basePackage配置不正确，多个包扫描使用"+SPLITOR+"分隔");
        }
        return predicate;
    }

}
