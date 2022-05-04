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

public class SupplierDetailActivity extends AppCompatActivity {
    TextView tvHeader;
    ListView lvSelectedProducts, lvAllProducts;
    Button btnSave;
    DataResource<Supplier, Integer> supplierData;
    DataResource<Product, Integer> productData;
    SimpleAdapter selectedAdapter, allAdapter;
    Supplier supplier;
    Collection<Product> allProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_detail);

        tvHeader = findViewById(R.id.tvHeader);
        lvSelectedProducts = findViewById(R.id.lvSelectedProducts);
        lvAllProducts = findViewById(R.id.lvAllProducts);
        btnSave = findViewById(R.id.btnSave);

        supplierData = new SupplierResource();
        productData = new ProductResource();

        supplier = (Supplier)getIntent().getSerializableExtra("supplier");
        tvHeader.setText(supplier.getName());
        updateSelectedProducts();

        loadProducts();
    }

    public void loadProducts() {
        new AsyncRunnable<>(
                productData::getList,
                (result) -> {
                    allProductsList = result;
                    removeDuplicates();
                    updateAddProducts();
                    initUIEvents();
                },
                SupplierDetailActivity.this
        ).start();
    }

    public void removeDuplicates() {
        allProductsList = allProductsList.stream()
                .filter(p ->
                        supplier.getProducts().stream()
                        .noneMatch(sp -> sp.getProductId() == p.getProductId())
                ).collect(Collectors.toList());
    }

    public void initUIEvents() {
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncRunnable<>(
                        () -> supplierData.update(supplier),
                        (result) -> {
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