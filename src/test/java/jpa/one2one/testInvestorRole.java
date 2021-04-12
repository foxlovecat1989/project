package jpa.one2one;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Role;
import com.spring.project.portfolio.entities.RoleType;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        updateRoleById(Optional.of(1), RoleType.MEMBER);
        Investor investor = findOneById(Optional.of(1));
        Assert.assertEquals(RoleType.MEMBER, investor.getRoles().iterator().next().getRoleType());

        // D - delete
        deleteById(Optional.of(4));
        Optional<Investor> investorNotExisted = findNotExistedById(Optional.of(4));
        Assert.assertFalse(investorNotExisted.isPresent());
    }

    public void create() {
        
        Role roleOfUser = new Role(RoleType.USER);
        Role roleOfMEMBER = new Role(RoleType.MEMBER);

        Investor investor1 = new Investor("Amber", "amber", "Amber@gmail.com", 300000, Boolean.TRUE, new Date());
        investor1.getRoles().add(roleOfUser);
        Investor investor2 = new Investor("Betty", "betty", "Betty@gmail.com", 1000000, Boolean.TRUE, new Date());
        investor2.getRoles().add(roleOfUser);
        Investor investor3 = new Investor("Cathy", "cathy", "Cathy@gmail.com", 100000, Boolean.TRUE, new Date());
        investor3.getRoles().add(roleOfMEMBER);
        Investor investor4 = new Investor("Delete", "delete", "Delete@gmail.com", 500000, Boolean.TRUE, new Date());
        investor4.getRoles().add(roleOfMEMBER);
        

        entityManager.persist(investor1);
        entityManager.persist(investor2);
        entityManager.persist(investor3);
        entityManager.persist(investor4);

        entityManager.flush();

    }

    public List<Investor> findAll() {
        TypedQuery<Investor> query
                = entityManager.createQuery("select i from Investor i INNER JOIN FETCH i.roles", Investor.class);
        List<Investor> investors = query.getResultList();

        return investors;
    }

    public Investor findOneById(Optional<Integer> id) {
        return entityManager.find(Investor.class, id.get());
    }

    public void updateRoleById(Optional<Integer> id, RoleType roleType) {
        Investor investor = entityManager.find(Investor.class, id.get());
        Query query = entityManager.createQuery("Select r from Role r WHERE r.roleType = ?1");
        query.setParameter(1, roleType);
        Role queryRole = (Role)query.getSingleResult();
        Set<Role> roles = new HashSet<>();
        roles.add(queryRole);
        investor.setRoles(roles);
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
