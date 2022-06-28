package lab1.rest;

import lab1.rest.model.Game;
import lab1.rest.model.Rank;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MySystemTest {

    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("my-system-db");

    @Test
    public void deleteGame() {
        createGamesAndRanks();

        final EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Game fifa21WithRanks = findGame(em, "Fifa 21");
        assertEquals(2, fifa21WithRanks.getRanks().size());

        // Quiero borrar el rank 1
        // Lo elimino de la colección y persisto la instancia
        fifa21WithRanks.getRanks().removeIf(r -> Objects.equals(r.getName(), "Rank 1"));
        em.persist(fifa21WithRanks);

        // Busco el Ranking que quiero borrar
        final Rank rank = findRank(em,"Rank 1").orElseThrow();
        // y lo borro
        em.remove(rank);

        em.getTransaction().commit();

        System.out.println("Debug line");
    }


    private Game findGame(EntityManager em, String name) {
        return em.find(Game.class, name);
    }

    private Optional<Rank> findRank(EntityManager em, String name) {
        return em.createQuery("SELECT r FROM Rank r WHERE r.name LIKE :name", Rank.class).setParameter("name", name).getResultList().stream().findFirst();
    }

    private void createGamesAndRanks() {
        final Game fifa22 = new Game();
        fifa22.setCategory("Sports");
        fifa22.setName("Fifa 22");

        final Game fifa21 = new Game();
        fifa21.setCategory("Sports");
        fifa21.setName("Fifa 21");

        final Game pacman = new Game();
        pacman.setCategory("Arcade");
        pacman.setName("Pac-man");

        final Rank rank1 = new Rank();
        rank1.setName("Rank 1");

        final Rank rank2 = new Rank();
        rank2.setName("Rank 2");

        final Rank rank3 = new Rank();
        rank3.setName("Rank 3");

        final Rank rank4 = new Rank();
        rank4.setName("Rank 4");

        final EntityManager em1 = factory.createEntityManager();

        em1.getTransaction().begin();

        em1.persist(rank1);
        em1.persist(rank2);
        em1.persist(rank3);
        em1.persist(rank4);

        em1.persist(fifa22);
        em1.persist(fifa21);
        em1.persist(pacman);

        fifa21.getRanks().add(rank1);
        fifa21.getRanks().add(rank2);

        em1.persist(fifa21);

        fifa22.getRanks().add(rank3);
        em1.persist(fifa22);

        em1.getTransaction().commit();
        em1.close();
    }

}
