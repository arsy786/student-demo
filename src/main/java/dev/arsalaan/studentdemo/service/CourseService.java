package dev.arsalaan.studentdemo.service;

import dev.arsalaan.studentdemo.exception.ApiRequestException;
import dev.arsalaan.studentdemo.model.Course;
import dev.arsalaan.studentdemo.model.Lecturer;
import dev.arsalaan.studentdemo.repository.CourseRepository;
import dev.arsalaan.studentdemo.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* Child Service contains functionality/business logic associated with Parent interaction */

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    // @Autowired - optional if class has only one constructor
    // Constructor-based Dependency Injection
    public CourseService(CourseRepository courseRepository, LecturerRepository lecturerRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    // [GET] View All Courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // [GET] View a specific Course by its ID
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));
    }

    // [POST] Create a Course
    public void createCourse(Course course) {
        Optional<Course> courseOptional = courseRepository.findCourseByName(course.getName());

        if (courseOptional.isPresent()) {
            throw new IllegalStateException("course name " + course.getName() + " taken");
        }

        courseRepository.save(course);
    }

    // [PUT] Update a specific Course by its ID
    @Transactional
    public void updateCourseById(Long courseId, String name, Integer duration) {

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(course.getName(), name)) {
            course.setName(name);
        }

        if (duration != null && duration >= 0 && duration <= 5 && !Objects.equals(course.getDuration(), duration)) {
            course.setDuration(duration);
        }

        // courseRepository.save(course); - would remove the need for @Transactional
    }

    // [DELETE] Remove a specific Course
    public void deleteCourseById(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);

        if (!exists) {
            throw new ApiRequestException("course with id " + courseId + " does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    // [GET] View All Courses By Lecturer Id
    public List<Course> viewAllCoursesByLecturerId(Long lecturerId) {

        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(
                () -> new ApiRequestException("Lecturer with id " + lecturerId + " does not exist"));


        // courseRepository.findCourseByLecturerLecturerId(lecturerId);
        return lecturer.getCourses();
    }

    // [POST] Add a Course to a specific Lecturer
    @Transactional
    public void addCourseToLecturer(Long lecturerId, Long courseId) {

        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(
                () -> new ApiRequestException("Lecturer with id " + lecturerId + " does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));

        if (Objects.nonNull(course.getLecturer())) {
            throw new ApiRequestException("course with id " + courseId + " already assigned to lecturer with id " + course.getLecturer().getLecturerId());
        }

        course.setLecturer(lecturer);
    }

    // [DELETE] Remove a specific Course assigned to a Lecturer
    @Transactional
    public void removeCourseFromLecturer(Long lecturerId, Long courseId) {

        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(
                () -> new ApiRequestException("Lecturer with id " + lecturerId + " not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiRequestException("course with id " + courseId + " does not exist"));

        if(!course.getLecturer().getLecturerId().equals(lecturer.getLecturerId())) {
            throw new ApiRequestException("Course with id " + courseId + " is not assigned to the Lecturer with id " + lecturerId);
        }

        if (Objects.isNull(course.getLecturer())) {
            throw new ApiRequestException("Course with id " + courseId + " is not assigned to any Lecturer");
        }

        //lecturer.getCourses().remove(course);
        course.setLecturer(null); // sets lecturer field in course to null instead of removing the parents AND deleting child
    }

}
