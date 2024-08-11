package com.dev.shopdienthoai.demo.controller;

import com.dev.shopdienthoai.demo.domain.Company;
import com.dev.shopdienthoai.demo.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {
    public final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> addCompany(@Valid @RequestBody Company company) {
        Company createCompany = companyService.create(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCompany);
    }
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companyList=this.companyService.getAllCompany();
        return ResponseEntity.status(HttpStatus.OK).body(companyList);
    }
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) {
        Company updatedCompany = companyService.update(company);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        this.companyService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company= this.companyService.getCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }
}
