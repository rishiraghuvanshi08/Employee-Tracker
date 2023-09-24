package com.springboot.SpringBootRestAPI.service;

import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import com.springboot.SpringBootRestAPI.repository.CompanyRepository;
import com.springboot.SpringBootRestAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public String deleteByIdAndCompany_Id(long cid, long eid){
        Optional<Employee> optionalEmployee = employeeRepository.findByIdAndCompanyId(eid, cid);

        Optional<Company> optionalCompany = companyRepository.findById(cid);
        if(optionalEmployee.isPresent() && optionalCompany.isPresent()) {
            Company company = optionalCompany.get();

            company.getEmployeeList().remove(optionalEmployee.get());

            employeeRepository.deleteByIdAndCompanyId(eid, cid);
            return "Deleted..";
        }
        return "Not found";
    }

    public ResponseEntity<Employee> getEmployeeById(long eid){
        Optional<Employee> optionalEmployee = employeeRepository.findById(eid);

        if(optionalEmployee.isPresent()){
            return ResponseEntity.ok(optionalEmployee.get());
        }
        return ResponseEntity.notFound().build();
    }
}
