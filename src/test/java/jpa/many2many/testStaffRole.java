package jpa.many2many;

import com.spring.project.portfolio.entities.Role;
import com.spring.project.portfolio.entities.Staff;
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

public class testStaffRole extends JPATemplate {

    @Test
    public void test() {
        // C - Create
        create();
        // R - Read
        List<Staff> staffs = findAll();
        staffs.stream().forEach(System.out::println);
        // U - update
        updateRolesById(Optional.of(2), "Manager", "Admin", "Staff");
        Staff staff = findOneById(Optional.of(2));
//        Assert.assertEquals("Manager", staff.getRoles().get(0).getName());
//        Assert.assertEquals("Admin", staff.getRoles().get(1).getName());
//        Assert.assertEquals("Staff", staff.getRoles().get(2).getName());
        // D - delete
        deleteById(Optional.of(1));
        Optional<Staff> staffNotExisted = findNotExistedById(Optional.of(1));
        Assert.assertFalse(staffNotExisted.isPresent());
    }

    public void create() {

        Staff staffA = new Staff("Staff_A");
        Staff staffB = new Staff("Staff_B");

        Role roleOfManager = new Role("Manager");
        Role roleOfAdmin = new Role("Admin");
        Role roleOfStaff = new Role("Staff");

        Set<Role> rolesOfStaffA = new HashSet<>();
        rolesOfStaffA.add(roleOfManager);
        rolesOfStaffA.add(roleOfAdmin);

        Set<Role> rolesOfStaffB = new HashSet<>();
        rolesOfStaffB.add(roleOfStaff);

        // relation
        staffA.setRoles(rolesOfStaffA);
        staffB.setRoles(rolesOfStaffB);

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

    public void deleteById(Optional<Integer> id) {
        Staff staff = entityManager.find(Staff.class, id.get());
        entityManager.remove(staff);
    }

    public Optional<Staff> findNotExistedById(Optional<Integer> id) {
        return Optional.ofNullable(entityManager.find(Staff.class, id.get()));
    }
}
