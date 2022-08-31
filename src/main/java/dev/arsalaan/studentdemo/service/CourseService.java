package dev.arsalaan.studentdemo.service;

import dev.arsalaan.studentdemo.model.Course;
import dev.arsalaan.studentdemo.model.Lecturer;
import dev.arsalaan.studentdemo.repository.CourseRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).get();
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public int updateCourse(Long courseId, String name, Integer duration) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (!courseOptional.isPresent()) {
            return 1;
        }

        Course course = courseOptional.get();

        if (name != null && name.length() > 0 && !Objects.equals(course.getName(), name)) {
            course.setName(name);
        }

        if (duration != null && duration >= 0 && duration <= 5 && !Objects.equals(course.getDuration(), duration)) {
            course.setDuration(duration);
        }
        courseRepository.save(course); //removes the need for @Transactional
        return 2;

    }

    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }


}
