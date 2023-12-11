package com.school.learning.repository;

import com.school.learning.model.entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {
    StudentInfo findById(int studentId);
    Long deleteById(int studentId);
}
