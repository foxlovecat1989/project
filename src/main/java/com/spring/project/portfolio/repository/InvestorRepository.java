package com.spring.project.portfolio.repository;

import com.spring.project.portfolio.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "investorRepository")
public interface InvestorRepository extends JpaRepository<Investor, Integer>{
   
    @Query(value = "Select i From Investor i Where i.isPassed='true' And i.username=?1")
    public Investor getInvestor(@Param("username") String username);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE Investor SET isPassed=?2 WHERE id=?1", nativeQuery = true)
    public void updateIsPassed(@Param("id") Integer id, @Param("isPassed") Boolean pass);
    
    @Transactional
    @Modifying
    @Query(value = 
            "UPDATE Investor "
                    + "SET username=?2, "
                    + "password=?3, "
                    + "email=?4, "
                    + "balance=?5 "
                    + "WHERE id=?1", 
            nativeQuery = true)
    
    public void update(
            @Param("id") Integer id, 
            @Param("username") String username, 
            @Param("password") String password, 
            @Param("email") String email, 
            @Param("balance") Integer balance);
}