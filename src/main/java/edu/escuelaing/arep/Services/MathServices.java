package edu.escuelaing.arep.Services;

import com.google.gson.JsonObject;

public class MathServices {

    public JsonObject cos(double value){
        double ans = Math.cos(value);

        JsonObject json = new JsonObject();

        json.addProperty("operation", "cos");
        json.addProperty("input", value);
        json.addProperty("output", ans);

        return json;
    }

    public JsonObject sqrt(double value){
        double ans = Math.sqrt(value);

        JsonObject json = new JsonObject();

        json.addProperty("operation", "sqrt");
        json.addProperty("input", value);
        json.addProperty("output", ans);

        return json;
    }


}
