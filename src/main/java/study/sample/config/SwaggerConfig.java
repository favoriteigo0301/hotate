package study.sample.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket swaggerSpringPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("sample-api")
                .select()
                .paths(PathSelectors.ant("/api/memo/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "サンプルメモAPI"
                ,"サンプルメモのCRUD処理に使用する"
                ,"V1"
                ,"????"
                ,new Contact(
                        "イッシー"
                ,"localhost"
                ,"xxxxx@localhost.com")
                ,"API LICECSES"
                ,"htttp://xxxxx.co.jp"
                , new ArrayList<VendorExtension>()
        );
    }
}
