package com.codingallday.employeeservice.dto.response;

import com.codingallday.employeeservice.dto.request.DepartmentDTO;
import com.codingallday.employeeservice.dto.request.EmployeeDTO;
import com.codingallday.employeeservice.dto.request.OrganizationDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployeeDepartmentDTO {
    private EmployeeDTO employee;
    private DepartmentDTO department;
    private OrganizationDTO organization;
}
