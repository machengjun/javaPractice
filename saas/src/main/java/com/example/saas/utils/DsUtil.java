package com.example.saas.utils;

import com.example.saas.annotation.PlatformDS;
import com.example.saas.annotation.TenantDS;
import com.example.saas.bean.DsCheckResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author 马成军
 **/
public class DsUtil {
    private static final Logger log = LoggerFactory.getLogger(DsUtil.class);

    public DsUtil() {
    }

    public static DsCheckResult checkDsByType(JoinPoint joinPoint) {
        Class<?>[] classes = joinPoint.getTarget().getClass().getInterfaces();
        Class<?> realClass = null;
        Class<?> currentClass = null;

        for(int i = 0; i < classes.length; ++i) {
            currentClass = classes[i];
            String className = currentClass.getName();
            if (!className.startsWith("org.springframework")) {
                realClass = currentClass;
                break;
            }
        }

        if (realClass == null) {
            throw new RuntimeException("None spring data jpa instance found!");
        } else {
            TenantDS tenantDS = (TenantDS)realClass.getDeclaredAnnotation(TenantDS.class);
            String clazzName = realClass.getName();
            DsCheckResult result = new DsCheckResult(false, false, clazzName);
            return result;
        }
    }

    public static DsCheckResult checkDsByMethod(ProceedingJoinPoint pjp) {
        String signature = pjp.getSignature().toString();
        int pos1 = signature.indexOf(" ");
        int pos2 = signature.lastIndexOf(".");
        String realClassName = signature.substring(pos1 + 1, pos2);
        DsCheckResult result = null;

        try {
            Class targetClass = Class.forName(realClassName);
            String methodName = pjp.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
            TenantDS tenantDS = null;
            PlatformDS platformDS = null;
            Method method = null;

            try {
                method = targetClass.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException var14) {
                log.info("NoSuchMethodException class:" + targetClass + ", method:" + methodName);
                MethodSignature signature2 = (MethodSignature)pjp.getSignature();
                method = signature2.getMethod();
            }

            tenantDS = (TenantDS)method.getAnnotation(TenantDS.class);
            platformDS = (PlatformDS)method.getAnnotation(PlatformDS.class);
            result = new DsCheckResult(targetClass.getName(), methodName);
            if (tenantDS != null) {
                result.setTenant(true);
            } else {
                if (platformDS == null) {
                    throw new RuntimeException("TenantDS or PlatformDS is required for Saasable Type!");
                }

                result.setPlatform(true);
            }

            return result;
        } catch (ClassNotFoundException var15) {
            var15.printStackTrace();
            return result;
        }
    }
}
