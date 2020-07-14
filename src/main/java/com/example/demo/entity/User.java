package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author 马成军
 **/
@Data
@Entity
@Table(name = "user")
@TableName(value = "user")
public class User extends BaseEntity {

    @Column(name = "name")
    @TableField(value = "name")
    private String name;


    @Column(name = "ArticleMapperage")
    @TableField(value = "age")
    private Integer age;

}
