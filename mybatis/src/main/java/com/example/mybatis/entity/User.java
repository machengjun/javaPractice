package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mybatis.enumeration.WorkType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "work_type")
    @TableField(value = "work_type")
    @Enumerated(EnumType.STRING)
    private WorkType workType;



//    @Transient
//    @Column(name = "other_info")
//    @TableField(value = "other_info",typeHandler= FastjsonTypeHandler.class)
//    private OtherInfo otherInfo;

    @TableLogic
    @Column(name = "is_delete", columnDefinition = "tinyint")
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @Column(name = "delete_time")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime deleteTime;

}
