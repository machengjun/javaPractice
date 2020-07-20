package com.example.mybatis.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 马成军
 **/
@Data
public class AuthorCreateDto {

    @NotBlank(message = "不为空")
    private String name;

    @NotBlank(message = "不为空")
    private Integer age;
}
