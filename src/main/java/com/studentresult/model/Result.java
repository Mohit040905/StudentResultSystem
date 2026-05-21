package com.studentresult.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity representing a student's result in a subject.
 * Contains auto-calculated grade and percentage.
 */
@Entity
@Table(
    name = "results",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"student_id", "subject_id"},
        name = "unique_student_subject"
    )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many results belong to one student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Many results belong to one subject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotNull(message = "Marks obtained is required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Column(name = "marks_obtained", nullable = false)
    private Integer marksObtained;

    // Auto-calculated fields — set before saving
    private Double percentage;
    private String grade;

    @Column(name = "exam_date")
    private LocalDate examDate;

    private String remarks;

    /**
     * Called automatically before INSERT.
     * Calculates percentage and grade based on marks.
     */
    @PrePersist
    @PreUpdate
    public void calculateGradeAndPercentage() {
        if (subject != null && subject.getMaxMarks() > 0) {
            this.percentage = ((double) marksObtained / subject.getMaxMarks()) * 100;
            this.grade      = calculateGrade(this.percentage);
            this.remarks    = calculateRemarks(this.percentage);
        }
        if (this.examDate == null) {
            this.examDate = LocalDate.now();
        }
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "O";   // Outstanding
        if (percentage >= 80) return "A+";
        if (percentage >= 70) return "A";
        if (percentage >= 60) return "B+";
        if (percentage >= 50) return "B";
        if (percentage >= 40) return "C";
        return "F";                          // Fail
    }

    private String calculateRemarks(double percentage) {
        if (percentage >= 90) return "Outstanding";
        if (percentage >= 80) return "Excellent";
        if (percentage >= 70) return "Very Good";
        if (percentage >= 60) return "Good";
        if (percentage >= 50) return "Average";
        if (percentage >= 40) return "Pass";
        return "Fail";
    }
}
