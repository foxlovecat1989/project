package jpa.many2one;

import com.spring.project.portfolio.entities.Classify;
import com.spring.project.portfolio.entities.TStock;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import jpa.JPATemplate;
import org.junit.Assert;
import org.junit.Test;

public class testTStockClassify extends JPATemplate {

    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<TStock> tStocks = findAll();
        tStocks.stream().forEach(System.out::println);
        // U - update
        updateTStockNameById(Optional.of(2), "modified");
        TStock tStock = findOneById(Optional.of(2));
        Assert.assertEquals("modified", tStock.getName());
        // D - delete
        deleteTStockById(Optional.of(3));
        Optional<TStock> tStockNotExisted = findNotExistedById(Optional.of(3));
        Assert.assertFalse(tStockNotExisted.isPresent());

    }
    

    public void create() {

        TStock tStockA = new TStock("Stock_A");
        TStock tStockB = new TStock("Stock_B");
        TStock tStockC = new TStock("Stock_C");
        
        Classify classify_A = new Classify("groupA", Boolean.TRUE);
        Classify classify_B = new Classify("groupB", Boolean.TRUE);
        
        // relation
        tStockA.setClassify(classify_A);
        tStockB.setClassify(classify_B);
        tStockC.setClassify(classify_A);
        
        entityManager.persist(classify_A);
        entityManager.persist(classify_B);
        entityManager.persist(tStockA);
        entityManager.persist(tStockB);
        entityManager.persist(tStockC);
        entityManager.flush();
    }

    public List<TStock> findAll() {
        TypedQuery<TStock> query
                = entityManager.createQuery("select t from TStock t INNER JOIN FETCH t.classify", TStock.class);
        List<TStock> tStocks = query.getResultList();

        return tStocks;
    }

    public TStock findOneById(Optional<Integer> id) {
        return entityManager.find(TStock.class, id.get());
    }

    public void updateTStockNameById(Optional<Integer> id, String name) {
        TypedQuery<TStock> query = entityManager.createQuery("select t from TStock t where id =?1", TStock.class);
        query.setParameter(1, id.get());
        TStock tStock = query.getSingleResult();
        tStock.setName(name);
        entityManager.persist(tStock);
        entityManager.flush();
    }

    public void deleteTStockById(Optional<Integer> id) {
        TStock tStock = entityManager.find(TStock.class, id.get());
        entityManager.remove(tStock);
    }

    public Optional<TStock> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(TStock.class, id.get()));
    }
}
