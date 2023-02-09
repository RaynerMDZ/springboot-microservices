package com.codingallday.organizationservice.controller;

import com.codingallday.organizationservice.dto.OrganizationDTO;
import com.codingallday.organizationservice.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        return new ResponseEntity<>(this.organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Long id) {
        return new ResponseEntity<>(this.organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<OrganizationDTO> getOrganizationByCode(@PathVariable String code) {
        return new ResponseEntity<>(this.organizationService.getOrganizationByCode(code), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        return new ResponseEntity<>(this.organizationService.createOrganization(organizationDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable Long id, @RequestBody OrganizationDTO organizationDTO) {
        return new ResponseEntity<>(this.organizationService.updateOrganization(id, organizationDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OrganizationDTO> deleteOrganization(@PathVariable Long id) {
        this.organizationService.deleteOrganization(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
