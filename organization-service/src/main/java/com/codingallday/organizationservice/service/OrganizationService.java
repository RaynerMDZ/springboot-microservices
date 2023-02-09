package com.codingallday.organizationservice.service;

import com.codingallday.organizationservice.dto.OrganizationDTO;

import java.util.List;

public interface OrganizationService {
    List<OrganizationDTO> getAllOrganizations();
    OrganizationDTO getOrganizationById(Long id);
    OrganizationDTO getOrganizationByCode(String code);
    OrganizationDTO createOrganization(OrganizationDTO organizationDTO);
    OrganizationDTO updateOrganization(Long id, OrganizationDTO organizationDTO);
    void deleteOrganization(Long id);
}
