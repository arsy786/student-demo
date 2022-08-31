package dev.arsalaan.studentdemo.service;

import dev.arsalaan.studentdemo.exception.ApiRequestException;
import dev.arsalaan.studentdemo.model.Student;
import dev.arsalaan.studentdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            return studentOptional.get();
        }

        return null;
    }

    public int createStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            return 1;
        }

        studentRepository.save(student);
        return 2; //true = 2

    }

    //@Transactional not needed
    public int updateStudent(Long studentId, String name, String email) {

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (!studentOptional.isPresent()) {
            return 1;
        }
        Student student = studentOptional.get();

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptionalEmail = studentRepository.findStudentByEmail(email);

            if (studentOptionalEmail.isPresent()) {
                return 2;
            }
            student.setEmail(email);
        }
        studentRepository.save(student); //removes the need for @Transactional
        return 3;

    }

    public boolean deleteStudentById(Long studentId) {

        boolean exists = studentRepository.existsById(studentId);

        if (exists) {
            studentRepository.deleteById(studentId);
            return true;
        }

        return false;
    }

}
