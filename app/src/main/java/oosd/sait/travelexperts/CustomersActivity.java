package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import oosd.sait.travelexperts.data.Customer;
import oosd.sait.travelexperts.data.CustomerManager;
import oosd.sait.travelexperts.data.DataSource;

public class CustomersActivity extends AppCompatActivity {
    ListView lvCustomers;
    Button btnAddCustomer;
    DataSource<Customer, Integer> ds;
    ArrayAdapter<Customer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        lvCustomers = findViewById(R.id.lvCustomers);
        btnAddCustomer = findViewById(R.id.btnAddCustomer);

        ds = new CustomerManager(this);

        adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1);
        lvCustomers.setAdapter(adapter);

        loadCustomers();
    }

    public void loadCustomers() {
        adapter.clear();
        ds.getList().forEach(c -> adapter.add(c));
    }
}