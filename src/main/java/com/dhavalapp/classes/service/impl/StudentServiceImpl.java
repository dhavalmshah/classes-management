package com.dhavalapp.classes.service.impl;

import com.dhavalapp.classes.domain.Student;
import com.dhavalapp.classes.repository.StudentRepository;
import com.dhavalapp.classes.service.StudentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> partialUpdate(Student student) {
        log.debug("Request to partially update Student : {}", student);

        return studentRepository
            .findById(student.getId())
            .map(existingStudent -> {
                if (student.getStudentName() != null) {
                    existingStudent.setStudentName(student.getStudentName());
                }
                if (student.getStandard() != null) {
                    existingStudent.setStandard(student.getStandard());
                }
                if (student.getStudentPhone() != null) {
                    existingStudent.setStudentPhone(student.getStudentPhone());
                }
                if (student.getParentName() != null) {
                    existingStudent.setParentName(student.getParentName());
                }
                if (student.getParentPhone() != null) {
                    existingStudent.setParentPhone(student.getParentPhone());
                }

                return existingStudent;
            })
            .map(studentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
