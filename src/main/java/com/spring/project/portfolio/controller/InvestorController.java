package com.spring.project.portfolio.controller;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Watch;
import com.spring.project.portfolio.service.EmailService;
import com.spring.project.portfolio.service.PortfolioService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    // 查詢單筆 investor, 根據 id
    @GetMapping(value = {"/{id}"})
    public Investor queryForInvestor(@PathVariable("id") Optional<Integer> id){
        return portfolioService.getInvestorRepository().findOne(id.get());
    }
 
    
    // 新增 investor - 註冊帳號 
    @PostMapping(value = {"/", "/add"})
    public Investor addInvestor(@RequestBody Map<String, String> jsonMap) {
        Investor investor = new Investor();
        investor.setUsername(jsonMap.get("username"));
        investor.setPassword(jsonMap.get("password"));
        investor.setEmail(jsonMap.get("email"));
        investor.setBalance(Integer.parseInt(jsonMap.get("balance")));
        investor.setEnabled(Boolean.FALSE);
        
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
    
    // 修改單筆(根據 id)
    @PutMapping(value = {"/{id}"})
    @Transactional
    public Boolean updateInvestor(@PathVariable("id") Optional<Integer> id, 
                          @RequestBody Map<String, String> jsonMap) {
        // 判斷是否有此 id?
        if(!id.isPresent()) {
            return false;
        }
        // 判斷該筆資料是否存在 ?
        if(queryForInvestor(id) == null) {
            return false;
        }
        // 修改資料
        portfolioService.getInvestorRepository().update(
                id.get(), 
                jsonMap.get("username"), 
                jsonMap.get("password"), 
                jsonMap.get("email"), 
                Integer.parseInt(jsonMap.get("balance")));
        return true;
    }
    
    // 單筆刪除(根據 id)
    @DeleteMapping(value = {"/{id}"})
    @Transactional
    public Boolean deleteInvestor(@PathVariable("id") Optional<Integer> id) {
        
        // 判斷是否有此 id?
        if(!id.isPresent()) {
            return false;
        }
        // 判斷該筆資料是否存在 ?
        if(queryForInvestor(id) == null) {
            return false;
        }
        
        // 刪除資料
        portfolioService.getInvestorRepository().delete(id.get());
        return true;
    }
    
    @GetMapping("/duplicate/{username}")
    public Boolean isDuplicateUsername(@PathVariable("username") Optional<String> username) {
        if (username.isPresent()) {
            Investor investor = portfolioService.getInvestorRepository().getInvestor(username.get());
            return investor == null ? false : true;
        }
        return false;
    }
}
