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
public class PersonCreateDto {

    @NotBlank(message = "不为空")
    private String name;

    @NotNull
    @Size(min = 1, message = "结构标识不能为空")
    private List<String> strct;
}
