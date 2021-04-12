package com.spring.project.portfolio.service;

import com.spring.project.portfolio.repository.ClassifyRepository;
import com.spring.project.portfolio.repository.InvestorRepository;
import com.spring.project.portfolio.repository.PortfolioRepository;
import com.spring.project.portfolio.repository.TStockRepository;
import com.spring.project.portfolio.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.project.portfolio.repository.InvestorRoleRepository;

@Service
public class PortfolioService {
    
    @Autowired
    private InvestorRoleRepository investorRoleRepository;
    
    @Autowired
    private ClassifyRepository classifyRepository;
    
    @Autowired
    private TStockRepository tStockRepository;
    
    @Autowired
    private InvestorRepository investorRepository;
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private WatchRepository watchRepository;

    public ClassifyRepository getClassifyRepository() {
        return classifyRepository;
    }

    public TStockRepository gettStockRepository() {
        return tStockRepository;
    }

    public InvestorRepository getInvestorRepository() {
        return investorRepository;
    }

    public PortfolioRepository getPortfolioRepository() {
        return portfolioRepository;
    }

    public WatchRepository getWatchRepository() {
        return watchRepository;
    }

    public InvestorRoleRepository getInvestorRoleRepository() {
        return investorRoleRepository;
    }

    
    
    
}
