package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.Supplier;
import oosd.sait.travelexperts.data.SupplierResource;

/**
 * Suppliers activity. Shows a list of suppliers that can be clicked for more info/to edit. Also there
 * is a button to click and add a new supplier
 * @author Nate Penner
 * */
public class SuppliersActivity extends AppCompatActivity {
    // UI elements
    TextView tvHeader;
    ListView lvSuppliers;
    Button btnAddSupplier;

    // Data source
    DataResource<Supplier, Integer> dataSource;
    ArrayAdapter<Supplier> adapter;

    // Instance of this activity
    private static SuppliersActivity instance;

    // Getter
    public static SuppliersActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        // Set instance to this
        instance = this;

        // Setup UI references
        tvHeader = findViewById(R.id.tvHeader);
        lvSuppliers = findViewById(R.id.lvSuppliers);
        btnAddSupplier = findViewById(R.id.btnAddSupplier);

        // Setup data sources and adapter
        dataSource = new SupplierResource();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvSuppliers.setAdapter(adapter);

        // Load suppliers list into listview
        loadSuppliers();

        // Handle list item click
        lvSuppliers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Open edit detail activity to edit products
                Intent intent = new Intent(getApplicationContext(), SupplierDetailActivity.class);
                intent.putExtra("supplier", adapter.getItem(i));
                startActivity(intent);
            }
        });

        // Handle add supplier click
        btnAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open activity to create a new supplier
                Intent intent = new Intent(getApplicationContext(), SupplierCreateActivity.class);
                startActivity(intent);
            }
        });
    }

    // Load a list of suppliers from the API
    public void loadSuppliers() {
        new AsyncRunnable<>(
                () -> dataSource.getList(),
                (result) -> {
                    // Update the listview
                    adapter.clear();
                    result.forEach(adapter::add);
                },
                SuppliersActivity.this
        ).start();
    }
}