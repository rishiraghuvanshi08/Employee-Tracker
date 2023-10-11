package com.springboot.SpringBootRestAPI.controller;

import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import com.springboot.SpringBootRestAPI.entity.User;
import com.springboot.SpringBootRestAPI.repository.CompanyRepository;
import com.springboot.SpringBootRestAPI.repository.EmployeeRepository;
import com.springboot.SpringBootRestAPI.repository.UserRepository;
import com.springboot.SpringBootRestAPI.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/public")
    public String getPublic(){
        return "This is Public URL.";
    }

    /**
     * For Getting List of Employees
     *
     * @return List<Employee>
     */
    @GetMapping("/getEmp")
    @PreAuthorize("hasRole('USER')")
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    /**
     * For Getting List of Company
     * 
     * @return List<Company>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getComp")
    public List<Company> getCompany(){
        // System.out.println("Hey I'm Here.."+ 23/0);
        return companyRepository.findAll();
    }

    /**
     * For Adding a new Company
     * 
     * @return Company
     */
    @PostMapping("/addCompany")
    public Company createCompany(@RequestBody Company company){
        if(company.getEmployeeList() == null || company.getEmployeeList().isEmpty()){
            return companyRepository.save(company);
        }
        else{
            List<Employee> employeeList = company.getEmployeeList();
            employeeList.stream().forEach(e -> e.setCompany(company));  //for each employee setting current company

            company.setEmployeeList(employeeList);  //for this company setting the listOfEmployee
            return companyRepository.save(company);
        }
    }

    /**
     * For Adding Employee by Company ID
     * 
     * @return Employee
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addEmployee/{cid}")
    public ResponseEntity<Employee> createEmployee(@PathVariable long cid, @Valid @RequestBody Employee employee){
        Optional<Company> company = companyRepository.findById(cid);
        System.out.println(employee);
        if(company.isPresent()){
            employee.setCompany(company.get());
            try {
                employeeRepository.save(employee);
                return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
            }
            catch (Exception e){
                return new ResponseEntity<Employee>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
        }
    }

    /**
     * For Deleting Employee by Employee ID
     * 
     * @return String
     */
    @DeleteMapping("/deleteEmp/{id}")
    public String deleteEmployeeById(@PathVariable long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
            System.out.println("Nipta Diya..");
            return "Deleted..";
        }
        else{
            return "Employee Not Found";
        }
    }

    /**
     * For Deleting Company by ID (Cascade.ALL)
     * 
     * @return String
     */
    @DeleteMapping("/deleteComp/{id}")
    public Company deleteCompanyById(@PathVariable long id){
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isPresent()) {
            companyRepository.deleteById(id);
            return optionalCompany.get();
        }
        else{
            return null;
        }
    }

    /**
     * For Getting Employee by CompanyID
     *
     * @return List<Employee>
     */
    @GetMapping("/{id}/getEmployee")
    public List<Employee> getAllEmployees(@PathVariable long id){
        return employeeRepository.findByCompanyId(id);
    }

    /**
     * For Updating Company By ID
     * 
     * @return Company
     */
    @PutMapping("/updateCompany/{id}")
    public Company updateCompany(@PathVariable long id, @RequestBody Company updatedCompany){
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isPresent()){
            Company existingCompany = optionalCompany.get();

            updatedCompany.getEmployeeList().forEach(e -> e.setCompany(existingCompany));

            existingCompany.setName(updatedCompany.getName());
            existingCompany.setEmployeeList(updatedCompany.getEmployeeList());      // *****

            return companyRepository.save(existingCompany);
        }
        else{
            return null;
        }
    }

    /**
     * For Getting Employee of a Company
     * 
     * @return Employee
     */
    @GetMapping("/{cid}/getEmployee/{eid}")
    public Employee getEmployee(@PathVariable("cid") long cid, @PathVariable("eid") long eid){
        List<Employee> employeeList = employeeRepository.findByCompanyId(cid);

        Optional<Employee> optionalEmployee = employeeList.stream().filter(e -> e.getId() == eid).findFirst();

        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else{
            return null;
        }
    }

    /**
     * For Deleting Employee
     * 
     * @return String
     */
    @DeleteMapping("/{cid}/deleteEmployee/{eid}")
    public String deleteEmployee(@PathVariable("cid") long cid, @PathVariable("eid") long eid){
        List<Employee> employeeList = employeeRepository.findByCompanyId(cid);

        Optional<Employee> empToDelete = employeeList.stream().filter(e -> e.getId() == eid).findFirst();

        if(empToDelete.isPresent()){
            Optional<Company> company = companyRepository.findById(cid);

            if (company.isPresent()){
                company.get().getEmployeeList().remove(empToDelete.get());      // *****

                employeeRepository.deleteById(eid);
                return "Deleted Successfully";
            }
            return "failed";
        }
        else{
            return "Employee Not Found";
        }
    }

    /**
    * Getting Employee List By Name
    *
    * @return List<Employee>
    * */
    @GetMapping("/getByName/{name}")
    public List<Employee> getEmployeeByName(@PathVariable String name){
        return employeeRepository.findByName(name);
    }

    /**
     * For Getting Employee By Id and Company Id
     * 
     * @return Employee
     */
    @GetMapping("{cid}/getByIdAndCompanyId/{eid}")
    public ResponseEntity<Employee> getByIdAndCompanyId(@PathVariable("cid") long cid, @PathVariable("eid") long eid){
        Optional<Employee> optionalEmployee = employeeRepository.findByIdAndCompanyId(eid, cid);

        if(optionalEmployee.isPresent()){
            return ResponseEntity.ok(optionalEmployee.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * For Deleting Employee by Id And Company Id
     * 
     * @return String
     */
    @DeleteMapping("{cid}/deleteById/{eid}")
    public String deleteByIdAndCompanyId(@PathVariable("cid") long cid, @PathVariable("eid") long eid){
        return employeeService.deleteByIdAndCompany_Id(cid, eid);
    }

    /**
     * For Getting Employee by Id
     *
     * @return ResponseEntity<Employee>
     */
    @GetMapping("/getEmployeeById/{eid}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("eid") Long eid){
        return employeeService.getEmployeeById(eid);
    }

    @PutMapping("/updateEmployee")
    public Employee updateEmployee(@RequestBody Employee updatedEmployee){
        Optional<Employee> existingEmployee = employeeRepository.findById(updatedEmployee.getId());

        if(existingEmployee.isPresent()){
            Company company = existingEmployee.get().getCompany();
            updatedEmployee.setCompany(company);
            employeeRepository.save(updatedEmployee);
            return updatedEmployee;
        }
        return null;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
