package com.dev.shopdienthoai.demo.service;

import com.dev.shopdienthoai.demo.domain.Company;
import com.dev.shopdienthoai.demo.repository.CompanyRespository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private CompanyRespository companyRespository;

    public CompanyService(CompanyRespository companyRespository) {
        this.companyRespository = companyRespository;
    }
    public Company create(Company company) {
        return companyRespository.save(company);
    }
}
