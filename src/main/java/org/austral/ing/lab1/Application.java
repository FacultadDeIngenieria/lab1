package org.austral.ing.lab1;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.austral.ing.lab1.model.User;
import org.austral.ing.lab1.repository.Users;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab1");

        Spark.port(4321);

        storedBasicUser(entityManagerFactory);

        /* 1. Basic Request */
        Spark.get("/hello",
                (req, resp) -> "Hello, World"
        );

        /* 2. Dynamic Content: Get Current Date & Time */
        Spark.get("/web/v1",
                (req, resp) -> {
                    final String now = Instant.now().toString();
                    return "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "  <meta charset=\"UTF-8\">\n" +
                            "  <title>Server side rendering v1</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "\n" +
                            "  <h1>Hora actual</h1>\n" +
                            "  <h3>" + now + "</h3>  \n" +
                            "\n" +
                            "</body>\n" +
                            "</html>";
                }
        );

        /* 3. Dynamic Content v2: Get Current Date & Time using template */
        Spark.get("/web/v2",
                (req, resp) -> {
                    final String now = Instant.now().toString();

                    final Map<String, Object> model = new HashMap<>();
                    model.put("time", now);
                    return new FreeMarkerEngine().render(new ModelAndView(model, "now.ftl"));
                }
        );

        /* 4. Dynamic Content: Show User Details */
        Spark.get("/users/:name",
                (req, resp) -> {
                    final String name = capitalized(req.params("name"));

                    final User user = User.create(name + "@gmail.com").firstName(name).lastName("Skywalker").build();

                    final Map<String, Object> model = new HashMap<>();
                    model.put("user", user);
                        return new FreeMarkerEngine().render(new ModelAndView(model, "user.ftl"));
                }
        );

        /* 5. Dynamic Content using query params */
        Spark.get("/users",
                (req, resp) -> {
                    final String name = capitalized(req.queryParams("name"));

                    final Map<String, Object> model = new HashMap<>();
                    if (!Strings.isNullOrEmpty(name)) {
                        final User user = User.create(name + "@gmail.com").firstName(name).lastName("Skywalker").build();
                        model.put("user", user);
                    }

                    return new FreeMarkerEngine().render(new ModelAndView(model, "user.ftl"));
                }
        );

        /* 6. Dynamic Content from Db */
        Spark.get("/persisted-users/:id",
                (req, resp) -> {
                    final String id = req.params("id");

                    final EntityManager entityManager = entityManagerFactory.createEntityManager();
                    final EntityTransaction tx = entityManager.getTransaction();
                    tx.begin();
                    User user = entityManager.find(User.class, Long.valueOf(id));
                    tx.commit();
                    entityManager.close();

                    final Map<String, Object> model = new HashMap<>();

                    model.put("user", user);
                    return new FreeMarkerEngine().render(new ModelAndView(model, "user.ftl"));
                }
        );

        /* 7. Receiving data from client */
        Spark.post("/users", "application/json", (req, resp) -> {
            final Gson gson = new Gson();

            final User user = gson.fromJson(req.body(), User.class);

            final EntityManager entityManager = entityManagerFactory.createEntityManager();
            final Users users = new Users(entityManager);
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            users.persist(user);
            resp.type("application/json");
            resp.status(201);
            tx.commit();
            entityManager.close();

            return gson.toJson(user);
        });

        Spark.get("/users1", "application/json", (req, resp) -> {

            resp.type("application/json");
            resp.status(201);

            final Gson gson = new Gson();

            User u1 = User.create("ja1").lastName("pee").build();
            User u2 = User.create("ja2").lastName("pee").build();

            List<User> users = Arrays.asList(u1, u2);
            return gson.toJson(users);
        });
    }

    private static void storedBasicUser(EntityManagerFactory entityManagerFactory) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Users users = new Users(entityManager);

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        if (users.listAll().isEmpty()) {
            final User luke =
                    User.create("luke.skywalker@jedis.org")
                            .firstName("Luke")
                            .lastName("Skywalker").
                            build();
            final User leia =
                    User.create("leia.skywalker@jedis.org")
                            .firstName("Leia")
                            .lastName("Skywalker")
                            .build();

            users.persist(luke);
            users.persist(leia);
        }
        tx.commit();
        entityManager.close();
    }

    private static String capitalized(String name) {
        return Strings.isNullOrEmpty(name) ? name : name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
