package jpa.singleTable;

import com.spring.project.portfolio.entities.InvestorRole;
import com.spring.project.portfolio.entities.RoleType;
import jpa.JPATemplate;
import org.junit.Test;

public class testRole extends JPATemplate {
    
    @Test
    public void crate(){
        
        InvestorRole roleOfStaff = new InvestorRole(RoleType.STAFF);
        InvestorRole roleOfAdmin = new InvestorRole(RoleType.ADMIN);
        InvestorRole roleOfMember = new InvestorRole(RoleType.MEMBER);
        InvestorRole roleOfUser = new InvestorRole(RoleType.USER);
        
        entityManager.persist(roleOfStaff);
        entityManager.persist(roleOfAdmin);
        entityManager.persist(roleOfMember);
        entityManager.persist(roleOfUser);
    }
    
}
