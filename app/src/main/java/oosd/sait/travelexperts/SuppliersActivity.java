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

public class SuppliersActivity extends AppCompatActivity {
    TextView tvHeader;
    ListView lvSuppliers;
    Button btnAddSupplier;
    DataResource<Supplier, Integer> dataSource;
    ArrayAdapter<Supplier> adapter;
    private static SuppliersActivity instance;

    public static SuppliersActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        instance = this;

        tvHeader = findViewById(R.id.tvHeader);
        lvSuppliers = findViewById(R.id.lvSuppliers);
        btnAddSupplier = findViewById(R.id.btnAddSupplier);
        dataSource = new SupplierResource();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvSuppliers.setAdapter(adapter);

        loadSuppliers();

        lvSuppliers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), SupplierDetailActivity.class);
                intent.putExtra("supplier", adapter.getItem(i));
                startActivity(intent);
            }
        });
        btnAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SupplierCreateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadSuppliers() {
        new AsyncRunnable<>(
                () -> dataSource.getList(),
                (result) -> {
                    adapter.clear();
                    result.forEach(adapter::add);
                },
                SuppliersActivity.this
        ).start();
    }
}