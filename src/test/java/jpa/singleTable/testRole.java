package jpa.singleTable;

import com.spring.project.portfolio.entities.InvestorRole;
import jpa.JPATemplate;
import org.junit.Test;

public class testRole extends JPATemplate {
    
    @Test
    public void crate(){
        
        InvestorRole roleOfStaff = new InvestorRole("Staff");
        InvestorRole roleOfAdmin = new InvestorRole("Admin");
        InvestorRole roleOfMember = new InvestorRole("Member");
        InvestorRole roleOfUser = new InvestorRole("User");
        
        entityManager.persist(roleOfStaff);
        entityManager.persist(roleOfAdmin);
        entityManager.persist(roleOfMember);
        entityManager.persist(roleOfUser);
    }
    
}
