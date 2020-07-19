package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.Version;
import com.example.demo.enumeration.WorkType;
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


    @Column(name = "age")
    @TableField(value = "age")
    private Integer age;

    @Column(name = "work_type")
    @TableField(value = "work_type")
    @Enumerated(EnumType.STRING)
    private WorkType workType;

    @TableLogic
    @Column(name = "is_delete", columnDefinition = "tinyint")
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete;

}
