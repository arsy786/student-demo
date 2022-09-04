package dev.arsalaan.studentdemo.repository;

import dev.arsalaan.studentdemo.model.Course;
import dev.arsalaan.studentdemo.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByName(String name);

}
