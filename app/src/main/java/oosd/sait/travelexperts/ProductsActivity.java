package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collection;

import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.ProductResource;

/**
 * Products activity. Displays a list of products that can be clicked for more information,
 * and a button to add a new product
 * @author Nate Penner
 * */
public class ProductsActivity extends AppCompatActivity {
    // UI elements
    ListView lvProducts;
    Button btnAddProduct;

    // Data variables
    ArrayAdapter<Product> adapter;
    DataResource<Product, Integer> ds;

    // static instance variable containing this activity
    private static ProductsActivity instance;

    // Getter
    public static ProductsActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        // Set the instance to this
        instance = this;

        // Get the data source
        ds = new ProductResource();

        // Setup UI references
        lvProducts = findViewById(R.id.lvProducts);
        btnAddProduct = findViewById(R.id.btnAddPackage);

        // Create list adapter
        adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1);
        lvProducts.setAdapter(adapter);

        // Load products from data source
        loadProducts();

        // Setup click listener for add product button
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add product activity
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("mode", "create");
                startActivity(intent);
            }
        });

        // Setup click listener for the product list
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Edit product activity
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("productId", adapter.getItem(i).getProductId());
                startActivity(intent);
            }
        });
    }

    // Loads products
    public void loadProducts() {
        adapter.clear();    // clear the current product list

        // load the new data on another thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                Collection<Product> productList = ds.getList();
                Log.d("nate", "got a product list of length " + productList.size());

                // Update the adapter on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productList.forEach(adapter::add);
                    }
                });
            }
        }).start();
    }
}