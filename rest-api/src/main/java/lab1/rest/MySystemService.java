package lab1.rest;


import lab1.rest.model.Game;
import spark.Spark;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class MySystemService {

    private final Routes routes = new Routes();

    public void start() {
        startWebServer();
    }

    public void stop() {
        stopWebServer();
    }

    private void startWebServer() {
        staticFiles.location("public");
        port(4321);
        final MySystem system = MySystem.create("my-system-db");
        routes.create(system);



        final Game game = new Game();
        game.setName("Fifa 22");
        game.setCategory("Sports");




    }

    private void stopWebServer() {
        Spark.stop();
    }

}
