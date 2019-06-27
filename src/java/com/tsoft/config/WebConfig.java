//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.config;

import com.tsoft.web.converter.DateConverter;
import com.tsoft.web.converter.FileConverter;
import com.tsoft.web.converter.JoinColumnConverter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(
    basePackages = {"com.tsoft"},
    excludeFilters = {@Filter(
    value = {Service.class},
    type = FilterType.ANNOTATION
), @Filter(
    value = {Repository.class},
    type = FilterType.ANNOTATION
)}
)
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    JoinColumnConverter mtoc;

    public WebConfig() {
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter mjhmc = new MappingJackson2HttpMessageConverter();
        ByteArrayHttpMessageConverter bhmc = new ByteArrayHttpMessageConverter();
        converters.add(mjhmc);
        converters.add(bhmc);
    }

    @Bean
    public ConversionServiceFactoryBean getConversionServiceFactoryBean() {
        ConversionServiceFactoryBean csfb = new ConversionServiceFactoryBean();
        Set converters = new HashSet();
        converters.add(new DateConverter());
        converters.add(new FileConverter());
        converters.add(this.mtoc);
        csfb.setConverters(converters);
        return csfb;
    }

    @Bean(
        name = {"CommonsMultipartResolver"}
    )
    public MultipartResolver getMultipartResolver() {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(1000000L);
        return cmr;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/app/**"}).addResourceLocations(new String[]{"/app/"});
    }
}
