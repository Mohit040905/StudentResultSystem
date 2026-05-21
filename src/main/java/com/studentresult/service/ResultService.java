package com.studentresult.service;

import com.studentresult.dto.ResultDTO;
import com.studentresult.exception.DuplicateResourceException;
import com.studentresult.exception.ResourceNotFoundException;
import com.studentresult.model.Result;
import com.studentresult.model.Student;
import com.studentresult.model.Subject;
import com.studentresult.repository.ResultRepository;
import com.studentresult.repository.StudentRepository;
import com.studentresult.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository    resultRepository;
    private final StudentRepository   studentRepository;
    private final SubjectRepository   subjectRepository;

    // ── ADD RESULT ────────────────────────────────────────────────────

    public ResultDTO addResult(ResultDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Student not found with ID: " + dto.getStudentId()));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found with ID: " + dto.getSubjectId()));

        // Prevent duplicate result for same student + subject
        if (resultRepository.existsByStudentIdAndSubjectId(dto.getStudentId(), dto.getSubjectId())) {
            throw new DuplicateResourceException(
                "Result for student '" + student.getFullName() +
                "' in subject '" + subject.getName() + "' already exists. Use update instead.");
        }

        // Validate marks don't exceed max marks
        if (dto.getMarksObtained() > subject.getMaxMarks()) {
            throw new IllegalArgumentException(
                "Marks obtained (" + dto.getMarksObtained() +
                ") cannot exceed max marks (" + subject.getMaxMarks() + ").");
        }

        Result result = Result.builder()
                .student(student)
                .subject(subject)
                .marksObtained(dto.getMarksObtained())
                .build();

        // @PrePersist will auto-calculate grade and percentage
        Result saved = resultRepository.save(result);
        return mapToDTO(saved);
    }

    // ── GET ALL RESULTS FOR A STUDENT ─────────────────────────────────

    public List<ResultDTO> getResultsByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
        return resultRepository.findByStudentId(studentId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // ── GET RESULTS FOR STUDENT BY SEMESTER ───────────────────────────

    public List<ResultDTO> getResultsByStudentAndSemester(Long studentId, Integer semester) {
        return resultRepository.findByStudentIdAndSemester(studentId, semester)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // ── GET OVERALL AVERAGE PERCENTAGE FOR A STUDENT ──────────────────

    public Double getAveragePercentage(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
        Double avg = resultRepository.findAveragePercentageByStudentId(studentId);
        return avg != null ? Math.round(avg * 100.0) / 100.0 : 0.0;
    }

    // ── UPDATE RESULT ─────────────────────────────────────────────────

    public ResultDTO updateResult(Long resultId, Integer newMarks) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Result not found with ID: " + resultId));

        if (newMarks > result.getSubject().getMaxMarks()) {
            throw new IllegalArgumentException(
                "Marks cannot exceed max marks: " + result.getSubject().getMaxMarks());
        }

        result.setMarksObtained(newMarks);
        // @PreUpdate will recalculate grade and percentage
        return mapToDTO(resultRepository.save(result));
    }

    // ── DELETE RESULT ─────────────────────────────────────────────────

    public void deleteResult(Long resultId) {
        if (!resultRepository.existsById(resultId)) {
            throw new ResourceNotFoundException("Result not found with ID: " + resultId);
        }
        resultRepository.deleteById(resultId);
    }

    // ── MAPPER ────────────────────────────────────────────────────────

    public ResultDTO mapToDTO(Result result) {
        return ResultDTO.builder()
                .id(result.getId())
                .studentId(result.getStudent().getId())
                .subjectId(result.getSubject().getId())
                .studentName(result.getStudent().getFullName())
                .rollNumber(result.getStudent().getRollNumber())
                .subjectName(result.getSubject().getName())
                .subjectCode(result.getSubject().getSubjectCode())
                .maxMarks(result.getSubject().getMaxMarks())
                .marksObtained(result.getMarksObtained())
                .percentage(result.getPercentage())
                .grade(result.getGrade())
                .remarks(result.getRemarks())
                .examDate(result.getExamDate() != null ? result.getExamDate().toString() : null)
                .build();
    }
}
