package dev.arsalaan.studentdemo.service;

import dev.arsalaan.studentdemo.model.Lecturer;
import dev.arsalaan.studentdemo.model.Student;
import dev.arsalaan.studentdemo.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public Lecturer getLecturerById(Long lecturerId) {
        return lecturerRepository.findById(lecturerId).get();
    }

    public Lecturer addLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    public int updateLecturer(Long lectureId, String name, Integer rating) {

        Optional<Lecturer> lecturerOptional = lecturerRepository.findById(lectureId);

        if (!lecturerOptional.isPresent()) {
            return 1;
        }

        Lecturer lecturer = lecturerOptional.get();

        if (name != null && name.length() > 0 && !Objects.equals(lecturer.getName(), name)) {
            lecturer.setName(name);
        }

        if (rating != null && rating >= 0 && rating <= 5 && !Objects.equals(lecturer.getRating(), rating)) {
            lecturer.setRating(rating);
        }
        lecturerRepository.save(lecturer); //removes the need for @Transactional
        return 2;

    }

    public void deleteLecturerById(Long lecturerId) {
        lecturerRepository.deleteById(lecturerId);
    }

}
