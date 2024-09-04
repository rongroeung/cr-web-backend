package kh.com.acledabank;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kh.com.acledabank.utility.CustomHeaderFilter;

@SuppressWarnings("deprecation")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean<CustomHeaderFilter> customHeaderFilter() {
        FilterRegistrationBean<CustomHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomHeaderFilter());
        registrationBean.addUrlPatterns("/*"); // Apply the filter to all URLs
        return registrationBean;
    }
}
