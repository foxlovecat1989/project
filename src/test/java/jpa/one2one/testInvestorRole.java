package jpa.one2one;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.InvestorRole;
import com.spring.project.portfolio.entities.RoleType;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpa.JPATemplate;
import org.junit.Assert;
import org.junit.Test;

public class testInvestorRole extends JPATemplate {

    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<Investor> investors = findAll();
        investors.stream().forEach(System.out::println);
    }

    public void create() {
        
        InvestorRole roleOfUser = new InvestorRole(RoleType.USER);
        InvestorRole roleOfMember = new InvestorRole(RoleType.MEMBER);

        Investor investor1 = new Investor("Amber", "amber", "Amber@gmail.com", 300000, Boolean.TRUE, new Date());
        investor1.setInvestorRole(roleOfUser);
        Investor investor2 = new Investor("Betty", "betty", "Betty@gmail.com", 1000000, Boolean.TRUE, new Date());
        investor2.setInvestorRole(roleOfMember);
        Investor investor3 = new Investor("Cathy", "cathy", "Cathy@gmail.com", 100000, Boolean.TRUE, new Date());
        investor3.setInvestorRole(roleOfMember);
        Investor investor4 = new Investor("Delete", "delete", "Delete@gmail.com", 500000, Boolean.TRUE, new Date());
        investor4.setInvestorRole(roleOfUser);
        
        entityManager.persist(roleOfUser);
        entityManager.persist(roleOfMember);
        entityManager.persist(investor1);
        entityManager.persist(investor2);
        entityManager.persist(investor3);
        entityManager.persist(investor4);

        entityManager.flush();

    }

    public List<Investor> findAll() {
        TypedQuery<Investor> query
                = entityManager.createQuery("select i from Investor i INNER JOIN FETCH i.investorRole", Investor.class);
        List<Investor> investors = query.getResultList();

        return investors;
    }

}
