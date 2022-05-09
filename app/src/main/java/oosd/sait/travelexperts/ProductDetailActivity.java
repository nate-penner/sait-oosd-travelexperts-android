package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.ProductResource;

/**
 * Packages activity. Displays a list of vacation packages that can be clicked for more information,
 * and a button to add a new package
 * @author Nate Penner
 * */
public class ProductDetailActivity extends AppCompatActivity {
    // UI elements
    TextView tvHeader;
    EditText etProductId, etProductName;
    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Setup UI references
        tvHeader = findViewById(R.id.tvHeader);
        etProductId = findViewById(R.id.etBasePrice);
        etProductName = findViewById(R.id.etAgencyCommission);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        // Disallow editing for the ID field
        etProductId.setFocusable(false);
        etProductName.setFocusedByDefault(true);

        // Check if this is edit or create mode
        String mode = getIntent().getStringExtra("mode");

        // Data source to use for creating/updating the products
        DataResource<Product, Integer> ds = new ProductResource();

        if (mode.equals("create")) {
            tvHeader.setText("New Product");

            // no need for the delete button in create mode
            btnDelete.setVisibility(View.GONE);

            // Save button click listener
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the product data from the UI
                    String productName = etProductName.getText().toString();

                    // Create the product on a separate thread
                    new Thread(() -> {
                        int result = ds.insert(new Product(0, productName));
                        Log.d("nate", result+"");

                        // Update the product list for ProductsActivity on the UI thread, then return
                        // to that activity
                        runOnUiThread(() -> {
                            ProductsActivity.getInstance().loadProducts();
                            Toast.makeText(ProductsActivity.getInstance(), "Product added.", Toast.LENGTH_LONG).show();
                            finish();
                        });

                    }).start();
                }
            });
        } else {
            // Get the product id to update from the previous activity
            int productId = getIntent().getIntExtra("productId", 0);

            // Fetch the product data based on productId
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Product product = ds.getById(productId);

                    // Put the data into the UI on the UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHeader.setText(MessageFormat.format("Product - {0}", product.getProductName()));
                            etProductId.setText(MessageFormat.format("{0}", product.getProductId()));
                            etProductName.setText(product.getProductName());

                            // Setup save button click listener
                            btnSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    product.setProductName(etProductName.getText().toString());

                                    // Update the product
                                    new Thread(() -> {
                                        int result = ds.update(product);

                                        // Reload the product list in ProductsActivity and return to
                                        // that activity
                                        runOnUiThread(() -> {
                                            ProductsActivity.getInstance().loadProducts();
                                            Toast.makeText(ProductsActivity.getInstance(), "Product saved.", Toast.LENGTH_LONG).show();
                                            finish();
                                        });
                                    }).start();
                                }
                            });

                            // Handle delete button click
                            btnDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new Thread(() -> {
                                        // Delete the record
                                        int result = ds.delete(product);

                                        // Reload the product list on ProductsActivity and return
                                        // to that activity
                                        runOnUiThread(() -> {
                                            ProductsActivity.getInstance().loadProducts();
                                            Toast.makeText(ProductsActivity.getInstance(), result > 0 ? "Product deleted." : "Product is in use by a package or supplier. Not deleted.", Toast.LENGTH_LONG).show();
                                            finish();
                                        });
                                    }).start();
                                }
                            });
                        }
                    });
                }
            }).start();
        }
    }
}