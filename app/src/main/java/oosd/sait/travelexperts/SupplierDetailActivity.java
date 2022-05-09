package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.ProductResource;
import oosd.sait.travelexperts.data.Supplier;
import oosd.sait.travelexperts.data.SupplierResource;

/**
 * Supplier detail activity. Allows the user to adjust what products are offered by
 * this supplier
 * @author Nate Penner
 * */
public class SupplierDetailActivity extends AppCompatActivity {
    // UI elements
    TextView tvHeader;
    ListView lvSelectedProducts, lvAllProducts;
    Button btnSave;

    // Data variables
    DataResource<Supplier, Integer> supplierData;   // for retrieving data about suppliers
    DataResource<Product, Integer> productData;     // for retrieving data about products
    SimpleAdapter selectedAdapter, allAdapter;      // adapters for selected products and remaining products
    Supplier supplier;  // The current supplier being edited
    Collection<Product> allProductsList;    // A list of all products in the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);

        // Setup UI references
        tvHeader = findViewById(R.id.tvHeader);
        lvSelectedProducts = findViewById(R.id.lvSelectedProducts);
        lvAllProducts = findViewById(R.id.lvAllProducts);
        btnSave = findViewById(R.id.btnSave);

        // Setup data sources
        supplierData = new SupplierResource();
        productData = new ProductResource();

        // Get the supplier from the previous activity
        supplier = (Supplier)getIntent().getSerializableExtra("supplier");
        tvHeader.setText(supplier.getName());

        // Show the selected products in the custom adapter layout
        updateSelectedProducts();

        // Load a list available products into the custom adapter layout
        loadProducts();
    }

    // Load products from the API, filtering out already selected ones
    public void loadProducts() {
        new AsyncRunnable<>(
                productData::getList,
                (result) -> {
                    allProductsList = result;

                    // filter out products already offered
                    removeDuplicates();

                    // update the offered (selected) products list
                    updateAddProducts();

                    // setup UI event handlers
                    initUIEvents();
                },
                SupplierDetailActivity.this
        ).start();
    }

    // Start from the list of all products, then remove any already offered by the supplier
    public void removeDuplicates() {
        allProductsList = allProductsList.stream()
                .filter(p ->
                        supplier.getProducts().stream()
                        .noneMatch(sp -> sp.getProductId() == p.getProductId())
                ).collect(Collectors.toList());
    }

    // Setup the UI event handlers
    public void initUIEvents() {
        // When clicking a product from the all products (available products) list,
        // move it to the selected (offered) products list
        lvAllProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("nate", allAdapter.getItem(i)+"");
                HashMap<String, String> map = (HashMap<String, String>)allAdapter.getItem(i);
                Product selectedProd = ((ArrayList<Product>)allProductsList).get(i);
                allProductsList.remove(selectedProd);
                supplier.getProducts().add(selectedProd);
                updateSelectedProducts();
                updateAddProducts();
            }
        });

        // When clicking a product from the selected (offered) list,
        // move it to the all products (available products) list
        lvSelectedProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product selectedProd = ((ArrayList<Product>)supplier.getProducts()).get(i);
                supplier.getProducts().remove(selectedProd);
                allProductsList.add(selectedProd);
                updateSelectedProducts();
                updateAddProducts();
            }
        });

        // Perform the save operation on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the supplier data on a new thread
                new AsyncRunnable<>(
                        () -> supplierData.update(supplier),
                        (result) -> {
                            // Show a status message on the main thread. Exit the activity on success
                            if (result == 1) {
                                // Updated correctly
                                Toast.makeText(getApplicationContext(), "Supplier products modified.",
                                        Toast.LENGTH_LONG).show();
                                SuppliersActivity.getInstance().loadSuppliers();
                                finish();
                            } else if (result == -1) {
                                // Error occurred
                                Toast.makeText(getApplicationContext(), "Some products were not removed because they are used in a package deal.",
                                        Toast.LENGTH_LONG).show();
                                SuppliersActivity.getInstance().loadSuppliers();
                                finish();
                            } else {
                                // Error occurred
                                Toast.makeText(getApplicationContext(), "A json parsing error occurred.",
                                        Toast.LENGTH_LONG).show();
                                SuppliersActivity.getInstance().loadSuppliers();
                                finish();
                            }
                        },
                        SupplierDetailActivity.this
                ).start();
            }
        });
    }

    // Update the selected products custom list adapter
    public void updateSelectedProducts() {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        supplier.setProducts(
                supplier.getProducts().stream()
                    .sorted(Comparator.comparing(Product::getProductName))
                    .collect(Collectors.toList())
        );
        supplier.getProducts().forEach(p -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("prodName", p.getProductName());
            data.add(map);
        });
        String[] from = {"prodName"};
        int[] to = {R.id.txtProductName};
        selectedAdapter = new SimpleAdapter(
                SupplierDetailActivity.this,
                data,
                R.layout.selected_products_layout,
                from, to
        );
        lvSelectedProducts.setAdapter(selectedAdapter);
    }

    // Update the list of available products in the customer adapter that could
    // be added
    public void updateAddProducts() {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        allProductsList = allProductsList.stream()
                .sorted(Comparator.comparing(Product::getProductName))
                .collect(Collectors.toList());
        allProductsList.forEach(p -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("prodName", p.getProductName());
            data.add(map);
        });
        String[] from = {"prodName"};
        int[] to = {R.id.txtProductName};
        allAdapter = new SimpleAdapter(
                SupplierDetailActivity.this,
                data,
                R.layout.add_products_layout,
                from, to
        );
        lvAllProducts.setAdapter(allAdapter);
    }
}