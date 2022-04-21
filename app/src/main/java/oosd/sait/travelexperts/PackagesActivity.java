package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collection;

import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Package;
import oosd.sait.travelexperts.data.PackageResource;
import oosd.sait.travelexperts.data.Product;

public class PackagesActivity extends AppCompatActivity {
    ListView lvPackages;
    Button btnAddPackage;
    ArrayAdapter<Package> adapter;
    DataSource<Package, Integer> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        lvPackages = findViewById(R.id.lvPackages);
        btnAddPackage = findViewById(R.id.btnAddPackage);
        adapter = new ArrayAdapter<Package>(this, android.R.layout.simple_list_item_1);
        lvPackages.setAdapter(adapter);

        dataSource = new PackageResource(this);

        loadPackages();

        btnAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PackageDetailActivity.class);
                intent.putExtra("mode", "create");
                startActivity(intent);
            }
        });
        lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PackageDetailActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("packageId", adapter.getItem(i).getId());
                startActivity(intent);
            }
        });
    }

    public void loadPackages() {
        adapter.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Collection<Package> packageList = dataSource.getList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        packageList.forEach(adapter::add);
                    }
                });
            }
        }).start();
    }
}