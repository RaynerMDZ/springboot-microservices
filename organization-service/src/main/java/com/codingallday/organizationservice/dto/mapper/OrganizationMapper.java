package com.codingallday.organizationservice.dto.mapper;

import com.codingallday.organizationservice.dto.OrganizationDTO;
import com.codingallday.organizationservice.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationMapper {
    OrganizationMapper MAPPER = Mappers.getMapper(OrganizationMapper.class);
    Organization mapToOrganization(OrganizationDTO organizationDTO);
    OrganizationDTO mapToOrganizationDTO(Organization organization);
}
