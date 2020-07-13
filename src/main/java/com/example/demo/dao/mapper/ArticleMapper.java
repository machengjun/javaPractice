package com.example.demo.dao.mapper;

import com.example.demo.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 马成军
 **/

@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}
