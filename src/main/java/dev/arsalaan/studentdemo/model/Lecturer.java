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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "lecturerId")
/* ^allows serialization of entities with bidirectional relationships,
   this means associated OneToMany entities (children) are included in the JSON response for a GET parent request,
   this allows parent to view child, and it does so while preventing the infinite recursion problem */
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecturerId;
    private String name;
    private Integer rating;

    @OneToMany(mappedBy = "lecturer" /*fetch = FetchType.EAGER*/ /*cascade = CascadeType.ALL*/)
    /* @JsonIgnore - prevents infinite recursion by ignoring the “Lecturer” property “courses” from serialization,
       this means the parent can NOT view the child entities by default GET parent request*/
    private List<Course> courses = new ArrayList<>(); // will see course list in normal GET lecturer request

}
