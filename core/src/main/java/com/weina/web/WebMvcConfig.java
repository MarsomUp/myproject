package com.weina.web;

import com.weina.interceptor.RedisInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/14 10:39
 */
@SpringBootConfiguration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public RedisInterceptor redisInterceptor() {
        return new RedisInterceptor();
    }
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redisInterceptor());
    }



    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        //converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
    }
}
