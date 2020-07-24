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
        DsCheckResult result = DsUtil.checkDsByMethod(pjp);
        log.debug("DsCheckResult:" + result);
        if (result.isTenant()) {
            log.debug("Set Tenant Datasource for:" + result.getClassName() + ":" + result.getMethodName());
            Assert.isTrue(!"masterDs".equals(DsSchemaHolder.getSchema()), "tenant schema not equals MASTER_DS");
        } else if (result.isPlatform()) {
            log.debug("Set Platform Datasource for: " + result.getClassName() + ":" + result.getMethodName());
            DsSchemaHolder.setSchema("masterDs");
        } else {
            //默认连接主数据源
            log.debug("Set default Datasource for: " + result.getClassName() + ":" + result.getMethodName());
            DsSchemaHolder.setSchema("masterDs");
        }
        Object returnObj = pjp.proceed();
        return returnObj;
    }
}
