package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 马成军
 **/
@Data
@Entity
@Table(name = "author")
@TableName(value = "author")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Author extends BaseEntity {


    @Column(name = "name")
    @TableField(value = "name")
    private String name;

    @Column(name = "age")
    @TableField(value = "age")
    private Integer age;


}
