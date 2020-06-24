package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import javax.persistence.Column;

/**
 * @author 马成军
 **/
public class Author extends BaseEntity {


    @Column(name = "name")
    @TableField(value = "name")
    private String name;

    @Column(name = "age")
    @TableField(value = "age")
    private Integer age;


}
