package org.austral.ing.lab1;

import org.austral.ing.lab1.model.Gamer;

import org.austral.ing.lab1.persistence.Database2;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class GamerTest {

    private EntityManagerFactory sessionFactory2;

    private final static Database2 database2 = new Database2();

    @BeforeClass
    public static void beforeSuite() {
        database2.startDBServer();
    }

    @AfterClass
    public static void afterSuite() {
        database2.stopDBServer();
    }

    @Before
    public void beforeTest() {
        sessionFactory2 = Persistence.createEntityManagerFactory("lab1");
    }

    @After
    public void close() {
        sessionFactory2.close();
    }

    @Test
    public void persistGamer() {
        final EntityManager entityManager2 = sessionFactory2.createEntityManager();
        final EntityTransaction transaction2 = entityManager2.getTransaction();
        transaction2.begin();

        final Gamer kyle = Gamer.create("Kyle").password("123456").build();
        entityManager2.persist(kyle);

        final List<Gamer> allGamers = entityManager2.createQuery("SELECT u FROM Gamer u", Gamer.class).getResultList();

        Assert.assertEquals(1, allGamers.size());

        transaction2.commit();

        entityManager2.close();
    }
}
