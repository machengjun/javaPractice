package com.example.saas.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 马成军
 **/
@Data
@Accessors(chain = true) // 链式方法
public class DataSourceVo {
    private String  driver;
    private String  url;
    private String  username;
    private String  password;
    private String  schema;
}
