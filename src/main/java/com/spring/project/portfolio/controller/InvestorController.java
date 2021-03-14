package com.spring.project.portfolio.controller;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Watch;
import com.spring.project.portfolio.service.EmailService;
import com.spring.project.portfolio.service.PortfolioService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/portfolio/investor")
public class InvestorController {
    
    @Autowired
    private  PortfolioService portfolioService;
    
    @Autowired
    private  EmailService emailService;
    
    // 查詢所有 investor
    @GetMapping(value = {"/", "/queryForAll"})
    public List<Investor> queryForAllInvestors(){
        return portfolioService.getInvestorRepository().findAll();
    }
    
    // 註冊帳號 新增 investor
    @PostMapping(value = {"/", "/add"})
    public Investor add(@RequestBody Map<String, String> jsonMap) {
        Investor investor = new Investor();
        investor.setUsername(jsonMap.get("username"));
        investor.setPassword(jsonMap.get("password"));
        investor.setEmail(jsonMap.get("email"));
        investor.setBalance(Integer.parseInt(jsonMap.get("balance")));
        investor.setIsPassed(Boolean.FALSE);
        
        // 設定認證碼 - 使用 the object of investor's hashCode 轉 16進位 作為 Code
        investor.setCode(Integer.toHexString(investor.hashCode()));
        
        // 存檔 Investor
        portfolioService.getInvestorRepository().save(investor);
        // 存檔 Watch
        Watch watch = new Watch(investor.getUsername() + "投資組合", investor);
        portfolioService.getWatchRepository().save(watch);
        
        // 發送認證信件
        emailService.send(investor);
        
        return investor;
    }
}
