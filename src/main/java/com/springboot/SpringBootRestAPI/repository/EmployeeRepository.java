package com.springboot.SpringBootRestAPI.repository;

import com.springboot.SpringBootRestAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyId(long companyId);
    List<Employee> findByName(String name);

    Employee findByIdAndCompanyId(long eid, long cid);


    void deleteByIdAndCompanyId(long eid, long cid);
}
