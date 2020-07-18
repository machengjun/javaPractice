package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author 马成军
 **/
@Data
@Entity
@Table(name = "address")
@TableName(value = "address")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Address extends BaseEntity {


    @Column(name = "city")
    @TableField(value = "city")
    private String city;

    @Transient
    @Column(name = "author_id")
    @TableField(value = "author_id")
    private String authorId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @TableField(exist = false)
    private Author author;
}
