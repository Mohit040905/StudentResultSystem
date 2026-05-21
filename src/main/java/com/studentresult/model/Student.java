package com.studentresult.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Student.
 * @Entity tells JPA/Hibernate to map this class to a database table.
 * Lombok annotations auto-generate getters, setters, constructors.
 */
@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Roll number is required")
    @Column(name = "roll_number", unique = true, nullable = false)
    private String rollNumber;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be between 1 and 8")
    @Max(value = 8, message = "Semester must be between 1 and 8")
    private Integer semester;

    @Column(name = "enrolled_on")
    private LocalDate enrolledOn;

    // One student can have many results
    // mappedBy = "student" refers to the 'student' field in Result entity
    // cascade = ALL means if student is deleted, their results are also deleted
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Result> results = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.enrolledOn = LocalDate.now();
    }

    // Convenience method - full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
