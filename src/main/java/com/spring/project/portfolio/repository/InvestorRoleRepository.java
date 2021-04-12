package com.spring.project.portfolio.repository;

import com.spring.project.portfolio.entities.InvestorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "investorRoleRepository")
public interface InvestorRoleRepository extends JpaRepository<InvestorRole, Integer>{
    
}