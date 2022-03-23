package dev.arsalaan.studentdemo.controller;

import dev.arsalaan.studentdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.arsalaan.studentdemo.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PostMapping("/")
    public ResponseEntity createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{studentId}")
        public ResponseEntity updateStudent(@PathVariable("studentId") Long studentId, @RequestBody Student student) {
        studentService.updateStudent(studentId, student.getName(), student.getEmail());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity deleteStudentById(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudentById(studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
