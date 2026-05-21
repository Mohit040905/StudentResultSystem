package com.studentresult.controller;

import com.studentresult.dto.ApiResponse;
import com.studentresult.dto.ResultDTO;
import com.studentresult.service.ResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    // POST /api/results — Add a result
    @PostMapping
    public ResponseEntity<ApiResponse<ResultDTO>> addResult(
            @Valid @RequestBody ResultDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Result added successfully",
                        resultService.addResult(dto)));
    }

    // GET /api/results/student/{studentId} — All results for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<ResultDTO>>> getResultsByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(
                ApiResponse.success("Results retrieved",
                        resultService.getResultsByStudent(studentId)));
    }

    // GET /api/results/student/{studentId}/semester/{semester}
    @GetMapping("/student/{studentId}/semester/{semester}")
    public ResponseEntity<ApiResponse<List<ResultDTO>>> getResultsByStudentAndSemester(
            @PathVariable Long studentId, @PathVariable Integer semester) {
        return ResponseEntity.ok(
                ApiResponse.success("Results retrieved",
                        resultService.getResultsByStudentAndSemester(studentId, semester)));
    }

    // GET /api/results/student/{studentId}/average — Overall average percentage
    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<ApiResponse<Double>> getAveragePercentage(
            @PathVariable Long studentId) {
        Double avg = resultService.getAveragePercentage(studentId);
        return ResponseEntity.ok(
                ApiResponse.success("Average percentage: " + avg + "%", avg));
    }

    // PUT /api/results/{resultId}?marks=85 — Update marks
    @PutMapping("/{resultId}")
    public ResponseEntity<ApiResponse<ResultDTO>> updateResult(
            @PathVariable Long resultId,
            @RequestParam Integer marks) {
        return ResponseEntity.ok(
                ApiResponse.success("Result updated successfully",
                        resultService.updateResult(resultId, marks)));
    }

    // DELETE /api/results/{resultId}
    @DeleteMapping("/{resultId}")
    public ResponseEntity<ApiResponse<Void>> deleteResult(@PathVariable Long resultId) {
        resultService.deleteResult(resultId);
        return ResponseEntity.ok(ApiResponse.success("Result deleted", null));
    }
}
