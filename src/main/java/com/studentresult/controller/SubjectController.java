package com.studentresult.controller;

import com.studentresult.dto.ApiResponse;
import com.studentresult.model.Subject;
import com.studentresult.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    // POST /api/subjects
    @PostMapping
    public ResponseEntity<ApiResponse<Subject>> addSubject(@Valid @RequestBody Subject subject) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Subject added successfully",
                        subjectService.addSubject(subject)));
    }

    // GET /api/subjects
    @GetMapping
    public ResponseEntity<ApiResponse<List<Subject>>> getAllSubjects() {
        return ResponseEntity.ok(
                ApiResponse.success("Subjects retrieved", subjectService.getAllSubjects()));
    }

    // GET /api/subjects/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Subject>> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Subject found", subjectService.getSubjectById(id)));
    }

    // GET /api/subjects/semester/{semester}
    @GetMapping("/semester/{semester}")
    public ResponseEntity<ApiResponse<List<Subject>>> getSubjectsBySemester(
            @PathVariable Integer semester) {
        return ResponseEntity.ok(
                ApiResponse.success("Subjects retrieved",
                        subjectService.getSubjectsBySemester(semester)));
    }

    // PUT /api/subjects/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Subject>> updateSubject(
            @PathVariable Long id, @Valid @RequestBody Subject subject) {
        return ResponseEntity.ok(
                ApiResponse.success("Subject updated", subjectService.updateSubject(id, subject)));
    }

    // DELETE /api/subjects/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(ApiResponse.success("Subject deleted", null));
    }
}
