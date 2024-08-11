package com.dev.shopdienthoai.demo.service;

import com.dev.shopdienthoai.demo.domain.Company;
import com.dev.shopdienthoai.demo.repository.CompanyRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRespository companyRespository;

    public CompanyService(CompanyRespository companyRespository) {
        this.companyRespository = companyRespository;
    }
    public Company create(Company company) {
        return companyRespository.save(company);
    }
    public List<Company> getAllCompany() {
        return companyRespository.findAll();
    }
    public Company getCompany(Long id) {
        return companyRespository.findById(id).get();
    }
    public Company update(Company company) {
        Optional<Company> optionalCompany = companyRespository.findById(company.getId());
        if(optionalCompany.isPresent()){
            Company companyToUpdate = optionalCompany.get();
            companyToUpdate.setName(company.getName());
            companyToUpdate.setAddress(company.getAddress());
            companyToUpdate.setDescription(company.getDescription());
            companyToUpdate.setLogo(company.getLogo());
            return this.companyRespository.save(companyToUpdate);
        }
        return null;
    }
    public void delete(Long id) {
        companyRespository.deleteById(id);
    }
}
