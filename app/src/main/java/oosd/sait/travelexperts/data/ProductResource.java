package oosd.sait.travelexperts.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A data source for obtaining product information from an API, which implements {@link DataResource}
 * (see for documentation of interface methods)
 * @see Product for information about the type of data used by this resource
 * @apiNote Only methods used so far in this project are implemented
 * @author Nate Penner
 * */
public class ProductResource implements DataResource<Product, Integer> {
    @Override
    public int insert(Product data) {
        APIRequest request = new APIRequest(
                API.get("products"),
                "/create",
                "PUT",
                "application/json",
                "application/json"
        );
        JSONObject obj = new JSONObject();
        try {
            obj.put("prodName", data.getProductName());
            JSONObject response = new JSONObject(request.send(obj.toString()));
            Log.d("nate5", response.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Product getById(Integer id) {
        Product product = null;
        APIRequest request = new APIRequest(
                API.get("products"),
                "/get/"+id,
                "GET",
                "application/json",
                "application/json"
        );
        String data = request.send();

        try {
            JSONObject prod = new JSONObject(data);
            product = new Product(
                    prod.getInt("productId"),
                    prod.getString("prodName")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public int update(Product data) {
        APIRequest request = new APIRequest(
                API.get("products"),
                "/update",
                "POST",
                "application/json",
                "application/json"
        );
        JSONObject obj = new JSONObject();
        try {
            obj.put("productId", data.getProductId());
            obj.put("prodName", data.getProductName());
            JSONObject response = new JSONObject(request.send(obj.toString()));
            Log.d("nate6", response.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int delete(Product data) {
        APIRequest request = new APIRequest(
                API.get("products"),
                "/delete/"+data.getProductId(),
                "DELETE",
                "application/json",
                "application/json"
        );

        try {
            JSONObject response = new JSONObject(request.send());
            return response.getInt("rowsAffected");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Product> getList() {
        List<Product> productList = new ArrayList<>();
        APIRequest request = new APIRequest(
                API.get("products"),
                "/list",
                "GET",
                "application/json",
                "application/json"
        );
        String data = request.send();

        try {
//            URL url = new URL("http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/product/list");
//
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(
//                            conn.getInputStream()
//                    )
//            );
//            String line;
//            StringBuffer buffer = new StringBuffer();
//            while ((line = reader.readLine()) != null)
//                buffer.append(line);
//
//            Log.d("nate", buffer.toString());
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject product = jsonArray.getJSONObject(i);
                productList.add(new Product(
                   product.getInt("productId"),
                   product.getString("prodName")
                ));
            }

//            reader.close();
//        } catch (MalformedURLException e) {
//            Log.d("nate", "malformed url exception:");
//            Log.d("nate", e.getMessage());
//        } catch (IOException e) {
//            Log.d("nate", "IO exception:");
//            Log.d("nate", e.getMessage());
        } catch (JSONException e) {
            Log.d("nate", "JSON exception:");
            Log.d("nate", e.getMessage());
        }

        Log.d("nate", "returning product list of length " + productList.size());
        return productList;
    }
}
