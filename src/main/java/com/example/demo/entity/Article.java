package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author 马成军
 **/
@Data
public class Article extends BaseEntity {


    /**
     * 关联作者
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @TableField(value = "author_id", el = "author.id")
    private Author author;


    @Column(name = "title")
    @TableField(value = "title")
    private String title;


    @Column(name = "content")
    @TableField(value = "content")
    private String content;


}
