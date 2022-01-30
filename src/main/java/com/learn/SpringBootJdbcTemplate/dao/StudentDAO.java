package com.learn.SpringBootJdbcTemplate.dao;

import com.learn.SpringBootJdbcTemplate.model.Student;

import java.util.List;

public interface StudentDAO {
    int save(Student s);

    int update(Student s);

    Student findById(Long id);

    int deleteById(Long id);

    List<Student> findAll();

    int deleteAll();

}
