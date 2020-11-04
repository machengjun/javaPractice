package com.example.validate.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 马成军
 **/
@Data
public class ArticleCreateReq {
    @NotNull
    @NotBlank(message = "标题不为空")
    private String title;

    @NotBlank(message = "内容不为空")
    private String content;

    @NotNull
    @NotBlank(message = "作者不为空")
    private String authorId;

}
