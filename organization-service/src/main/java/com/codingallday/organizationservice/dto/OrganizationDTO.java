package com.codingallday.organizationservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class OrganizationDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String createdDate;
}
