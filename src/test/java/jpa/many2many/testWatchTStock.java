package jpa.many2many;

import com.spring.project.portfolio.entities.Role;
import com.spring.project.portfolio.entities.Staff;
import com.spring.project.portfolio.entities.TStock;
import com.spring.project.portfolio.entities.Watch;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpa.JPATemplate;
import org.junit.Assert;
import org.junit.Test;

public class testWatchTStock extends JPATemplate {

    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<Watch> watchs = findAll();
        watchs.stream().forEach(System.out::println);       
        // D - revome TSTOCK
        
        // D - delete watchlist
        deleteWatchListById(Optional.of(1));
        Optional<Staff> watchlistNotExisted = findNotExistedById(Optional.of(1));
        Assert.assertFalse(watchlistNotExisted.isPresent());
    }

    public void create() {

        TStock tStockA = new TStock("Stock_A");
        TStock tStockB = new TStock("Stock_B");
        TStock tStockC = new TStock("Stock_C");

        Watch watch_A = new Watch("watch_A");
        Watch watch_B = new Watch("watch_B");

        Set<TStock> tStocksOfWatchlistA = new HashSet<>();
        tStocksOfWatchlistA.add(tStockA);
        tStocksOfWatchlistA.add(tStockB);
        tStocksOfWatchlistA.add(tStockC);
        
        Set<TStock> tStocksOfWatchlistB = new HashSet<>();
        tStocksOfWatchlistB.add(tStockC);
        

        watch_A.settStocks(tStocksOfWatchlistA);
        watch_B.settStocks(tStocksOfWatchlistB);

        entityManager.persist(watch_A);
        entityManager.persist(watch_B);
        entityManager.flush();
    }

    public List<Watch> findAll() {
        TypedQuery<Watch> query
                = entityManager.createQuery("select w from Watch w", Watch.class);
        List<Watch> watchs = query.getResultList();

        return watchs;
    }

    public Staff findOneById(Optional<Integer> id) {
        return entityManager.find(Staff.class, id.get());
    }

    public void updateRolesById(Optional<Integer> id, String... roles) {
        Staff staff = entityManager.find(Staff.class, id.get());
        Set<Role> modifiedRoles = new HashSet<>();
        for (String role : roles) {
            Query query = entityManager.createQuery("select r from Role r where r.name = ?1", Role.class);
            query.setParameter(1, role);
            Role queryRole = (Role) query.getSingleResult();
            modifiedRoles.add(queryRole);
        }

        staff.setRoles(modifiedRoles);
        entityManager.persist(staff);
        entityManager.flush();
    }

    public void deleteWatchListById(Optional<Integer> id) {
        Watch watch = entityManager.find(Watch.class, id.get());
        entityManager.remove(watch);
    }

    public Optional<Staff> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(Staff.class, id.get()));
    }
}
