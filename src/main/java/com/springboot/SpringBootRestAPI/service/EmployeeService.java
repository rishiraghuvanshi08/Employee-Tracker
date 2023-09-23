package com.springboot.SpringBootRestAPI.service;

import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import com.springboot.SpringBootRestAPI.repository.CompanyRepository;
import com.springboot.SpringBootRestAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public String deleteByIdAndCompany_Id(long cid, long eid){
        Employee employee = employeeRepository.findByIdAndCompanyId(eid, cid);

        Optional<Company> optionalCompany = companyRepository.findById(cid);
        if(employee != null && optionalCompany.isPresent()) {
            Company company = optionalCompany.get();



            employeeRepository.deleteByIdAndCompanyId(eid, cid);
            company.getEmployeeList().remove(employee);
            return "Deleted..";
        }
        return "Not found";
    }
}
