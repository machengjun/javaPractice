package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author 马成军
 **/
@Data
public class BaseEntity {
    private static final int ID_LENGTH = 36;

    @Id
    @Column(length = ID_LENGTH, nullable = false)
    @GeneratedValue(generator = "uuid")
    /**
     *生成器名称，uuid生成类
     */
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @TableId(value = "id", type = IdType.UUID)
    /**
     * id
     */
    private String id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    public BaseEntity() {
    }

    /**
     * 清除 baseEntity 各基础属性
     */
    public void clearProps() {
        this.setId(null);
        this.setCreateTime(null);
        this.setModifyTime(null);
    }
}
