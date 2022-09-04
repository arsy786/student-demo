package dev.arsalaan.studentdemo.service;

import dev.arsalaan.studentdemo.exception.ApiRequestException;
import dev.arsalaan.studentdemo.model.Lecturer;
import dev.arsalaan.studentdemo.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return lecturerRepository.findById(lecturerId).orElseThrow(
                () -> new ApiRequestException("lecturer with id " + lecturerId + " does not exist"));
    }

    public void createLecturer(Lecturer lecturer) {
        Optional<Lecturer> lecturerOptional = lecturerRepository.findLecturerByName(lecturer.getName());

        if (lecturerOptional.isPresent()) {
            throw new IllegalStateException("lecturer name " + lecturer.getName() + " taken");
        }

        lecturerRepository.save(lecturer);
    }

    @Transactional
    public void updateLecturerById(Long lectureId, String name, Integer rating) {

        Lecturer lecturer = lecturerRepository.findById(lectureId).orElseThrow(
                () -> new ApiRequestException("lecturer with id " + lectureId + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(lecturer.getName(), name)) {
            lecturer.setName(name);
        }

        if (rating != null && rating >= 0 && rating <= 5 && !Objects.equals(lecturer.getRating(), rating)) {
            lecturer.setRating(rating);
        }

        // lecturerRepository.save(lecturer); - would removes the need for @Transactional
    }

    public void deleteLecturerById(Long lecturerId) {
        boolean exists = lecturerRepository.existsById(lecturerId);

        if (!exists) {
            throw new ApiRequestException("lecturer with id " + lecturerId + " does not exist");
        }

        lecturerRepository.deleteById(lecturerId);
    }



}
