package com.studentresult.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO for creating/updating a Result.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Marks obtained is required")
    @Min(value = 0, message = "Marks cannot be negative")
    private Integer marksObtained;

    // Response fields (read-only — calculated by server)
    private String studentName;
    private String rollNumber;
    private String subjectName;
    private String subjectCode;
    private Integer maxMarks;
    private Double percentage;
    private String grade;
    private String remarks;
    private String examDate;
}
