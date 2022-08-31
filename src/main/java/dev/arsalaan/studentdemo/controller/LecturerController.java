package dev.arsalaan.studentdemo.controller;

import dev.arsalaan.studentdemo.model.Lecturer;
import dev.arsalaan.studentdemo.service.LecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Lecturer>> getAllLecturers() {
        List<Lecturer> lecturers = lecturerService.getAllLecturers();

        if (lecturers == null || lecturers.isEmpty()) {
            return new ResponseEntity<>(lecturers, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(lecturerService.getAllLecturers());
    }

    @GetMapping("/{lecturerId}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable("lecturerId") Long lecturerId) {
        Lecturer lecturer = lecturerService.getLecturerById(lecturerId);

        if (lecturer == null) {
            return new ResponseEntity("lecturer with id " + lecturerId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Lecturer>(lecturer, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity createLecturer(@Valid @RequestBody Lecturer lecturer) {
        lecturerService.addLecturer(lecturer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{lecturerId}")
    public ResponseEntity updateLecturer(@PathVariable("lecturerId") Long lecturerId, @RequestBody Lecturer lecturer) {
        int val = lecturerService.updateLecturer(lecturerId, lecturer.getName(), lecturer.getRating());

        if (val == 1) {
            return new ResponseEntity("lecturer with id " + lecturerId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{lecturerId}")
    public ResponseEntity deleteLecturerById(@PathVariable("lecturerId") Long lecturerId) {

        boolean exists = lecturerService.deleteLecturerById(lecturerId);

        if (exists) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity("lecturer with id " + lecturerId + " does not exist", HttpStatus.NOT_FOUND);
    }

}
