package org.extvos.builtin.quartz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author shenmc
 */
@Configuration
@EntityScan("org.extvos.builtin.quartz.entity")
@MapperScan("org.extvos.builtin.quartz.mapper")
@ComponentScan(basePackages = "org.extvos.builtin.quartz")
public class BuiltinQuartzAutoConfigure {
    @Bean
    public Docket createQuartzDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("任务调度服务")
                .apiInfo(new ApiInfoBuilder()
                        .title("任务调度服务")
                        .description("Builtin Quartz services for generic use.")
                        .contact(new Contact("Mingcai SHEN","https://gitlab.inodes.cn/","archsh@gmail.com"))
                        .termsOfServiceUrl("https://gitlab.inodes.cn/quickstart/java-scaffolds/quick-builtin-quartz.git")
                        .version(getClass().getPackage().getImplementationVersion())
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.extvos.builtin.quartz"))
                .build();
    }
}
