package com.dev.shopdienthoai.demo.service;

import com.dev.shopdienthoai.demo.domain.Company;
import com.dev.shopdienthoai.demo.domain.dto.Meta;
import com.dev.shopdienthoai.demo.domain.dto.ResultPaginationDTO;
import com.dev.shopdienthoai.demo.repository.CompanyRespository;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public ResultPaginationDTO getAllCompany( Specification<Company> companySpecification, Pageable pageable) {
        Page<Company> companyPage = companyRespository.findAll(companySpecification,pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta=new Meta();
        meta.setTotal(companyPage.getTotalElements());
        meta.setPage(companyPage.getNumber()+1);
        meta.setPageSize(companyPage.getSize());
        meta.setPages(companyPage.getTotalPages());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(companyPage.getContent());
        return resultPaginationDTO;
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
