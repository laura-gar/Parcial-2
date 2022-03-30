package edu.escuelaing.arep;

import static spark.Spark.*;
import static spark.Spark.before;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;

import java.nio.charset.Charset;
import java.nio.charset.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ProxyServer {
    private static int url = 0;
    public static void main( String[] args ) {
//        String URL1 = "http://localhost:4567";
//        String URL2 = "";
        port(getPort());

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
            System.out.println(url);
            String URL = (url == 0) ? getURL1() : getURL2();
            url = (url + 1) % 2;
            String value = req.queryParams("value");
            return service(Double.parseDouble(value), URL + "/cos");

        });

        get("/sqrt", (req, res) -> {
            System.out.println(url);
            String URL = (url == 0) ? getURL1() : getURL2();
            url = (url + 1) % 2;
            System.out.println(URL);
            String value = req.queryParams("value");
            return service(Double.parseDouble(value), URL + "/sqrt");

        });




    }


    public static String service(Double value, String newUrl) throws IOException {

        URL url = new URL(newUrl +  "?value=" + value );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        String cont = "";
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
            cont += inputLine;
        }
        in.close();

        System.out.println(cont);

        return cont;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 2703; //returns default port if heroku-port isn't set (i.e. on localhost)
    }



    static String getURL1(){
        if(System.getenv("URL1")!= null){
            return System.getenv("URL1");
        }
        return "http://ec2-52-91-230-74.compute-1.amazonaws.com:4567/";
    }

    static String getURL2() {
        if (System.getenv("URL2") != null) {
            return System.getenv("URL2");
        }
        return "http://ec2-44-201-149-216.compute-1.amazonaws.com:4567/";
    }

}
