package com.example.demo.dao.jpa;

import com.example.demo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 马成军
 **/
public interface AuthorDao extends JpaRepository<Author, String>, JpaSpecificationExecutor<Author> {

}
