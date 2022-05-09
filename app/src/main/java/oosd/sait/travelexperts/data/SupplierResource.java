package oosd.sait.travelexperts.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A data source for obtaining supplier information from an API, which implements {@link DataResource}
 * (see for documentation of interface methods)
 * @see Supplier for information about the type of data used by this resource
 * @apiNote Only methods used so far in this project are implemented
 * @author Nate Penner
 * */
public class SupplierResource implements DataResource<Supplier, Integer> {
    @Override
    public int insert(Supplier data) {
        APIRequest request = new APIRequest(
                API.get("suppliers"),
                "/create",
                "PUT",
                "application/json",
                "application/json"
        );

        try {
            JSONObject obj = new JSONObject();
            obj.put("id", data.getId());
            obj.put("supName", data.getName());

            String responseData = request.send(obj.toString());

            Log.d("nate", responseData);

            JSONObject response = new JSONObject(responseData);

            return response.getInt("statusCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Supplier getById(Integer id) {
        return null;
    }

    @Override
    public int update(Supplier data) {
        APIRequest request = new APIRequest(
                API.get("suppliers"),
                "/update",
                "POST",
                "application/json",
                "application/json"
        );

        try {
            JSONObject uploadData = new JSONObject();
            uploadData.put("id", data.getId());
            JSONArray productsList = new JSONArray();
            for (Product p : data.getProducts()) {
                JSONObject product = new JSONObject();
                product.put("productId", p.getProductId());
                product.put("prodName", p.getProductName());
                productsList.put(product);
            }
            uploadData.put("products", productsList);

            String responseData = request.send(uploadData.toString());
            Log.d("nate", responseData);

            JSONObject response = new JSONObject(responseData);
            return response.getInt("statusCode");
        } catch (JSONException e) {
            Log.d("nate", "something went wrong");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Supplier data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Supplier> getList() {
        APIRequest request = new APIRequest(
                API.get("suppliers"),
                "/list",
                "GET",
                "application/json",
                "application/json"
        );

        List<Supplier> suppliersList = new ArrayList<>();

        String data = request.send();

        try {
            JSONArray arrayList = new JSONArray(data);

            for (int i = 0; i < arrayList.length(); i++) {
                JSONObject supplierObj = arrayList.getJSONObject(i);
                JSONArray productsList = supplierObj.getJSONArray("products");
                List<Product> productsArrayList = new ArrayList<>();
                for (int j = 0; j < productsList.length(); j++) {
                    JSONObject productObj = productsList.getJSONObject(j);
                    productsArrayList.add(new Product(
                            productObj.getInt("productId"),
                            productObj.getString("prodName")
                    ));
                }
                Supplier supplier = new Supplier(
                        supplierObj.getInt("id"),
                        supplierObj.getString("supName"),
                        productsArrayList
                );
                suppliersList.add(supplier);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return suppliersList;
    }
}
