package com.codingallday.departmentservice.service;

import com.codingallday.departmentservice.dto.DepartmentDTO;
import com.codingallday.departmentservice.dto.mapper.DepartmentMapper;
import com.codingallday.departmentservice.entity.Department;
import com.codingallday.departmentservice.exception.ResourceNotFoundException;
import com.codingallday.departmentservice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return this.departmentRepository.findAll()
                .stream()
                .map(department -> DepartmentMapper.MAPPER.mapToDepartmentDTO(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = this.departmentRepository.findById(id).orElse(null);

        if (department == null) {
            throw new ResourceNotFoundException("Department", "id", id);
        }

        return DepartmentMapper.MAPPER.mapToDepartmentDTO(department);
    }

    @Override
    public DepartmentDTO getDepartmentByCode(String code) {
        Department department = this.departmentRepository.findByCode(code);

        if (department == null) {
            throw new ResourceNotFoundException("Department", "code", code);
        }

        return DepartmentMapper.MAPPER.mapToDepartmentDTO(department);
    }

    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {

        // convert departmentDTO to department
        Department department = DepartmentMapper.MAPPER.mapToDepartment(departmentDTO);

        // save department
        Department savedDepartment = this.departmentRepository.save(department);

        // convert and return savedDepartment to departmentDTO
        return DepartmentMapper.MAPPER.mapToDepartmentDTO(savedDepartment);

    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department foundDepartment = this.departmentRepository.findById(id).orElse(null);

        if (foundDepartment == null) {
            throw new ResourceNotFoundException("Department", "id", id);
        }

        departmentDTO.setId(foundDepartment.getId());
        return this.saveDepartment(departmentDTO);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = this.departmentRepository.findById(id).orElse(null);

        if (department == null) {
            throw new ResourceNotFoundException("Department", "id", id);
        }
        this.departmentRepository.delete(department);
    }
}
