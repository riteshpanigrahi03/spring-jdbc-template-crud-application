package com.learn.SpringBootJdbcTemplate.controller;

import com.learn.SpringBootJdbcTemplate.dao.StudentDAO;
import com.learn.SpringBootJdbcTemplate.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    StudentDAO studentDAO;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentDAO.findAll();

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@RequestBody Student s){
        try {
            int result = studentDAO.save(s);
            return new ResponseEntity<>("Student added Successfully",HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/students/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") long id, @RequestBody Student s){
        Student existingStudent = studentDAO.findById(id);
        Student newDetails = new Student();
        if(existingStudent!=null){
            newDetails.setId(id);
            newDetails.setName(s.getName());
            newDetails.setCity(s.getCity());

            studentDAO.update(newDetails);
            return new ResponseEntity<>("Updated details successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Cannot find Student with id=" + id, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") long id) {
        try {
            int result = studentDAO.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Student with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Student was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Student.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/students")
    public ResponseEntity<String> deleteAllStudents() {
        try {
            int numRows = studentDAO.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Student(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Students.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
