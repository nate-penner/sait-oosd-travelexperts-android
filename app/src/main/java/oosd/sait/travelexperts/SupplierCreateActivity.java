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

/**
 * Create supplier activity. Allows the user to create a new supplier. This should eventually be
 * merged with the SupplierDetailActivity to allow editing of supplier information along with
 * products offered
 * @author Nate Penner
 * */
public class SupplierCreateActivity extends AppCompatActivity {
    // UI elements
    EditText etSupplierId, etSupplierName;
    Button btnSave;

    // Data variables
    DataResource<Supplier, Integer> supplierData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_create);

        // Setup UI references
        etSupplierId = findViewById(R.id.etSupplierId);
        etSupplierName = findViewById(R.id.etSupplierName);
        btnSave = findViewById(R.id.btnSave);
        supplierData = new SupplierResource();

        // Setup save button click handler
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Supplier object from the data entered
                Supplier supplier = new Supplier(
                        Integer.parseInt(etSupplierId.getText().toString()),
                        etSupplierName.getText().toString(),
                        new ArrayList<>()
                );

                // Insert the data via the API on another thread
                new AsyncRunnable<>(
                        () -> supplierData.insert(supplier),
                        (result) -> {
                            // On the UI thread, show a status message, reload suppliers list for
                            // the previous activity, and return, or stay on this activity if
                            // the insert failed
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