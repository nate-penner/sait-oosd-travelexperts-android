package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Package;
import oosd.sait.travelexperts.data.PackageResource;

public class PackageDetailActivity extends AppCompatActivity {
    EditText etId, etName, etStartDate, etEndDate, etDescription, etBasePrice,
             etAgencyCommission;
    TextView tvHeader;
    Button btnSave, btnDelete, btnAddEditProducts;
//    Date selectedStartDate, selectedEndDate;
    DataSource<Package, Integer> dataSource;
    Package pkg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);

        // Get references to UI controls
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etDescription = findViewById(R.id.etDescription);
        etBasePrice = findViewById(R.id.etBasePrice);
        etAgencyCommission = findViewById(R.id.etAgencyCommission);
        tvHeader = findViewById(R.id.tvHeader);
        etName.setFocusedByDefault(true);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnAddEditProducts = findViewById(R.id.btnAddEditProducts);

        // Don't let the user manually enter dates
        etStartDate.setFocusable(false);
        etEndDate.setFocusable(false);
        etId.setFocusable(false);

        // Set up date dialogs
        setupDateDialogs();

        // Connect to a data source
        dataSource = new PackageResource(this);

        // Check whether this is in create or edit mode
        String mode = getIntent().getStringExtra("mode");

        if (mode.equalsIgnoreCase("create")) {
            // Create a new package
            tvHeader.setText("New Package");
            btnDelete.setVisibility(View.GONE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveForm();

                    // TODO: Get products list from the products suppliers activity

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int result = dataSource.insert(pkg);
                            Log.d("nate", "result: " + result);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PackagesActivity.getInstance().loadPackages();
                                    Toast.makeText(PackagesActivity.getInstance(), "Package added.",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }
                    }).start();
                }
            });
        } else {
            // Edit a package

        }
    }

    public void saveForm() {
        int id;
        double basePrice, agencyCommission;
        Log.d("nate", "saving form data");
//        Log.d("nate", Timestamp.valueOf(etStartDate.getText().toString())+"");
//        Log.d("nate", Timestamp.valueOf(etEndDate.getText().toString())+"");
        if (etId.getText().toString().equals(""))
            id = 0;
        else
            id = Integer.parseInt(etId.getText().toString());

        try {
            basePrice = Double.parseDouble(etBasePrice.getText().toString());
        } catch (Exception e) {
            basePrice = 0;
        }

        try {
            agencyCommission = Double.parseDouble(etAgencyCommission.getText().toString());
        } catch (Exception e) {
            agencyCommission = 0;
        }

        pkg = new Package(
                id,
                etName.getText().toString(),
                etStartDate.getText().toString() + " 00:00:00",
                etEndDate.getText().toString() + " 00:00:00",
                etDescription.getText().toString(),
                basePrice,
                agencyCommission
        );
    }

    public void setupDateDialogs() {
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(etStartDate, "startDate");
            }
        });
        etStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    showDatePicker(etStartDate, "startDate");
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(etEndDate, "endDate");
            }
        });
        etEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    showDatePicker(etEndDate, "endDate");
            }
        });
    }

    public void showDatePicker(EditText trigger, String whichDate) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(
                PackageDetailActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        trigger.setText(String.format(Locale.US, "%d-%02d-%02d", year, month, day));
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, day);

                        trigger.requestFocus();
                    }
                }, nowYear, nowMonth, nowDay
        );
        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                trigger.requestFocus();
            }
        });
        if (whichDate.equalsIgnoreCase("startDate"))
            datePicker.setTitle("Select start date");
        else
            datePicker.setTitle("Select end date");
        datePicker.show();
    }
}