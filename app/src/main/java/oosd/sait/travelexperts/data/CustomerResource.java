package oosd.sait.travelexperts.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerResource extends DataSource<Customer, Integer> {

    public CustomerResource(Context context) {
        super(context);
    }
    @Override
    public int insert(Customer data) {
        APIRequest request = new APIRequest(
                API.get("customers"),
                "/create",
                "PUT",
                "application/json",
                "application/json"
        );
        JSONObject obj = new JSONObject();
        try {
            obj.put("custFirstName", data.getFirstName());
            obj.put("custLastName", data.getLastName());
            obj.put("custAddress", data.getAddress());
            obj.put("custCity", data.getCity());
            obj.put("custProv", data.getProvince());
            obj.put("custPostal", data.getPostalCode());
            obj.put("custCountry", data.getCountry());
            obj.put("custHomePhone", data.getHomePhone());
            obj.put("custBusPhone", data.getBusinessPhone());
            obj.put("custEmail", data.getEmail());
            obj.put("agentId", data.getAgentId());
            request.send(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public Customer getById(Integer id) {
        APIRequest request = new APIRequest(
                API.get("customers"),
                "/get/"+id,
                "GET",
                "application/json",
                "application/json"
        );
        String data = request.send();

        Log.d("nate", "package data received: " + data);
        try {
            JSONObject response = new JSONObject(data);
            Customer customer = new Customer(
                    response.getInt("customerId"),
                    response.getString("custFirstName"),
                    response.getString("custLastName"),
                    response.getString("custAddress"),
                    response.getString("custCity"),
                    response.getString("custProv"),
                    response.getString("custPostal"),
                    response.getString("custCountry"),
                    response.getString("custHomePhone"),
                    response.getString("custBusPhone"),
                    response.getString("custEmail"),
                    response.getInt("agentId")
            );
            return customer;
        } catch (JSONException e) {
            Log.d("nate", "JSON exception:");
            Log.d("nate", e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d("nate", "Illegal argument");
            Log.d("nate", e.getMessage());
        }

        return null;
    }

    @Override
    public int update(Customer data) {
        APIRequest request = new APIRequest(
                API.get("customers"),
                "/update",
                "POST",
                "application/json",
                "application/json"
        );
        JSONObject obj = new JSONObject();
        try {
            obj.put("customerId", data.getCustomerId());
            obj.put("custFirstName", data.getFirstName());
            obj.put("custLastName", data.getLastName());
            obj.put("custAddress", data.getAddress());
            obj.put("custCity", data.getCity());
            obj.put("custProv", data.getProvince());
            obj.put("custPostal", data.getPostalCode());
            obj.put("custCountry", data.getCountry());
            obj.put("custHomePhone", data.getHomePhone());
            obj.put("custBusPhone", data.getBusinessPhone());
            obj.put("custEmail", data.getEmail());
            obj.put("agentId", data.getAgentId());
            request.send(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public int delete(Customer data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        APIRequest request = new APIRequest(
                API.get("customers"),
                "/delete/"+id,
                "DELETE",
                "application/json",
                "application/json"
        );
        try {
            request.send();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Collection<Customer> getList() {
        Log.d("nate", "Gonna get customer list from REST service");

        List<Customer> customerList  = new ArrayList<>();
        APIRequest request = new APIRequest(
                API.get("customers"),
                "/list",
                "GET",
                "application/json",
                "application/json"
        );
        String data = request.send();

        Log.d("nate", "package data received: " + data);
        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject customerData = jsonArray.getJSONObject(i);
                Customer customer = new Customer(
                    customerData.getInt("customerId"),
                    customerData.getString("custFirstName"),
                    customerData.getString("custLastName"),
                    customerData.getString("custAddress"),
                    customerData.getString("custCity"),
                    customerData.getString("custProv"),
                    customerData.getString("custPostal"),
                    customerData.getString("custCountry"),
                    customerData.getString("custHomePhone"),
                    customerData.getString("custBusPhone"),
                    customerData.getString("custEmail"),
                    customerData.getInt("agentId")
                );
                customerList.add(customer);
            }
        } catch (JSONException e) {
            Log.d("nate", "JSON exception:");
            Log.d("nate", e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d("nate", "Illegal argument");
            Log.d("nate", e.getMessage());
        }

        Log.d("nate", "returning customer list of length " + customerList.size());
        return customerList;
    }
}
