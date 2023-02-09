package com.codingallday.departmentservice.dto.mapper;

import com.codingallday.departmentservice.dto.DepartmentDTO;
import com.codingallday.departmentservice.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper MAPPER = Mappers.getMapper(DepartmentMapper.class);

    Department mapToDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO mapToDepartmentDTO(Department department);
}
