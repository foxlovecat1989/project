package com.spring.project.portfolio.repository;

import com.spring.project.portfolio.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "portfolioRepository")
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>{

}