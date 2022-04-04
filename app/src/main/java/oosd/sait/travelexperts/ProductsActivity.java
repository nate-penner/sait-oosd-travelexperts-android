package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.ProductManager;

public class ProductsActivity extends AppCompatActivity {
    Button btnFindProduct;
    EditText etProductId;
    TextView tvProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        DataSource<Product, Integer> ds = new ProductManager(getApplicationContext());

        btnFindProduct = findViewById(R.id.btnFindProduct);
        etProductId = findViewById(R.id.etProductId);
        tvProductName = findViewById(R.id.tvProductName);

        btnFindProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productId = Integer.parseInt(etProductId.getText().toString());
                Product product = ds.getById(productId);
                tvProductName.setText(product.getProductName());
            }
        });
    }
}