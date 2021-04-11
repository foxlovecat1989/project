package jpa.one2one;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Role;
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
        // U - update
        updateRoleById(Optional.of(1), "VIP_C");
        Investor investor = findOneById(Optional.of(1));
        Assert.assertEquals("VIP_C", investor.getRole().getName());
        // D - delete
        deleteById(Optional.of(2));
        Optional<Investor> investorNotExisted = findNotExistedById(Optional.of(2));
        Assert.assertFalse(investorNotExisted.isPresent());
    }

    public void create() {

        Investor investor1 = new Investor("Amber", "1234", "Amber@gmail.com", 500000, new Date());

        Investor investor2 = new Investor("Betty", "4323", "Betty@gmail.com", 10000, new Date());

        Role roleOfVipA = new Role("VIP_A");
        Role roleOfVipB = new Role("VIP_B");
        Role roleOfVipC = new Role("VIP_C");
        Role roleOfNormal = new Role("Normal");

        // relation
        investor1.setRole(roleOfVipB);
        investor2.setRole(roleOfNormal);
        
        entityManager.persist(roleOfVipA);
        entityManager.persist(roleOfVipB);
        entityManager.persist(roleOfVipC);
        entityManager.persist(roleOfNormal);
        entityManager.persist(investor1);
        entityManager.persist(investor2);
        
        entityManager.flush();

    }

    public List<Investor> findAll() {
        TypedQuery<Investor> query
                = entityManager.createQuery("select i from Investor i INNER JOIN FETCH i.role", Investor.class);
        List<Investor> investors = query.getResultList();

        return investors;
    }

    public Investor findOneById(Optional<Integer> id) {
        return entityManager.find(Investor.class, id.get());
    }

    public void updateRoleById(Optional<Integer> id, String role) {
        Investor investor = entityManager.find(Investor.class, id.get());
        Query query = entityManager.createQuery("Select r from Role r WHERE r.name = ?1");
        query.setParameter(1, role);
        Role queryRole = (Role)query.getSingleResult();
        investor.setRole(queryRole);
        entityManager.persist(investor);
        entityManager.flush();
    }

    public void deleteById(Optional<Integer> id) {
        Investor investor = entityManager.find(Investor.class, id.get());
        entityManager.remove(investor);
    }

    public Optional<Investor> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(Investor.class, id.get()));
    }
}
