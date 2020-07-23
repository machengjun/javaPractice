package com.example.saas.aop;

/**
 * @author 马成军
 **/

import com.example.saas.bean.DsCheckResult;
import com.example.saas.component.DsSchemaHolder;
import com.example.saas.utils.DsUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
public class DynamicDsAdvice {
    private static final Logger log = LoggerFactory.getLogger(DynamicDsAdvice.class);
    @Autowired
    private AbstractRoutingDataSource routingDataSource;

    public DynamicDsAdvice() {
        log.debug("Constructor...");
    }

    @Pointcut("this(org.springframework.data.repository.Repository)")
    private void jpa2() {
    }

    @Pointcut("@within(com.example.saas.annotation.TenantDS)")
    private void jpa3() {
    }

//    @Pointcut("execution(* com.example.saas.dao.jpa.TenantUserDao.*(..))")
//    private void jpa4() {
//    }

    @Pointcut("@annotation(com.example.saas.annotation.DynamicDS)")
    private void jpa5() {
    }

    @Pointcut("execution(* com.example.saas.dao..*Mapper.*(..))")
    private void mapper() {
    }

    @Pointcut("jpa2()")
    private void dao() {
    }

    Object dynamicDs(ProceedingJoinPoint pjp) throws Throwable {
        DsSchemaHolder.clear();
        Assert.isNull(DsSchemaHolder.getSchema(), "DsSchemaHolder 中schema必须为空!");
        if (DsSchemaHolder.isEmpty()) {
            DsCheckResult result = DsUtil.checkDsByMethod(pjp);
            log.debug("DsCheckResult:" + result);
            if (result.isTenant()) {
                log.debug("Set Tenant Datasource for:" + result.getClassName() + ":" + result.getMethodName());
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                Enumeration<String> schemaEnum = request.getHeaders("schema");
                Assert.notNull(schemaEnum, "TenantDS: schema must not be blank!");
                String schema = (String)schemaEnum.nextElement();
                log.debug("schema:" + schema);
                Assert.isTrue(!schema.equals("masterDs"), "tenant schema not equals MASTER_DS");
                DsSchemaHolder.setSchema(schema);
            } else if (result.isPlatform()) {
                log.debug("Set Platform Datasource for: " + result.getClassName() + ":" + result.getMethodName());
                DsSchemaHolder.setSchema("masterDs");
            }
        }

        Assert.notNull(DsSchemaHolder.getSchema(), "DsSchemaHolder 中schema不为空!");
        Object returnObj = pjp.proceed();
        DsSchemaHolder.clear();
        return returnObj;
    }
}
