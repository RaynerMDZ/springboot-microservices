package com.codingallday.employeeservice.service.implementation;

import com.codingallday.employeeservice.dto.request.DepartmentDTO;
import com.codingallday.employeeservice.dto.request.EmployeeDTO;
import com.codingallday.employeeservice.dto.mapper.EmployeeMapper;
import com.codingallday.employeeservice.dto.request.OrganizationDTO;
import com.codingallday.employeeservice.dto.response.EmployeeDepartmentDTO;
import com.codingallday.employeeservice.entity.Employee;
import com.codingallday.employeeservice.exception.EmailAlreadyExistsException;
import com.codingallday.employeeservice.exception.ResourceNotFoundException;
import com.codingallday.employeeservice.repository.EmployeeRepository;
import com.codingallday.employeeservice.service.APIClient;
import com.codingallday.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {


    private final String DEPARTMENT_SERVICE_URL = "http://localhost:8082/api/v1/departments";
    private final String ORGANIZATION_SERVICE_URL = "http://localhost:8083/api/v1/organizations";

    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;

    private final WebClient webClient;
    private final APIClient apiClient;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               RestTemplate restTemplate,
                               WebClient webClient,
                               APIClient apiClient) {
        this.employeeRepository = employeeRepository;
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.apiClient = apiClient;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return this.employeeRepository.findAll()
                .stream()
                .map(employee -> EmployeeMapper.MAPPER.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultEmployeeById" )
//    @Retry(
//            name = "${spring.application.name}",
//            fallbackMethod = "getDefaultEmployeeById")
    @Override
    public EmployeeDepartmentDTO getEmployeeById(Long id) {

        log.info("EmployeeService.getEmployeeById() called with parameter: {}", id);

        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        // RestTemplate approach
//        ResponseEntity<DepartmentDTO> responseEntity =
//                this.restTemplate.getForEntity("http://localhost:8082/api/v1/departments/code/" +
//                        employee.getDepartmentCode(),
//                        DepartmentDTO.class);
//
//        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
//            throw new ResourceNotFoundException("Department", "code", employee.getDepartmentCode());
//        }
//
//        DepartmentDTO departmentDTO = responseEntity.getBody();

        // WebClient approach
        DepartmentDTO departmentDTO = this.webClient.get()
                .uri(this.DEPARTMENT_SERVICE_URL + "/code/" + employee.getDepartmentCode())
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Department", "code", employee.getDepartmentCode())))
                .bodyToMono(DepartmentDTO.class)
                .block();

        // WebClient approach
        OrganizationDTO organizationDTO = this.webClient.get()
                .uri(this.ORGANIZATION_SERVICE_URL + "/code/" + employee.getOrganizationCode())
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Organization", "code", employee.getOrganizationCode())))
                .bodyToMono(OrganizationDTO.class)
                .block();

        // FeignClient approach
//        DepartmentDTO departmentDTO = this.apiClient.getDepartmentByCode(employee.getDepartmentCode());


        EmployeeDTO employeeDTO = EmployeeMapper.MAPPER.mapToEmployeeDTO(employee);
        EmployeeDepartmentDTO employeeDepartmentDTO = new EmployeeDepartmentDTO();
        employeeDepartmentDTO.setEmployee(employeeDTO);
        employeeDepartmentDTO.setDepartment(departmentDTO);
        employeeDepartmentDTO.setOrganization(organizationDTO);

        log.info("EmployeeService.getEmployeeById() returns: {}", employeeDepartmentDTO);

        return employeeDepartmentDTO;
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = this.employeeRepository.findByEmail(email);

        if (employee == null) {
            throw new ResourceNotFoundException("Employee", "email", email);
        }

        return EmployeeMapper.MAPPER.mapToEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        // See if the employee already exists
        Employee foundEmployee = this.employeeRepository.findByEmail(employeeDTO.getEmail());

        if (foundEmployee != null) {
            throw new EmailAlreadyExistsException("An employee with this email address already exists");
        }

        // convert employeeDTO to employee
        Employee employee = EmployeeMapper.MAPPER.mapToEmployee(employeeDTO);

        // save employee
        Employee savedEmployee = this.employeeRepository.save(employee);

        // convert employee to employeeDTO and return it
        return EmployeeMapper.MAPPER.mapToEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee foundEmployee = this.employeeRepository.findById(id).orElse(null);

        if (foundEmployee == null) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }

        Employee employee = EmployeeMapper.MAPPER.mapToEmployee(employeeDTO);

        Employee savedEmployee = this.employeeRepository.save(employee);

        return EmployeeMapper.MAPPER.mapToEmployeeDTO(savedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }

        this.employeeRepository.delete(employee);
    }

    public EmployeeDepartmentDTO getDefaultEmployeeById(Long id, Throwable throwable) {

        log.error("EmployeeService.getDefaultEmployeeById() called with parameter: {}", id);

        Employee employee = this.employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }

        EmployeeDTO employeeDTO = EmployeeMapper.MAPPER.mapToEmployeeDTO(employee);

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setDescription("Default Department");
        departmentDTO.setName("Default Department");
        departmentDTO.setCode("DEFAULT");


        EmployeeDepartmentDTO employeeDepartmentDTO = new EmployeeDepartmentDTO();
        employeeDepartmentDTO.setEmployee(employeeDTO);
        employeeDepartmentDTO.setDepartment(departmentDTO);

        return employeeDepartmentDTO;
    }
}
