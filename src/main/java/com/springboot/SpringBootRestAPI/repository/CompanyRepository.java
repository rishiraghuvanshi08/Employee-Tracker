package com.springboot.SpringBootRestAPI.repository;

import com.springboot.SpringBootRestAPI.entity.Company;
import com.springboot.SpringBootRestAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
