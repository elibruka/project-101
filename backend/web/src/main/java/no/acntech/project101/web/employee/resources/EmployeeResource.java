package no.acntech.project101.web.employee.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import no.acntech.project101.employee.Employee;
import no.acntech.project101.employee.service.EmployeeService;
import no.acntech.project101.web.employee.resources.converter.EmployeeConverter;
import no.acntech.project101.web.employee.resources.converter.EmployeeDtoConverter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("employees")

//TODO This is a REST controler and should receive request on path employees
public class EmployeeResource {


    //TODO The constructor needs to accept the required dependencies and assign them to class variables
    private final EmployeeService employeeService;
    private final EmployeeDtoConverter employeeDtoConverter;
    private final EmployeeConverter employeeConverter;


    public EmployeeResource(EmployeeService employeeService, EmployeeDtoConverter employeeDtoConverter,EmployeeConverter employeeConverter){
        this.employeeService = employeeService;
        this.employeeDtoConverter = employeeDtoConverter;
        this.employeeConverter = employeeConverter;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
//        EmployeeDto employeeDto1 = new EmployeeDto(1L,"E","K",LocalDate.of(2010, 01, 01),971547235L);
//        EmployeeDto employeeDto2 = new EmployeeDto(2L,"M","V",LocalDate.of(2014, 02, 10),971547235L);
//        final List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
//        employeeDtoList.add(employeeDto1);
//        employeeDtoList.add(employeeDto2);
        final List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
        final List<Employee>  employeeList= employeeService.findAll();
        for (Employee e : employeeList){
            employeeDtoList.add(employeeDtoConverter.convert(e));
        }
        return ResponseEntity.ok(employeeDtoList);
    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable final Long id) {
        // TODO create a GET endpoint that fetches a spesific employee based on its ID
        // EmployeeDto employeeDto = new EmployeeDto(id,"E","K",LocalDate.of(2010, 01, 01),971547235L);
        final Optional<Employee> employee = employeeService.findById((id));
        if (employee.isPresent()){
            EmployeeDto employeeDto = employeeDtoConverter.convert(employee.get());
            return ResponseEntity.ok(employeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /*@GetMapping("list")
    public ResponseEntity<List<EmployeeDto>> findByIds(@RequestBody final List<Long> idList){
        List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
        for (Long tempId : idList) {
            EmployeeDto e = new EmployeeDto(tempId,"E", "K", LocalDate.of(2010, 01, 01),971547235L);
            employeeDtoList.add(e);

        }
        return ResponseEntity.ok(employeeDtoList);
    }*/

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody final EmployeeDto employeeDto) {
        //TODO Create a POST endpoint that accepts an employeeDTO and saves it in the database
        Employee employee = employeeConverter.convert(employeeDto);
        Employee savedEmployee = employeeService.save(employee);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmployee.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable final Long id) {
        final Optional<Employee> employee = employeeService.findById(id);
        // TODO Create a DELETE endpoint that deletes a specific employee
        if (employee.isPresent()) {
            employeeService.delete(id);
            return ResponseEntity.accepted().build();
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity updateEmployee(@PathVariable final Long id, @RequestBody final EmployeeDto employeeDto) {
        //TODO Create a PATCH endpoint that updates an employee with new values
        final Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setDateOfBirth(employeeDto.getDateOfBirth());
            Employee saved = employeeService.save(existingEmployee);
            EmployeeDto savedEmployeeDto = employeeDtoConverter.convert(saved);
            return ResponseEntity.ok(savedEmployeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
