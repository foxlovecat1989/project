package jpa.singleTable;

import com.spring.project.portfolio.entities.Role;
import com.spring.project.portfolio.entities.RoleType;
import jpa.JPATemplate;
import org.junit.Test;

public class testRole extends JPATemplate {
    
    @Test
    public void crate(){
        
        Role roleOfStaff = new Role(RoleType.STAFF);
        Role roleOfAdmin = new Role(RoleType.ADMIN);
        Role roleOfMember = new Role(RoleType.MEMBER);
        Role roleOfUser = new Role(RoleType.USER);
        
        entityManager.persist(roleOfStaff);
        entityManager.persist(roleOfAdmin);
        entityManager.persist(roleOfMember);
        entityManager.persist(roleOfUser);
    }
    
}
