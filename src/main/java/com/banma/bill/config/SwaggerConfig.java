package com.banma.bill.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author binglang
 */
@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Autowired
    private Environment env;

    @Profile({"default", "pro"})
    @Bean
    public Docket createWebApi() {
        return new Docket(DocumentationType.OAS_30)
            .enable(false)
            .select().build();
    }

    @Profile("test")
    @Bean
    public Docket createWebApiTest() {
        return new Docket(DocumentationType.OAS_30)
            .host(getIp() + getPort())
            .protocols(Sets.newHashSet("http", "https"))
            .apiInfo(new ApiInfoBuilder()
                .title("bill")
                .description("bill api接口文档")
                .version("1.0")
                .build())
            .globalRequestParameters(Lists
                .newArrayList(new RequestParameterBuilder().name("token").in(ParameterType.HEADER)
                    .description("登录校验")
                    .query(p -> p.model(m -> m.scalarModel(ScalarType.STRING)))
                    .required(true).build()))
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build();
    }

    private String getIp() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            // ignore
        }
        return "127.0.0.1";
    }

    private String getPort() {
        return env.getProperty("local.server.port");
    }
}
