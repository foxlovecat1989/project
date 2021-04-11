package jpa.many2many;

import com.google.api.client.util.Sets;
import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.entities.Staff;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpa.JPATemplate;
import org.junit.Assert;
import org.junit.Test;

public class testStaffInvestor extends JPATemplate {

    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<Staff> staffs = findAll();
        staffs.stream().forEach(System.out::println);
        // U - update

        updateInvestorById(Optional.of(1), 1, 3);
        Staff staff = findOneById(Optional.of(1));
//        Assert.assertTrue(staff.getInvestors().get(0).getId() == 1);
//        Assert.assertTrue(staff.getInvestors().get(1).getId() == 3);

        // D - delete
        deleteStaffById(Optional.of(2));
        Optional<Staff> staffNotExisted = findNotExistedById(Optional.of(2));
        Assert.assertFalse(staffNotExisted.isPresent());
    }

    public void create() {

        Staff staffA = new Staff("Staff_A");
        Staff staffB = new Staff("Staff_B");

        Investor investorA = new Investor("Amber", "1234", "Amber@gmail.com", 500000, new Date());
        Investor investorB = new Investor("Betty", "4231", "Betty@gmail.com", 8000, new Date());
        Investor investorC = new Investor("Cathy", "4231", "Cathy@gmail.com", 200000, new Date());

        staffA.getInvestors().add(investorA);
        staffA.getInvestors().add(investorB);
        staffB.getInvestors().add(investorA);

        entityManager.persist(investorA);
        entityManager.persist(investorB);
        entityManager.persist(investorC);
        entityManager.persist(staffA);
        entityManager.persist(staffB);
        entityManager.flush();
    }

    public List<Staff> findAll() {
        TypedQuery<Staff> query
                = entityManager.createQuery("select s from Staff s", Staff.class);
        List<Staff> staffs = query.getResultList();

        return staffs;
    }

    public Staff findOneById(Optional<Integer> id) {
        return entityManager.find(Staff.class, id.get());
    }

    public void updateInvestorById(Optional<Integer> id, Integer... investorIds) {
        Staff staff = entityManager.find(Staff.class, id.get());

        Set<Investor> modifiedinvestors = new HashSet<>();
        for (Integer investorId : investorIds) {
            Query query = entityManager.createQuery("select i from Investor i where i.id = ?1", Investor.class);
            query.setParameter(1, investorId);
            Investor queryInvestor = (Investor) query.getSingleResult();
            modifiedinvestors.add(queryInvestor);
        }
        staff.setInvestors(modifiedinvestors);
        entityManager.persist(staff);
        entityManager.flush();
    }

    public void deleteStaffById(Optional<Integer> id) {
        Staff staff = entityManager.find(Staff.class, id.get());
        entityManager.remove(staff);
    }

    public Optional<Staff> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(Staff.class, id.get()));
    }
}
