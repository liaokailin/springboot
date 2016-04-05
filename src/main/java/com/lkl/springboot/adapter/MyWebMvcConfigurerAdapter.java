package com.lkl.springboot.adapter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lkl.springboot.annotation.NoAuth;
import com.lkl.springboot.utils.AuthTokenUtils;

/**
 * 添加自定义配置信息
 * 
 * @author lkl
 * @version $Id: MyWebMvcConfigurerAdapter.java, v 0.1 2015年7月28日 下午9:54:24 lkl Exp $
 */
@Component
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        System.out.println("--addFormatters--");
        super.addFormatters(registry);
    }

    /**
     *   @Bean
      public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
          MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
          ObjectMapper objectMapper = new ObjectMapper();
          objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          jsonConverter.setObjectMapper(objectMapper);
          return jsonConverter;
      }
     */

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // TODO Auto-generated method stub
        super.configureMessageConverters(converters);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // TODO Auto-generated method stub
        super.extendMessageConverters(converters);
    }

    /**
     * 自定义验证器，自定义错误消息
     */
    @Override
    public Validator getValidator() {
        System.out.println("-----自定义验证器，自定义错误消息----");
        return validator();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.setBasename("classpath:validator");
        bean.setDefaultEncoding("UTF-8");
        bean.setCacheSeconds(120);
        return bean;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // TODO Auto-generated method stub
        super.configureContentNegotiation(configurer);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // TODO Auto-generated method stub
        super.configureAsyncSupport(configurer);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // TODO Auto-generated method stub
        super.configurePathMatch(configurer);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // TODO Auto-generated method stub
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // TODO Auto-generated method stub
        super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // TODO Auto-generated method stub
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        // TODO Auto-generated method stub
        return super.getMessageCodesResolver();
    }

    public static final String PARAM_KEY_TIMESTAMP = "timestamp";
    public static final String PARAM_KEY_TOKEN     = "token";

    @Bean
    private HandlerInterceptor myCustomerInterceptor() {
        return new HandlerInterceptor() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                                                                                                              throws Exception {
                boolean turnOn = false; //开关
                if (turnOn) {
                    System.out.println(" ===myCustomerInterceptor===>preHandle:" + handler);
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
                    if (noAuth != null) {
                        return true;
                    } else {
                        System.out.println("无noAuth");
                        response.setStatus(400);

                        Enumeration<String> params = request.getParameterNames();
                        String _timestamp_ = null, _token_ = null;
                        if (params != null) {
                            Set<String> elements = new HashSet<String>();
                            Map<String, String[]> map = new HashMap<String, String[]>();
                            while (params.hasMoreElements()) {
                                String element = params.nextElement();

                                if (PARAM_KEY_TIMESTAMP.equals(element) || PARAM_KEY_TOKEN.equals(element)) {
                                    _timestamp_ = request.getParameter(element);
                                    continue;
                                }

                                if (PARAM_KEY_TOKEN.equals(element)) {
                                    _token_ = request.getParameter(element);
                                    continue;
                                }

                                String[] value = request.getParameterValues(element);
                                if (value != null && value.length > 0) {
                                    elements.add(element);
                                    map.put(element, value);
                                }
                            }
                            String paramKey = AuthTokenUtils.obatinParamsKey(elements, map);
                            if (_token_ != null && _token_.equals(AuthTokenUtils.geneToken(paramKey + _timestamp_))) {
                                return true;
                            }
                        }

                        return false;
                    }
                } else {
                    return true;
                }
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                                   ModelAndView modelAndView) throws Exception {
                //   System.out.println(" ===myCustomerInterceptor===>postHandle : " + handler);

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                        Exception ex) throws Exception {
                //   System.out.println(" ===myCustomerInterceptor===>afterCompletion : " + handler);

            }
        };
    }

    /**
     * 添加额外的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myCustomerInterceptor());
        //  super.addInterceptors(registry);

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // TODO Auto-generated method stub
        super.addViewControllers(registry);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // TODO Auto-generated method stub
        super.configureViewResolvers(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // TODO Auto-generated method stub
        super.configureDefaultServletHandling(configurer);
    }

}
