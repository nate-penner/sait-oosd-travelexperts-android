package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import oosd.sait.travelexperts.data.Customer;
import oosd.sait.travelexperts.data.CustomerManager;
import oosd.sait.travelexperts.data.CustomerResource;
import oosd.sait.travelexperts.data.DataSource;

public class CustomersActivity extends AppCompatActivity {
    ListView lvCustomers;
    Button btnAddCustomer;
    DataSource<Customer, Integer> dataSource;
    ArrayAdapter<Customer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        lvCustomers = findViewById(R.id.lvPackages);
        btnAddCustomer = findViewById(R.id.btnAddCustomer);

        dataSource = new CustomerResource(this);

        adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1);
        lvCustomers.setAdapter(adapter);

        loadCustomers();

        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadCustomers() {
        Log.d("nate", "Loading customers...");
        adapter.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("nate", "Retrieving customer data and sorting...");
                Collection<Customer> customerList = dataSource.getList().stream()
                        .sorted(Comparator.comparing(Customer::getLastName))
                        .collect(Collectors.toList());

                Log.d("nate", "Done. Adding to customer list...");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customerList.forEach(adapter::add);
                        Log.d("nate", "Done");
                    }
                });
            }
        }).start();
    }
}