package com.springboot.SpringBootRestAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)@Column(nullable = false)
    private long id;

    private String name;

    private LocalDate dateOfJoining;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

//    ***
//    @Transient // Add this annotation to indicate it's not a persistent field
//    private Long companyId; // Transient field to hold company_id
//
//    public void setCompanyId(Long companyId) {
//
//        this.companyId = companyId;
//    }
// Getter and Setter for companyId

//    public Long getCompanyId() {
//        if (company != null) {
//            return company.getId();
//        }
//
//        return null; // Handle the case when company is null
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", company=" + company +
                '}';
    }
}
