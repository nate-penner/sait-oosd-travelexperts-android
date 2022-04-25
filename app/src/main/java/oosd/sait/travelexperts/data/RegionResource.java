package oosd.sait.travelexperts.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RegionResource extends DataSource<Province, Integer> {

    public RegionResource(Context context) {
        super(context);
    }

    @Override
    public int insert(Province data) {
        return 0;
    }

    @Override
    public Province getById(Integer id) {
        return null;
    }

    @Override
    public int update(Province data) {
        return 0;
    }

    @Override
    public int delete(Province data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Province> getList() {
        Log.d("nate", "getting province list...");
        APIRequest request = new APIRequest(
                API.get("regions"),
                "/province/list",
                "GET",
                "application/json",
                "application/json"
        );

        String data = request.send();
        Log.d("nate", "got data: " + data);
        List<Province> provincesList = new ArrayList<>();
        try {
            JSONObject provinceData = new JSONObject(data);

            Iterator<String> keys = provinceData.keys();
            while(keys.hasNext()) {
                String provinceName = keys.next();
                Log.d("nate", "province: " + data);
                Log.d("nate", "code: " + provinceData.getString(provinceName));
                provincesList.add(new Province(
                        provinceName, provinceData.getString(provinceName)
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return provincesList;
    }
}
