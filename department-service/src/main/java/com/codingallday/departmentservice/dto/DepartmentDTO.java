package com.codingallday.departmentservice.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DepartmentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be empty or null")
    private String name;

    @NotEmpty(message = "Description should not be empty or null")
    private String description;

    @NotEmpty(message = "Code should not be empty or null")
    private String code;
}
