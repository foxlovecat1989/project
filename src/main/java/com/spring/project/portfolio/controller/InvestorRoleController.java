package com.spring.project.portfolio.controller;


import com.spring.project.portfolio.entities.InvestorRole;
import com.spring.project.portfolio.service.PortfolioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio/investorRole")
public class InvestorRoleController {
    
     @Autowired
    private PortfolioService service;

    @GetMapping(value = {"/", "/query"})
    public Iterable<InvestorRole> query() {
        return service.getInvestorRoleRepository().findAll();
    }

}
