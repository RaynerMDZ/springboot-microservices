package com.codingallday.organizationservice.service.implementation;

import com.codingallday.organizationservice.dto.OrganizationDTO;
import com.codingallday.organizationservice.dto.mapper.OrganizationMapper;
import com.codingallday.organizationservice.entity.Organization;
import com.codingallday.organizationservice.exception.ResourceNotFoundException;
import com.codingallday.organizationservice.repository.OrganizationRepository;
import com.codingallday.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        return this.organizationRepository.findAll()
                .stream()
                .map(organization -> OrganizationMapper.MAPPER.mapToOrganizationDTO(organization))
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO getOrganizationById(Long id) {
        Organization organization = this.organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", id));

        return OrganizationMapper.MAPPER.mapToOrganizationDTO(organization);
    }

    @Override
    public OrganizationDTO getOrganizationByCode(String code) {
        Organization organization = this.organizationRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "code", code));

        return OrganizationMapper.MAPPER.mapToOrganizationDTO(organization);
    }

    @Override
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = OrganizationMapper.MAPPER.mapToOrganization(organizationDTO);
        Organization savedOrganization = this.organizationRepository.save(organization);

        return OrganizationMapper.MAPPER.mapToOrganizationDTO(savedOrganization);
    }

    @Override
    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organizationDTO) {
        Organization foundOrganization = this.organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", id));

        foundOrganization.setName(organizationDTO.getName());
        foundOrganization.setCode(organizationDTO.getCode());
        foundOrganization.setDescription(organizationDTO.getDescription());

        return OrganizationMapper.MAPPER.mapToOrganizationDTO(foundOrganization);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization = this.organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", id));

        this.organizationRepository.delete(organization);
    }
}
