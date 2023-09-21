package com.springboot.SpringBootRestAPI.controller;


import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import com.springboot.SpringBootRestAPI.repository.CompanyRepository;
import com.springboot.SpringBootRestAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/getEmp")
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    @GetMapping("/getComp")
    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

    @PostMapping("/addCompany")
    public Company createCompany(@RequestBody Company company){
        if(company.getEmployeeList().isEmpty()){
            return companyRepository.save(company);
        }
        else{
            List<Employee> employeeList = company.getEmployeeList();
            employeeList.stream().forEach(e -> e.setCompany(company));  //for each employee setting current company

            company.setEmployeeList(employeeList);  //for this company setting the listOfEmployee
            return companyRepository.save(company);
        }
    }

    @PostMapping("/addEmployee/{id}")
    public Employee createEmployee(@PathVariable long id, @RequestBody Employee employee){
        Optional<Company> company = companyRepository.findById(id);

        if(company.isPresent()){
            employee.setCompany(company.get());

            return employeeRepository.save(employee);
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/deleteEmp/{id}")
    public String deleteEmployeeById(@PathVariable long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
            return "Deleted..";
        }
        else{
            return "Employee Not Found";
        }
    }

    @DeleteMapping("/deleteComp/{id}")
    public String deleteCompanyById(@PathVariable long id){
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isPresent()) {
            companyRepository.deleteById(id);
            return "Deleted..";
        }
        else{
            return "Employee Not Found";
        }
    }

    @GetMapping("/{id}/getEmployee")
    public List<Employee> getAllEmployees(@PathVariable long id){
        return employeeRepository.findByCompanyId(id);
    }

//  --------------------------------------------------------------------------------------------------------------------
    @PutMapping("/updateCompany/{id}")
    public Company updateCompany(@PathVariable long id, @RequestBody Company updatedCompany){
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isPresent()){
            Company existingCompany = optionalCompany.get();

            updatedCompany.getEmployeeList().forEach(e -> e.setCompany(existingCompany));

            existingCompany.setName(updatedCompany.getName());
            existingCompany.setEmployeeList(updatedCompany.getEmployeeList());

            return companyRepository.save(existingCompany);
        }
        else{
            return null;
        }
    }


}
