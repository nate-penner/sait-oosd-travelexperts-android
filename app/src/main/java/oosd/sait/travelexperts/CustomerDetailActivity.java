package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

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

import oosd.sait.travelexperts.data.AgentMin;
import oosd.sait.travelexperts.data.AgentMinResource;
import oosd.sait.travelexperts.data.Customer;
import oosd.sait.travelexperts.data.CustomerResource;
import oosd.sait.travelexperts.data.DataResource;
import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Province;
import oosd.sait.travelexperts.data.RegionResource;

public class CustomerDetailActivity extends AppCompatActivity {
    EditText etId, etFirstName, etLastName, etAddress, etCity, etPostalCode,
             etHomePhone, etBusinessPhone, etEmail;
    Spinner spProvince, spCountry, spAgents;
    Button btnSave, btnDelete, btnViewBookings;
    LinearLayout layoutBookings;
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

        countryAdapter.add("Canada");

        etId.setFocusable(false);
        etFirstName.setFocusedByDefault(true);

        String mode = getIntent().getStringExtra("mode");

        if (mode.equalsIgnoreCase("create")) {
            btnDelete.setVisibility(View.GONE);
            layoutBookings.setVisibility(View.GONE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Collection<Province> provincesList = provinceDataSource.getList();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadProvinces(provincesList);
                            setupCreateMode();
                        }
                    });
                }
            }).start();
        } else {
            Customer customer = (Customer)getIntent().getSerializableExtra("customer");
            Log.d("nate", "the customer id is " + customer.getCustomerId());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Collection<Province> provincesList = provinceDataSource.getList();
                    Collection<AgentMin> agentsList = agentDataSource.getList().stream()
                            .sorted(Comparator.comparing(AgentMin::getLastName))
                            .collect(Collectors.toList());;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadProvinces(provincesList);
                            loadAgents(agentsList);
                            populateFields(customer);
                            setupEditMode();
                        }
                    });
                }
            }).start();
        }
    }

    public void setupEditMode() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Customer customer = saveForm();
                        int result = dataSource.update(customer);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result == 1) {
                                    CustomersActivity.getInstance().loadCustomers();
                                    Toast.makeText(CustomersActivity.getInstance(), "Customer updated.",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(CustomersActivity.getInstance(), "Failed to update customer.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    public void setupCreateMode() {

    }

    public Customer saveForm() {
        Customer customer = new Customer(
                Integer.parseInt(etId.getText().toString()),
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

    public void loadProvinces(Collection<Province> provincesList) {
        provinceAdapter.clear();
        provincesList.forEach(provinceAdapter::add);
    }

    public void loadAgents(Collection<AgentMin> agentsList) {
        agentAdapter.clear();
        agentsList.forEach(agentAdapter::add);
    }

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
        for (int i = 0; i < provinceAdapter.getCount(); i++) {
             p = provinceAdapter.getItem(i);
             if (p.getName().equalsIgnoreCase(customer.getProvince())) {
                 spProvince.setSelection(i);
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