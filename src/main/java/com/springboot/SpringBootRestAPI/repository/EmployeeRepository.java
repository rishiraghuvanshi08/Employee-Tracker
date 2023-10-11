package com.springboot.SpringBootRestAPI.repository;

import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyId(long companyId);

    List<Employee> findByName(String name);

    Optional<Employee> findByIdAndCompanyId(long eid, long cid);

    void deleteByIdAndCompanyId(long eid, long cid);

//    Long findCompanyId(long eid);

    Company findCompanyById(Long employeeId);
}
