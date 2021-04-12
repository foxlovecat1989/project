package com.spring.project.portfolio.controller;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.service.PortfolioService;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/portfolio/verify")
public class VerifyController {
    @Autowired
    PortfolioService portfolioService;
    @Transactional
    @RequestMapping(value = "/{id}/{code}")
    public String verify(
            @PathVariable("id") Optional<Integer> id,
            @PathVariable("code") Optional<String> code,
            HttpSession httpSession){
        String message = "FAIL ~";
        // 核對 id & code 是否正確?
        // 取出 符合 id 的 investor
        Investor investor = portfolioService.getInvestorRepository().findOne(id.get());
        // 判斷 investor 是否為 Null & code是否正確?
        if(investor != null && investor.getCode().equals(code.get())){
            // 正確則, update 資料庫的 isPassed = true
            portfolioService.getInvestorRepository().updateEnabled(id.get(), Boolean.TRUE);
            message = "SUCCESS";
        }
        // 設定 message attribute
        httpSession.setAttribute("message", message);
        
        // 導至 verify.jsp
        return "redirect:/portfolio/verify.jsp";
    }
}