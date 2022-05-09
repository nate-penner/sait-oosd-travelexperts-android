package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.AgentMin;
import oosd.sait.travelexperts.data.AgentMinResource;
import oosd.sait.travelexperts.data.Customer;
import oosd.sait.travelexperts.data.CustomerResource;
import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Province;
import oosd.sait.travelexperts.data.RegionResource;

/**
* Customer details activity - display information about a customer that can be edited
* @author Nate Penner
* */
public class CustomerDetailActivity extends AppCompatActivity {
    // UI Elements
    EditText etId, etFirstName, etLastName, etAddress, etCity, etPostalCode,
             etHomePhone, etBusinessPhone, etEmail;
    Spinner spProvince, spCountry, spAgents;
    Button btnSave, btnDelete, btnViewBookings;
    LinearLayout layoutBookings;

    // Data handling variables
    DataSource<Customer, Integer> dataSource;
    DataSource<Province, Integer> provinceDataSource;
    DataResource<AgentMin, Integer> agentDataSource;
    ArrayAdapter<Province> provinceAdapter;
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<AgentMin> agentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        // Initialize UI references
        etId = findViewById(R.id.etId);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etHomePhone = findViewById(R.id.etHomePhone);
        etBusinessPhone = findViewById(R.id.etBusinessPhone);
        etEmail = findViewById(R.id.etEmail);
        spProvince = findViewById(R.id.spProvince);
        spCountry = findViewById(R.id.spCountry);
        spAgents = findViewById(R.id.spAgents);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewBookings = findViewById(R.id.btnViewBookings);
        layoutBookings = findViewById(R.id.layoutBookings);

        // Setup data sources for various fields
        dataSource = new CustomerResource(this);
        provinceDataSource = new RegionResource(this);
        agentDataSource = new AgentMinResource();
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item);
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item);
        agentAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item);

        spProvince.setAdapter(provinceAdapter);
        spCountry.setAdapter(countryAdapter);
        spAgents.setAdapter(agentAdapter);

        // Only Canada is supported for now
        countryAdapter.add("Canada");

        // Disable the ID field
        etId.setFocusable(false);
        etFirstName.setFocusedByDefault(true);

        // Check if we are in create or edit mode
        String mode = getIntent().getStringExtra("mode");

        if (mode.equalsIgnoreCase("create")) {
            // No need for the delete button in create mode
            btnDelete.setVisibility(View.GONE);
            layoutBookings.setVisibility(View.GONE);

            // Retrieve data and setup UI
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Use a different thread for network activity
                    Collection<Province> provincesList = provinceDataSource.getList();
                    Collection<AgentMin> agentsList = agentDataSource.getList().stream()
                            .sorted(Comparator.comparing(AgentMin::getLastName))
                            .collect(Collectors.toList());

                    // Run on UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Load the downloaded data into Spinners, then set up create mode
                            loadProvinces(provincesList);
                            loadAgents(agentsList);
                            setupCreateMode();
                        }
                    });
                }
            }).start();
        } else {
            // Get the data of the selected customer from the previous activity
            Customer customer = (Customer)getIntent().getSerializableExtra("customer");
            Log.d("nate", "the customer id is " + customer.getCustomerId());

            // Retrieve data and setup UI
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Perform network operations on another thread
                    Collection<Province> provincesList = provinceDataSource.getList();
                    Collection<AgentMin> agentsList = agentDataSource.getList().stream()
                            .sorted(Comparator.comparing(AgentMin::getLastName))
                            .collect(Collectors.toList());

                    // Setup UI on the UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Load downloaded data into spinners
                            loadProvinces(provincesList);
                            loadAgents(agentsList);

                            // fill in fields with customer data
                            populateFields(customer);

                            // Set up the UI for edit mode
                            setupEditMode(customer);
                        }
                    });
                }
            }).start();
        }
    }

    /**
     * Set up the UI for edit mode
     * @param customer The customer being modified
     * */
    public void setupEditMode(Customer customer) {
        // Setup on click listener for save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = saveForm();     // Get the data from the UI

                // Update the customer data
                new AsyncRunnable<>(
                    () -> {
                        // Offload network task to background thread
                        return dataSource.update(customer);
                    },
                    (result) -> {
                        // Update stuff on UI thread
                        if (result == 1) {
                            CustomersActivity.getInstance().loadCustomers();
                            Toast.makeText(CustomersActivity.getInstance(), "Customer updated.",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CustomersActivity.getInstance(), "Failed to update customer.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }, CustomerDetailActivity.this
                )
                .start();
            }
        });

        // Set up the delete button click listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncRunnable<>(
                    () -> {
                        // Offload network task to background thread
                        return dataSource.deleteById(customer.getCustomerId());
                    },
                    (result) -> {
                        // Update stuff on UI thread
                        if (result == 1) {
                            CustomersActivity.getInstance().loadCustomers();
                            Toast.makeText(CustomersActivity.getInstance(), "Customer deleted.",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CustomersActivity.getInstance(), "Failed to delete customer.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }, CustomerDetailActivity.this
                ).start();
            }
        });

        // Set up the view bookings button click handler
        btnViewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookingsActivity.class);
                // Pass the customer to the Booking activity
                intent.putExtra("customer", customer);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up the UI for customer creation mode
     * */
    public void setupCreateMode() {
        // Set up save button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Customer customer = saveForm();
                new AsyncRunnable<>(
                    () -> {
                        // Offload network task to background thread
                        return dataSource.insert(customer);
                    },
                    (result) -> {
                        // Update stuff on UI thread
                        if (result == 1) {
                            CustomersActivity.getInstance().loadCustomers();
                            Toast.makeText(CustomersActivity.getInstance(), "Customer created.",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(CustomersActivity.getInstance(), "Failed to create customer.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }, CustomerDetailActivity.this
                ).start();
            }
        });
    }

    /**
     * Pulls all customer data from the UI and creates a Customer object out of it
     * @return a new Customer object
     * */
    public Customer saveForm() {
        int id;
        try {
            id = Integer.parseInt(etId.getText().toString());
        } catch (Exception e) {
            id = 0;
        }
        Customer customer = new Customer(
                id,
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etAddress.getText().toString(),
                etCity.getText().toString(),
                ((Province)spProvince.getSelectedItem()).getCode(),
                etPostalCode.getText().toString(),
                (String)spCountry.getSelectedItem(),
                etHomePhone.getText().toString(),
                etBusinessPhone.getText().toString(),
                etEmail.getText().toString(),
                ((AgentMin)spAgents.getSelectedItem()).getId()
        );

        return customer;
    }

    /**
     * Loads a collection of provinces into the adapter for the spinner
     * */
    public void loadProvinces(Collection<Province> provincesList) {
        provinceAdapter.clear();
        provincesList.forEach(provinceAdapter::add);
    }

    /**
     * Loads a collection of agents into the adapter for the spinner
     * */
    public void loadAgents(Collection<AgentMin> agentsList) {
        agentAdapter.clear();
        agentsList.forEach(agentAdapter::add);
    }

    /**
     * Loads the customer data received from the previous activity into the user interface, where it
     * can be edited.
     * */
    public void populateFields(Customer customer) {
        etId.setText(MessageFormat.format("{0}", customer.getCustomerId()));
        etFirstName.setText(customer.getFirstName());
        etLastName.setText(customer.getLastName());
        etAddress.setText(customer.getAddress());
        etCity.setText(customer.getCity());
        etPostalCode.setText(customer.getPostalCode());
        etHomePhone.setText(customer.getHomePhone());
        etBusinessPhone.setText(customer.getBusinessPhone());
        etEmail.setText(customer.getEmail());

        // Select their province
        Province p;
        Log.d("nate", "num provinces " + provinceAdapter.getCount());
        Log.d("nate", "this customer's province is " + customer.getProvince());
        for (int i = 0; i < provinceAdapter.getCount(); i++) {
            Log.d("nate", "province loop " + i);
             p = provinceAdapter.getItem(i);
             if (p.getCode().equalsIgnoreCase(customer.getProvince())) {
                 spProvince.setSelection(i);
                 Log.d("nate", "the province is " + p.getName());
                 break;
             }
        }

        // Select their agent
        AgentMin a;
        for (int i = 0; i < agentAdapter.getCount(); i++) {
            a = agentAdapter.getItem(i);
            if (a.getId() == customer.getAgentId()) {
                spAgents.setSelection(i);
                break;
            }
        }
    }
}