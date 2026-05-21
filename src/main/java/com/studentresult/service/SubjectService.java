package com.studentresult.service;

import com.studentresult.exception.DuplicateResourceException;
import com.studentresult.exception.ResourceNotFoundException;
import com.studentresult.model.Subject;
import com.studentresult.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject addSubject(Subject subject) {
        if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
            throw new DuplicateResourceException(
                "Subject with code '" + subject.getSubjectCode() + "' already exists.");
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found with ID: " + id));
    }

    public List<Subject> getSubjectsBySemester(Integer semester) {
        return subjectRepository.findBySemester(semester);
    }

    public Subject updateSubject(Long id, Subject updated) {
        Subject existing = getSubjectById(id);
        existing.setName(updated.getName());
        existing.setMaxMarks(updated.getMaxMarks());
        existing.setSemester(updated.getSemester());
        existing.setDepartment(updated.getDepartment());
        return subjectRepository.save(existing);
    }

    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with ID: " + id);
        }
        subjectRepository.deleteById(id);
    }
}
