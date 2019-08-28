package no.acntech.project101.web.employee.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public EmployeeResource(){
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
        EmployeeDto employeeDto1 = new EmployeeDto(1L,"E","K",LocalDate.of(2010, 01, 01),971547235L);
        EmployeeDto employeeDto2 = new EmployeeDto(2L,"M","V",LocalDate.of(2014, 02, 10),971547235L);
        final List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
        employeeDtoList.add(employeeDto1);
        employeeDtoList.add(employeeDto2);



        //TODO create a GET endpoint find all employees in the database and return them
        return ResponseEntity.ok(employeeDtoList);
    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable final Long id) {
        // TODO create a GET endpoint that fetches a spesific employee based on its ID

        EmployeeDto employeeDto = new EmployeeDto(id,"E","K",LocalDate.of(2010, 01, 01),971547235L);
        return ResponseEntity.ok(employeeDto);
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
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(1L)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable final Long id) {
        // TODO Create a DELETE endpoint that deletes a specific employee
        if (id == 1) {
            return ResponseEntity.accepted().build();
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity updateEmployee(@PathVariable final Long id, @RequestBody final EmployeeDto employeeDto) {
        //TODO Create a PATCH endpoint that updates an employee with new values

        if (id == 1) {
            return ResponseEntity.ok(employeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
