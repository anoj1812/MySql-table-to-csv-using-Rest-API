package com.finsurge.task50.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.finsurge.task50.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{ }

