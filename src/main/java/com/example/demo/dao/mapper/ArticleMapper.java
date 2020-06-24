package com.example.demo.dao.mapper;

import com.example.demo.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 马成军
 **/

@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {


}
