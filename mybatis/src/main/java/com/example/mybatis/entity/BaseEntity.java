package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base Entity
 * 声明各实体类的公共属性
 *
 * @author 马成军
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    private static final int ID_LENGTH = 36;

    /**
     * ID
     * 生成器名称，uuid生成类
     */
    @Id
    @Column(length = ID_LENGTH, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @TableId(value = "id", type = IdType.UUID)
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

    /**
     * 清除 baseEntity 各基础属性
     */
    public void clearProps() {
        this.setId(null);
        this.setCreateTime(null);
        this.setModifyTime(null);
        this.setVersion(null);
    }

}