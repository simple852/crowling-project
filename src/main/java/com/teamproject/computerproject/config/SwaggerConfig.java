package com.teamproject.computerproject.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openapi()
   {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }


    private Info apiInfo() {
        return new Info()
               .title("API테스트")
                .description("오픈 스웨거")
               .version("1.0.0");
    }
}