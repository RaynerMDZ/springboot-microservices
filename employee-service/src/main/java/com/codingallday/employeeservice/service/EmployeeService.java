package com.codingallday.employeeservice.service;

import com.codingallday.employeeservice.dto.request.EmployeeDTO;
import com.codingallday.employeeservice.dto.response.EmployeeDepartmentDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDepartmentDTO getEmployeeById(Long id);
    EmployeeDTO getEmployeeByEmail(String email);
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
