package com.example.demo.req;

import com.example.demo.enumeration.WorkType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
