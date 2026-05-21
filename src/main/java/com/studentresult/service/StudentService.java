package com.studentresult.service;

import com.studentresult.dto.StudentDTO;
import com.studentresult.exception.DuplicateResourceException;
import com.studentresult.exception.ResourceNotFoundException;
import com.studentresult.model.Student;
import com.studentresult.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Student operations.
 * All business logic and validation lives here.
 * Controllers call Service; Service calls Repository.
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // ── ADD ──────────────────────────────────────────────────────────

    public StudentDTO addStudent(StudentDTO dto) {
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException(
                "Student with email '" + dto.getEmail() + "' already exists.");
        }
        if (studentRepository.existsByRollNumber(dto.getRollNumber())) {
            throw new DuplicateResourceException(
                "Student with roll number '" + dto.getRollNumber() + "' already exists.");
        }
        Student student = mapToEntity(dto);
        Student saved   = studentRepository.save(student);
        return mapToDTO(saved);
    }

    // ── GET ALL ───────────────────────────────────────────────────────

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── GET BY ID ─────────────────────────────────────────────────────

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Student not found with ID: " + id));
        return mapToDTO(student);
    }

    // ── GET BY ROLL NUMBER ────────────────────────────────────────────

    public StudentDTO getStudentByRollNumber(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Student not found with roll number: " + rollNumber));
        return mapToDTO(student);
    }

    // ── GET BY DEPARTMENT ─────────────────────────────────────────────

    public List<StudentDTO> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── GET BY DEPARTMENT AND SEMESTER ───────────────────────────────

    public List<StudentDTO> getStudentsByDepartmentAndSemester(String department, Integer semester) {
        return studentRepository.findByDepartmentAndSemester(department, semester)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── UPDATE ────────────────────────────────────────────────────────

    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Student not found with ID: " + id));

        // Check email conflict with another student
        studentRepository.findByEmail(dto.getEmail())
                .filter(s -> !s.getId().equals(id))
                .ifPresent(s -> { throw new DuplicateResourceException(
                    "Email '" + dto.getEmail() + "' is already in use."); });

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setSemester(dto.getSemester());

        return mapToDTO(studentRepository.save(existing));
    }

    // ── DELETE ────────────────────────────────────────────────────────

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    // ── MAPPERS ───────────────────────────────────────────────────────

    public Student mapToEntity(StudentDTO dto) {
        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .rollNumber(dto.getRollNumber())
                .department(dto.getDepartment())
                .semester(dto.getSemester())
                .build();
    }

    public StudentDTO mapToDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .rollNumber(student.getRollNumber())
                .department(student.getDepartment())
                .semester(student.getSemester())
                .build();
    }
}
