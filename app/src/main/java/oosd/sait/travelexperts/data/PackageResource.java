package oosd.sait.travelexperts.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PackageResource extends DataSource<Package, Integer> {

    public PackageResource(Context context) {
        super(context);
    }
    @Override
    public int insert(Package data) {
        APIRequest request = new APIRequest(
                API.get("packages"),
                "/create",
                "PUT",
                "application/json",
                "application/json"
        );
        JSONObject obj = new JSONObject();
        try {
            obj.put("pkgName", data.getName());
            obj.put("pkgStartDate", data.getStartDate());
            obj.put("pkgEndDate", data.getEndDate());
            obj.put("pkgDesc", data.getDescription());
            obj.put("pkgBasePrice", data.getBasePrice());
            obj.put("pkgAgencyCommission", data.getAgencyCommission());
            JSONObject response = new JSONObject(request.send(obj.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Package getById(Integer id) {
        APIRequest request = new APIRequest(
                API.get("packages"),
                "/get/"+id,
                "GET",
                "application/json",
                "application/json"
        );

        String response = request.send();

        try {
            JSONObject data = new JSONObject(response);

            return new Package(
                    data.getInt("packageId"),
                    data.getString("pkgName"),
                    Timestamp.valueOf(data.getString("pkgStartDate")).toString(),
                    Timestamp.valueOf(data.getString("pkgEndDate")).toString(),
                    data.getString("pkgDesc"),
                    data.getDouble("pkgBasePrice"),
                    data.getDouble("pkgAgencyCommission")
            );
        } catch (JSONException e) {
            Log.d("nate", "json exception: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int update(Package data) {
        APIRequest request = new APIRequest(
                API.get("packages"),
                "/update",
                "POST",
                "application/json",
                "application/json"
        );

        JSONObject pkgData = new JSONObject();
        try {
            pkgData.put("packageId", data.getId());
            pkgData.put("pkgName", data.getName());
            pkgData.put("pkgStartDate", data.getStartDate());
            pkgData.put("pkgEndDate", data.getEndDate());
            pkgData.put("pkgDesc", data.getDescription());
            pkgData.put("pkgBasePrice", data.getBasePrice());
            pkgData.put("pkgAgencyCommission", data.getAgencyCommission());
            JSONObject response = new JSONObject(request.send(pkgData.toString()));
            if (response.getString("message").equalsIgnoreCase("success"))
                return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int delete(Package data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        APIRequest request = new APIRequest(
                API.get("packages"),
                "/delete/"+id,
                "DELETE",
                "application/json",
                "application/json"
        );

        String data = request.send();

        Log.d("nate", "response received: " + data);

        try {
            JSONObject response = new JSONObject(data);
            Log.d("nate", "message: " + response.getString("message"));
            if (response.getString("message").equalsIgnoreCase("success"))
                return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Collection<Package> getList() {
        List<Package> packageList  = new ArrayList<>();
        APIRequest request = new APIRequest(
                API.get("packages"),
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
                JSONObject pkg = jsonArray.getJSONObject(i);
                Package p = new Package(
                        pkg.getInt("packageId"),
                        pkg.getString("pkgName"),
                        Timestamp.valueOf(pkg.getString("pkgStartDate")).toString(),
                        Timestamp.valueOf(pkg.getString("pkgEndDate")).toString(),
                        pkg.getString("pkgDesc"),
                        pkg.getDouble("pkgBasePrice"),
                        pkg.getDouble("pkgAgencyCommission")
                );
                packageList.add(p);
                Log.d("nate", "start date: " + p.getStartDate().toString());
            }
        } catch (JSONException e) {
            Log.d("nate", "JSON exception:");
            Log.d("nate", e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d("nate", "Illegal argument");
            Log.d("nate", e.getMessage());
        }

        Log.d("nate", "returning package list of length " + packageList.size());
        return packageList;
    }
}
