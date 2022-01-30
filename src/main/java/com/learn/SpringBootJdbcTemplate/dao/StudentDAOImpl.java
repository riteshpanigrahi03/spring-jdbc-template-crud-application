package com.learn.SpringBootJdbcTemplate.dao;

import com.learn.SpringBootJdbcTemplate.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Student> rowMapper = (rs,rowNum)->{
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setCity(rs.getString("city"));
        return s;
    };

    @Override
    public int save(Student s) {
        String query = "Insert INTO students(id,name,city) values(?,?,?)";
        return jdbcTemplate.update(query,s.getId(),s.getName(),s.getCity());
    }

    @Override
    public int update(Student s) {
        String query = "Update students SET name=?,city=? WHERE id=? ";
        return jdbcTemplate.update(query,s.getName(),s.getCity(),s.getId());
    }

    @Override
    public Student findById(Long id) {
        String query = "SELECT * FROM students WHERE id=?";
        return jdbcTemplate.queryForObject(query,rowMapper,id);
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM students WHERE id=?";
        return jdbcTemplate.update(query,id);
    }

    @Override
    public List<Student> findAll() {
        String query = "SELECT * FROM students ";
        return jdbcTemplate.query(query,rowMapper);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from students");
    }
}
