package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JPATemplate {
    
    private ClassPathXmlApplicationContext ctx;
    private EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected EntityTransaction transaction;
    
    @Before
    public void before(){
        ctx = new ClassPathXmlApplicationContext("springdata-jpa-config.xml");
        // 取得連線工廠
        entityManagerFactory = ctx.getBean("entityManagerFactory", EntityManagerFactory.class);
        // 取得連線物件
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.clear();
        // 取得交易管理 & 開始
        transaction = entityManager.getTransaction();
        transaction.begin();
    } 
    
    @After
    public void after(){
        transaction.commit();
        entityManager.close(); // 關閉
        entityManagerFactory.close();
        ctx.close();
    }
}

