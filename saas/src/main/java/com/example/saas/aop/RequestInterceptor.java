package com.example.saas.aop;

import com.example.saas.component.DsSchemaHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author 马成军
 **/
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    //获取header中的schema 存到 阿里线程变量中
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return true;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> schemaEnum = request.getHeaders("schema");
        log.debug("schema:{}" + schemaEnum);
        if (!schemaEnum.hasMoreElements()) {
            return true;
        }
        String schema = schemaEnum.nextElement();
        DsSchemaHolder.clear();
        DsSchemaHolder.setSchema(schema);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        DsSchemaHolder.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
