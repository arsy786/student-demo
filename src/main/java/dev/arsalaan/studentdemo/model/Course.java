package dev.arsalaan.studentdemo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "courseId")
// ^ serialization of entities with bidirectional relationships, prevents infinite recursion and allows both entities to be viewed
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String name;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "lecturerId")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    /* @JsonIgnore annotation on the getLecturer() method in the Course(child) entity.
    This is done to avoid an infinite recursion going on during serialization since
    Author refers to Book and Book refer to Author. */
    @JsonIgnore // will not see lecturer in normal GET course request
    public Lecturer getLecturer() {
        return lecturer;
    }

    @JsonIgnore
    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    /* will not see student list in normal GET course request
    @JsonIgnore
    public List<Student> getStudents() {
        return students;
    }
    @JsonIgnore
    public void setStudents(List<Student> students) {
        this.students = students;
    }
     */
}
