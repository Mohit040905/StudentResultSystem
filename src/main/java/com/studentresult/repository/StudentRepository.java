package com.studentresult.repository;

import com.studentresult.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Student database operations.
 *
 * JpaRepository gives us all basic CRUD operations for free:
 * save(), findById(), findAll(), deleteById(), existsById() etc.
 * We only need to define custom queries here.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByRollNumber(String rollNumber);

    List<Student> findByDepartment(String department);

    List<Student> findBySemester(Integer semester);

    List<Student> findByDepartmentAndSemester(String department, Integer semester);

    boolean existsByEmail(String email);

    boolean existsByRollNumber(String rollNumber);
}
