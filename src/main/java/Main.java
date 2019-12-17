import clases.Rutas;
import spark.Spark;

import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        port(8081);
        Spark.staticFileLocation("/publico");
        new Rutas().manejoRutas();

    }
}
