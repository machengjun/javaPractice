package com.example.saas.annotation;

import java.lang.annotation.*;

/**
 * @author 马成军
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantDS {
}

