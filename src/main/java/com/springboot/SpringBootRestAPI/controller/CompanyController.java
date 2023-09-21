package com.springboot.SpringBootRestAPI.controller;


import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import com.springboot.SpringBootRestAPI.repository.CompanyRepository;
import com.springboot.SpringBootRestAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getEmp")
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    @GetMapping("/getComp")
    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

    @PostMapping("/compAdd")
    public Company createCompany(@RequestBody Company company){
        return companyRepository.save(company);
    }

    @PostMapping("/empAdd")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/deleteEmp/{id}")
    public String deleteById(@PathVariable long id){
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

}
