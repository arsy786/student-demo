package dev.arsalaan.studentdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecturerId;
    private String name;
    private Integer rating;

    public Lecturer() {
    }

    public Lecturer(Long lecturerId, String name, Integer rating) {
        this.lecturerId = lecturerId;
        this.name = name;
        this.rating = rating;
    }

    public Lecturer(String name, Integer rating) {
        this.name = name;
        this.rating = rating;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "lecturerId=" + lecturerId +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
