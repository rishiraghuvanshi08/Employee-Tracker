package com.springboot.SpringBootRestAPI.repository;

import com.springboot.SpringBootRestAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
        List<Employee> findByCompanyId(long companyId);
}
