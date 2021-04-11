package com.spring.project.portfolio.repository;


import com.spring.project.portfolio.entities.Profit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "profitRepository")
public interface ProfitRepository extends JpaRepository<Profit, Integer>{
    @Query(value = "SELECT p FROM Profit p WHERE p.invid = ?1")
    public List<Profit> findByInvId(@Param("invid") Integer invid);
}