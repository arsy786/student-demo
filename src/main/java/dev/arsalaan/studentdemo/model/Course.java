package dev.arsalaan.studentdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String name;
    private Integer duration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lecturer_id", referencedColumnName = "lecturerId")
    private Lecturer lecturer;

    public Course() {
    }

    public Course(Long courseId, String name, Integer duration, Lecturer lecturer) {
        this.courseId = courseId;
        this.name = name;
        this.duration = duration;
        this.lecturer = lecturer;
    }

    public Course(String name, Integer duration, Lecturer lecturer) {
        this.name = name;
        this.duration = duration;
        this.lecturer = lecturer;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", lecturer=" + lecturer +
                '}';
    }
}
