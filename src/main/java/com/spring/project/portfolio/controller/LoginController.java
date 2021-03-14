package com.spring.project.portfolio.controller;


import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.service.PortfolioService;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portfolio")
public class LoginController {
    
    @Autowired
    PortfolioService portfolioService;
    
    @RequestMapping(value = "/login")
    public String login(
            @RequestParam Optional<String> username,
            @RequestParam Optional<String> password,
            HttpSession session){
        // 取得 investor
        Investor investor = 
                portfolioService.getInvestorRepository().getInvestor(username.get());
        // 核實 investor 帳號密碼是否正確?
        if(investor != null && investor.getCode().equals(investor.getPassword())){
            // 正確, 設定 attribute - investor & watch_id(觀察清單_id)
            session.setAttribute("investor", investor);
            session.setAttribute("watch_id", investor.getWatchs().iterator().next().getId());
        }
         session.invalidate();
        // 導至登入首頁
        return "redirect:/portfolio/index.jsp";
    }
    
    // 登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/portfolio/login.jsp";
    }
}
