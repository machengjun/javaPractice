package com.example.mybatis.mapper;

import com.example.mybatis.dao.mapper.PersonMapper;
import com.example.mybatis.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonMapperTest {
    @Autowired
    PersonMapper personMapper;

    @Test
    public void insert() {
        Person person = new Person();
        person.setAge(11);
        person.setName("mcj");
        int res = personMapper.insert(person);
        Assertions.assertNotNull(res);
    }
}