package learn.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // Marks this class as a configuration class for Spring.
@EnableWebMvc  // Enables default Spring MVC configurations.
@ComponentScan(basePackages = {"learn.controller", "learn.service","learn.config"})
// Specifies the packages to scan for Spring components like Controllers and Services.
@MapperScan("learn.mapper")  // Configures MyBatis to scan the specified package for mapper interfaces.
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    // Defines a bean for the view resolver.
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/"); // Sets the prefix for the view files.
        resolver.setSuffix(".jsp"); // Sets the suffix for the view files (here, JSP files).
        return resolver;
    }

    @Override
    // Configures resource handlers to serve static resources.
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**") // Specifies the URL pattern for static resources.
            .addResourceLocations("classpath:/static/"); // Specifies the location of static resources.
    }

}
