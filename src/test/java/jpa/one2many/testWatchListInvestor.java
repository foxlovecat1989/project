package jpa.one2many;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Watch;
import java.util.Date;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import jpa.JPATemplate;
import org.junit.Assert;
import org.junit.Test;

public class testWatchListInvestor extends JPATemplate {

    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<Watch> watchs = findAll();
        watchs.stream().forEach(System.out::println);
        // U - update
        updateNameById(Optional.of(2), "After-Modified");
        Watch watch = findOneById(Optional.of(2));
        Assert.assertEquals("After-Modified", watch.getName());
        // D - delete
        deleteById(Optional.of(2));
        Optional<Watch> staffNotExisted = findNotExistedById(Optional.of(2));
        Assert.assertFalse(staffNotExisted.isPresent());
    }

    public void create() {

        Watch wacListA = new Watch("InvestorA_PortfolioA");
        Watch wacListB = new Watch("InvestorA_PortfolioB");
        Watch wacListC = new Watch("InvestorA_PortfolioC");
        Watch wacListD = new Watch("InvestorB_PortfolioA");
        
        Investor investorA = new Investor("Amber", "1234", "Amber@gmail.com", 100000, new Date());
        Investor investorB = new Investor("Betty", "5678", "Betty@gmail.com", 90000, new Date());

        // relation
        wacListA.setInvestor(investorA);
        wacListB.setInvestor(investorA);
        wacListC.setInvestor(investorA);
        wacListD.setInvestor(investorB);

        entityManager.persist(wacListA);
        entityManager.persist(wacListB);
        entityManager.persist(wacListC);
        entityManager.persist(wacListD);
        entityManager.persist(investorA);
        entityManager.persist(investorB);
        entityManager.flush();
    }

    public List<Watch> findAll() {
        TypedQuery<Watch> query
                = entityManager.createQuery("select w from Watch w LEFT OUTER JOIN FETCH w.investor", Watch.class);
        List<Watch> watchs = query.getResultList();

        return watchs;
    }

    public Watch findOneById(Optional<Integer> id) {
        return entityManager.find(Watch.class, id.get());
    }

    public void updateNameById(Optional<Integer> id, String name) {
        Watch watch = entityManager.find(Watch.class, id.get());
        watch.setName(name);
        entityManager.persist(watch);
        entityManager.flush();
    }

    public void deleteById(Optional<Integer> id) {
        Watch watch = entityManager.find(Watch.class, id.get());
        entityManager.remove(watch);
    }

    public Optional<Watch> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(Watch.class, id.get()));
    }
}
