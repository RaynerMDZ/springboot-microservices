package com.codingallday.employeeservice.dto.mapper;

import com.codingallday.employeeservice.dto.request.EmployeeDTO;
import com.codingallday.employeeservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);
    Employee mapToEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO mapToEmployeeDTO(Employee employee);
}
