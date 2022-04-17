package oosd.sait.travelexperts.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ProductResource extends DataSource<Product, Integer> {

    public ProductResource(Context context) {
        super(context);
    }

    @Override
    public int insert(Product data) {
        return 0;
    }

    @Override
    public Product getById(Integer id) {
        return null;
    }

    @Override
    public int update(Product data) {
        return 0;
    }

    @Override
    public int delete(Product data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Product> getList() {
        List<Product> productList = new ArrayList<>();
        try {
            URL url = new URL("http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/product/list");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            url.openStream()
                    )
            );
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
                buffer.append(line);

            JSONArray jsonArray = new JSONArray(buffer.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject product = jsonArray.getJSONObject(i);
                productList.add(new Product(
                   product.getInt("id"),
                   product.getString("prodName")
                ));
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
