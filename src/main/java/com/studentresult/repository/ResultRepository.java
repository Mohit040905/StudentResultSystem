package com.studentresult.repository;

import com.studentresult.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    // All results for a student
    List<Result> findByStudentId(Long studentId);

    // All results for a subject
    List<Result> findBySubjectId(Long subjectId);

    // Specific result for a student in a subject
    Optional<Result> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    // Check if result already exists
    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);

    // Custom JPQL query - average percentage for a student
    @Query("SELECT AVG(r.percentage) FROM Result r WHERE r.student.id = :studentId")
    Double findAveragePercentageByStudentId(@Param("studentId") Long studentId);

    // Results by grade
    List<Result> findByGrade(String grade);

    // All results for a student in a specific semester via subject
    @Query("SELECT r FROM Result r WHERE r.student.id = :studentId AND r.subject.semester = :semester")
    List<Result> findByStudentIdAndSemester(@Param("studentId") Long studentId,
                                             @Param("semester") Integer semester);
}
