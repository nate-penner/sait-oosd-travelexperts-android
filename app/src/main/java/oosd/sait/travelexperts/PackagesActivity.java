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

/**
 * Packages activity. Displays a list of vacation packages that can be clicked for more information,
 * and a button to add a new package
 * @author Nate Penner
 * */
public class PackagesActivity extends AppCompatActivity {
    // UI elements
    ListView lvPackages;
    Button btnAddPackage;

    // Data handling variables
    ArrayAdapter<Package> adapter;
    DataSource<Package, Integer> dataSource;

    // Static instance of this object
    private static PackagesActivity instance;

    // Getter
    public static PackagesActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        // Set the static instance to this
        instance = this;

        // Setup UI references
        lvPackages = findViewById(R.id.lvDetails);
        btnAddPackage = findViewById(R.id.btnAddPackage);
        adapter = new ArrayAdapter<Package>(this, android.R.layout.simple_list_item_1);
        lvPackages.setAdapter(adapter);

        // Create data source
        dataSource = new PackageResource(this);

        // Load the packages
        loadPackages();

        // Handle click for the add package button
        btnAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open an activity to create a new package
                Intent intent = new Intent(getApplicationContext(), PackageDetailActivity.class);
                intent.putExtra("mode", "create");
                startActivity(intent);
            }
        });

        // Handle clicking on an existing package in the list
        lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Open an activity to update the package
                Intent intent = new Intent(getApplicationContext(), PackageDetailActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("packageId", adapter.getItem(i).getId());
                startActivity(intent);
            }
        });
    }

    /**
     * Loads the package data from the data source
     * */
    public void loadPackages() {
        adapter.clear(); // clear the existing list

        // Get the packages data
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform network retrieval on a separate thread
                Collection<Package> packageList = dataSource.getList();

                // Add the packages to the list adapter on the UI thread
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