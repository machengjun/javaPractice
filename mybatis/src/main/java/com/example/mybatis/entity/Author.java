package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

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

    @Transient
    @TableField(exist = false)
    private List<Article> articles;


    @Transient
    @TableField(exist = false)
    private List<Address> addresses;
}
