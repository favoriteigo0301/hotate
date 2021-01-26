package study.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger設定クラス
 * API概要と詳細について記述
 * URL:{ドメイン}/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("sample-api")
                .select()
                .paths(PathSelectors.ant("/api/memo/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("サンプルメモアプリ")
                .description("サンプルメモAPI仕様について")
                .version("1.0.0")
                .build();
    }
}
