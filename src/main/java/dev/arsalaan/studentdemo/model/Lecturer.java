package dev.arsalaan.studentdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer rating;

    @JsonIgnore
    @OneToMany(mappedBy = "lecturer")
    private Set<Course> courses;

    public Lecturer() {
    }

    public Lecturer(Long id, String name, Integer rating, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.courses = courses;
    }

    public Lecturer(String name, Integer rating, Set<Course> courses) {
        this.name = name;
        this.rating = rating;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", courses=" + courses +
                '}';
    }
}
