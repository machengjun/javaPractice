package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * @author 马成军
 **/
@Data
@Entity
@Table(name = "person")
@TableName(value = "person")
public class Person extends BaseEntity {

    @Column(name = "name")
    @TableField(value = "name")
    private String name;


    @Column(name = "age")
    @TableField(value = "age")
    private Integer age;


    /**
     * ID
     * 生成器名称，uuid生成类
     */
    @Column(name="id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

    /**
     * 版本锁
     */
    @Version
    @com.baomidou.mybatisplus.annotation.Version
//    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

}
