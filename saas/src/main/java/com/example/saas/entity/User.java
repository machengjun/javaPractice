package com.example.saas.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 马成军
 **/
@Data
@Entity
@Table(name = "user")
@TableName(value = "user")
public class User extends BaseEntity {


    @Column(name = "name")
    @TableField(value = "name",fill = FieldFill.UPDATE)
    private String name;


    @Column(name = "age")
    @TableField(value = "age")
    private Integer age;
}
