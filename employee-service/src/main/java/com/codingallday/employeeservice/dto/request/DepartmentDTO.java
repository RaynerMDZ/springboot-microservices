package com.codingallday.employeeservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
}
