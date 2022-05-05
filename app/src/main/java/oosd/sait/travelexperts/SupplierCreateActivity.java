package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.Supplier;
import oosd.sait.travelexperts.data.SupplierResource;

public class SupplierCreateActivity extends AppCompatActivity {
    EditText etSupplierId, etSupplierName;
    Button btnSave;
    DataResource<Supplier, Integer> supplierData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_create);

        etSupplierId = findViewById(R.id.etSupplierId);
        etSupplierName = findViewById(R.id.etSupplierName);
        btnSave = findViewById(R.id.btnSave);
        supplierData = new SupplierResource();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Supplier supplier = new Supplier(
                        Integer.parseInt(etSupplierId.getText().toString()),
                        etSupplierName.getText().toString(),
                        new ArrayList<>()
                );
                new AsyncRunnable<>(
                        () -> supplierData.insert(supplier),
                        (result) -> {
                            if (result == 1) {
                                Toast.makeText(getApplicationContext(), "Supplier created.",
                                        Toast.LENGTH_LONG).show();
                                SuppliersActivity.getInstance().loadSuppliers();
                                finish();
                            } else if (result == -1) {
                                Toast.makeText(getApplicationContext(), "Duplicate supplier ID. Please choose another.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "An unknown error occurred. Please try again later.",
                                        Toast.LENGTH_LONG).show();
                            }
                        },
                        SupplierCreateActivity.this
                ).start();
            }
        });
    }
}