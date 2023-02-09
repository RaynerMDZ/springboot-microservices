package com.codingallday.employeeservice.service;

import com.codingallday.employeeservice.dto.request.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface APIClient {

    @GetMapping("/api/v1/departments/code/{code}")
    DepartmentDTO getDepartmentByCode(@PathVariable("code") String code);
}
