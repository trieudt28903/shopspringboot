package com.dev.shopdienthoai.demo.controller;

import com.dev.shopdienthoai.demo.domain.Company;
import com.dev.shopdienthoai.demo.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
