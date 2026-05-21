package com.studentresult.controller;

import com.studentresult.dto.ApiResponse;
import com.studentresult.dto.StudentDTO;
import com.studentresult.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Student endpoints.
 *
 * @RestController = @Controller + @ResponseBody (auto-converts return to JSON)
 * @RequestMapping sets the base URL for all endpoints in this class
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // POST /api/students — Add a new student
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> addStudent(
            @Valid @RequestBody StudentDTO dto) {
        StudentDTO created = studentService.addStudent(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student added successfully", created));
    }

    // GET /api/students — Get all students
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(
                ApiResponse.success("Students retrieved successfully", students));
    }

    // GET /api/students/{id} — Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Student found", studentService.getStudentById(id)));
    }

    // GET /api/students/roll/{rollNumber} — Get student by roll number
    @GetMapping("/roll/{rollNumber}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentByRollNumber(
            @PathVariable String rollNumber) {
        return ResponseEntity.ok(
                ApiResponse.success("Student found",
                        studentService.getStudentByRollNumber(rollNumber)));
    }

    // GET /api/students/department/{dept} — Get students by department
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getByDepartment(
            @PathVariable String department) {
        return ResponseEntity.ok(
                ApiResponse.success("Students retrieved",
                        studentService.getStudentsByDepartment(department)));
    }

    // GET /api/students/department/{dept}/semester/{sem}
    @GetMapping("/department/{department}/semester/{semester}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getByDepartmentAndSemester(
            @PathVariable String department, @PathVariable Integer semester) {
        return ResponseEntity.ok(
                ApiResponse.success("Students retrieved",
                        studentService.getStudentsByDepartmentAndSemester(department, semester)));
    }

    // PUT /api/students/{id} — Update student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
            @PathVariable Long id, @Valid @RequestBody StudentDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.success("Student updated successfully",
                        studentService.updateStudent(id, dto)));
    }

    // DELETE /api/students/{id} — Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully", null));
    }
}
