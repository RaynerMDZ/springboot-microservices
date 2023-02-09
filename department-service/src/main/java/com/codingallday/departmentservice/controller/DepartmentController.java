package com.codingallday.departmentservice.controller;

import com.codingallday.departmentservice.dto.DepartmentDTO;
import com.codingallday.departmentservice.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return new ResponseEntity<>(this.departmentService.getAllDepartments(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(this.departmentService.saveDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<DepartmentDTO> getDepartmentByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(this.departmentService.getDepartmentByCode(code), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("id") Long id,
                                                          @RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(this.departmentService.updateDepartment(id, departmentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable("id") Long id) {
        this.departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
