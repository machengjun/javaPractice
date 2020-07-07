package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author 马成军
 **/
@Data
@Entity
@Table(name = "article")
@TableName(value = "article")
@ToString(callSuper = true, exclude = {"author"})
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseEntity {
    /**
     * 关联作者
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @TableField(exist = false)
    private Author author;

    /**
     * author_id(for mybatis)
     */
    @Transient
    @TableField(value = "author_id")
    private String authorId;

    @Column(name = "title")
    @TableField(value = "title")
    private String title;


    @Column(name = "content")
    @TableField(value = "content")
    private String content;


}
