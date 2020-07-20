package com.example.mybatis.resp;

import lombok.Data;
import lombok.ToString;

/**
 * @author 马成军
 **/
@Data
@ToString
public class ArticleResp {

    private String title;

    private String content;

    private String authorId;

}
