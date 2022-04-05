package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.ProductManager;

public class ProductsActivity extends AppCompatActivity {
    ListView lvProducts;
    Button btnAddProduct;
    ArrayAdapter<Product> adapter;
    DataSource<Product, Integer> ds;

    public static ProductsActivity instance;

    public static ProductsActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        instance = this;
        ds = new ProductManager(getApplicationContext());

        lvProducts = findViewById(R.id.lvProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1);
        lvProducts.setAdapter(adapter);

        loadProducts();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("mode", "create");
                startActivity(intent);
            }
        });

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("productId", adapter.getItem(i).getProductId());
                startActivity(intent);
            }
        });
    }

    public void loadProducts() {
        adapter.clear();
        ds.getList().forEach(p -> adapter.add(p));
    }
}