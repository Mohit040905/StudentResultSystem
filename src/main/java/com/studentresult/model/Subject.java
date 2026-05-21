package com.studentresult.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entity representing a Subject (e.g., Data Structures, DBMS).
 */
@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject name is required")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "Subject code is required")
    @Column(name = "subject_code", unique = true, nullable = false)
    private String subjectCode;

    @NotNull(message = "Max marks is required")
    @Min(value = 1, message = "Max marks must be at least 1")
    @Column(name = "max_marks", nullable = false)
    private Integer maxMarks;

    @NotNull(message = "Semester is required")
    @Min(value = 1) @Max(value = 8)
    private Integer semester;

    private String department;
}
