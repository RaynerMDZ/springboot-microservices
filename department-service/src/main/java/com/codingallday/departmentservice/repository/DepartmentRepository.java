package com.codingallday.departmentservice.repository;

import com.codingallday.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByCode(String code);
}
