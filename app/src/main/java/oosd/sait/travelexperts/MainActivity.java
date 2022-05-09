package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import oosd.sait.travelexperts.data.AgentMin;
import oosd.sait.travelexperts.data.DBHelper;

/**
 * Main activity
 * @author Nate Penner
 * */
public class MainActivity extends AppCompatActivity {
    // UI Elements
    Button btnCustomers, btnPackages, btnProducts, btnSuppliers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup UI
        btnCustomers = findViewById(R.id.btnCustomers);
        btnPackages = findViewById(R.id.btnPackages);
        btnProducts = findViewById(R.id.btnProducts);
        btnSuppliers = findViewById(R.id.btnSuppliers);

        // get the agent data passed from login activity and popup a welcome message
        AgentMin agent = (AgentMin)getIntent().getSerializableExtra("agent");
        Toast.makeText(getApplicationContext(), "Logged in as " + agent.getUsername(), Toast.LENGTH_SHORT)
                .show();

        // Setup UI handlers
        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run customer activity
                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                startActivity(intent);
            }
        });
        btnPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run packages activity
                Intent intent = new Intent(getApplicationContext(), PackagesActivity.class);
                startActivity(intent);
            }
        });
        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run products activity
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });
        btnSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run suppliers activity
                Intent intent = new Intent(getApplicationContext(), SuppliersActivity.class);
                startActivity(intent);
            }
        });
    }
}