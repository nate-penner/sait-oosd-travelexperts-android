package oosd.sait.travelexperts.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class API {
    private static final HashMap<String, String> apiMap = new HashMap<>();

    public static void map(String name, String endPoint) {
        apiMap.put(name, endPoint);
    }

    public static String get(String name) {
        return apiMap.get(name);
    }
}
