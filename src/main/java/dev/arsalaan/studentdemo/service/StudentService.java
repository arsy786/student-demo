package dev.arsalaan.studentdemo.service;

import dev.arsalaan.studentdemo.exception.ApiRequestException;
import dev.arsalaan.studentdemo.model.Course;
import dev.arsalaan.studentdemo.model.Student;
import dev.arsalaan.studentdemo.repository.CourseRepository;
import dev.arsalaan.studentdemo.repository.StudentRepository;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new ApiRequestException("student with id " + studentId + " does not exist"));
    }

    public void createStudent(Student student) {
        Optional<Student> studentOptionalEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptionalEmail.isPresent()) {
            throw new IllegalStateException("email of " + student.getEmail() + " taken");
        }

        studentRepository.save(student);
    }

    public void updateStudent(Long studentId, String name, String email) {

        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ApiRequestException("student with id " + studentId + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptionalEmail = studentRepository.findStudentByEmail(email);

            if (studentOptionalEmail.isPresent()) {
                throw new IllegalStateException("email of " + student.getEmail() + " taken");
            }

            student.setEmail(email);
        }

        studentRepository.save(student); //removes the need for @Transactional
    }

    public void deleteStudentById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new ApiRequestException("student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    // [GET] View All Students By Course Id
    public List<Student> viewAllStudentsByCourseId(Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));

        // courseRepository.findStudentByCourseCourseId(courseId);
        return course.getStudents();
    }


    // [POST] Add a specific Student to a specific Course
    @Transactional
    public void addStudentToCourse(Long courseId, Long studentId) {

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));

        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ApiRequestException("student with id " + studentId + " does not exist"));

        if (Objects.nonNull(student.getCourse())) {
            throw new ApiRequestException("student with id " + studentId + " already assigned to course with id " + student.getCourse().getCourseId());
        }

        student.setCourse(course); // tie Course to Student
        course.getStudents().add(student); // tie Student to Course
        course.setStudents(course.getStudents()); // tie Student to Course

    }

    // [DELETE] Remove a specific student assigned to a Course
    @Transactional
    public void removeStudentFromCourse(Long courseId, Long studentId) {

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));

        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ApiRequestException("student with id " + studentId + " does not exist"));


        if(!student.getCourse().getCourseId().equals(course.getCourseId())) {
            throw new ApiRequestException("student with id " + studentId + " is not assigned to the course with id " + courseId);
        }

        if (Objects.isNull(student.getCourse())) {
            throw new ApiRequestException("student with id " + studentId + " is not assigned to any course");
        }

        // course.getStudents().remove(student); - would be to remove student studying course from the student list in course entity (if bi-directional @OneToMany implemented)
        student.setCourse(null); // sets lecturer field in course to null instead of removing the parents AND deleting child
        course.getStudents().remove(student);
        course.setStudents(course.getStudents());

    }

}
