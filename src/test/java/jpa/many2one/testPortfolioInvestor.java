package jpa.many2one;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Portfolio;
import com.spring.project.portfolio.entities.TStock;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import jpa.JPATemplate;
import org.junit.Assert;
import org.junit.Test;

public class testPortfolioInvestor extends JPATemplate{
    
    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<Portfolio> portfolios = findAll();
        portfolios.stream().forEach(System.out::println);
        // U - update
        updateAmountById(Optional.of(2), 100);
        Portfolio portfolio = findOneById(Optional.of(2));
        Assert.assertTrue(portfolio.getAmount() == 100);
        // D - delete
        deletePortfolioById(Optional.of(1));
        Optional<Portfolio> portfolioNotExisted = findNotExistedById(Optional.of(1));
        Assert.assertFalse(portfolioNotExisted.isPresent());
    }

    public void create() {
        Investor investorA = new Investor("USER_A", "1234", "USER_A@gmail.com", 100000, new Date());
        Investor investorB = new Investor("USER_B", "4321", "USER_B@gmail.com", 50000, new Date());
        
        TStock stockA = new TStock("stockA");
        TStock stockB = new TStock("stockB");
        TStock stockC = new TStock("stockC");

        Portfolio portfolioA = new Portfolio(1000.0, 10 , stockA);
        portfolioA.setInvestor(investorA);
        Portfolio portfolioB = new Portfolio(500.0, 10, stockB);
        portfolioB.setInvestor(investorA);
        Portfolio portfolioC = new Portfolio(300.0, 5, stockC);
        portfolioC.setInvestor(investorB);
       
        List<Portfolio> portfoliosOfInvestorA = new LinkedList<>();
        portfoliosOfInvestorA.add(portfolioA);
        portfoliosOfInvestorA.add(portfolioB);
        List<Portfolio> portfoliosOfInvestorB = new LinkedList<>();
        portfoliosOfInvestorB.add(portfolioC);
        
        entityManager.persist(stockA);
        entityManager.persist(stockB);
        entityManager.persist(stockC);
        
        entityManager.persist(investorA);
        entityManager.persist(investorB);
        entityManager.persist(portfolioA);
        entityManager.persist(portfolioB);
        entityManager.persist(portfolioC);
        entityManager.flush();
    }

    public List<Portfolio> findAll() {
        TypedQuery<Portfolio> query
                = entityManager.createQuery("select p from Portfolio p INNER JOIN FETCH p.investor INNER JOIN FETCH p.tStock", Portfolio.class);
        List<Portfolio> portfolios = query.getResultList();

        return portfolios;
    }

    public Portfolio findOneById(Optional<Integer> id) {
        return entityManager.find(Portfolio.class, id.get());
    }

    public void updateAmountById(Optional<Integer> id, Integer amount) {
        Portfolio portfolio = entityManager.find(Portfolio.class, id.get());
        portfolio.setAmount(amount);
        entityManager.persist(portfolio);
        entityManager.flush();
    }

    public void deletePortfolioById(Optional<Integer> id) {
        Portfolio portfolio = entityManager.find(Portfolio.class, id.get());
        entityManager.remove(portfolio);
    }

    public Optional<Portfolio> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(Portfolio.class, id.get()));
    }
}
