package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import oosd.sait.travelexperts.data.DBHelper;

public class MainActivity extends AppCompatActivity {
    Button btnCustomers, btnPackages, btnProducts, btnSuppliers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomers = findViewById(R.id.btnCustomers);
        btnPackages = findViewById(R.id.btnPackages);
        btnProducts = findViewById(R.id.btnProducts);
        btnSuppliers = findViewById(R.id.btnSuppliers);

        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                startActivity(intent);
            }
        });
        btnPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PackagesActivity.class);
                startActivity(intent);
            }
        });
        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });
        btnSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SuppliersActivity.class);
                startActivity(intent);
            }
        });
    }
}