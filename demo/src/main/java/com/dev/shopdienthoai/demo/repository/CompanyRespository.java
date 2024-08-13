package com.dev.shopdienthoai.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  com.dev.shopdienthoai.demo.domain.Company;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRespository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
}
