package edu.escuelaing.arep;

import edu.escuelaing.arep.Services.MathServices;

import static spark.Spark.*;

public class App {
    public static void main( String[] args ) {
        port(getPort());
        MathServices mathServices = new MathServices();

        get("/hello", (req, res) -> "Hello Server");

        // Allow CORS
        options("/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                    }
                    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                    }
                    return "OK";
                });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/cos", (req, res) -> {
            System.out.println("Servicio");
            res.type("application/json");
            String value = req.queryParams("value");
            return mathServices.cos(Double.parseDouble(value));
        });

        get("/sqrt", (req, res) -> {
            System.out.println("Servicio");
            res.type("application/json");
            String value = req.queryParams("value");
            return mathServices.sqrt(Double.parseDouble(value));
        });


    }

    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     *
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}