package com.example.mybatis.req;

import com.example.mybatis.enumeration.WorkType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 马成军
 **/
@Data
public class UserCreateDto {

    @NotBlank(message = "不为空")
    private String name;

    @NotBlank(message = "不为空")
    private Integer age;

    @NotBlank(message = "不为空")
    private WorkType workType;
}
