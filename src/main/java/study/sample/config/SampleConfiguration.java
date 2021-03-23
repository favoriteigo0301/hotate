package study.sample.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *　コンフィグレーションクラス
 */
@Configuration
@ConfigurationProperties(prefix = "sample")
@Data
public class SampleConfiguration implements WebMvcConfigurer {

    private int maxPageSize;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        // 1ページあたりのページ件数はプロパティファイルから取得
        resolver.setMaxPageSize(maxPageSize);
        resolvers.add(resolver);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/sample/login").setViewName("login");
    }
}
