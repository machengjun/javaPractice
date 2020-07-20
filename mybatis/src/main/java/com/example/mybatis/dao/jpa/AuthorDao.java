package com.example.mybatis.dao.jpa;

import com.example.mybatis.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 马成军
 **/
public interface AuthorDao extends JpaRepository<Author, String>, JpaSpecificationExecutor<Author> {

}
