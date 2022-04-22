package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PackageDetailActivity extends AppCompatActivity {
    EditText startDate, endDate;
    Date selectedStartDate, selectedEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
    }

    public void showDatePicker(EditText trigger, String whichDate) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(
                getApplicationContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        trigger.setText(String.format(Locale.US, "%d/%02d/%02d", year, month, day));
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        if (whichDate.equalsIgnoreCase("startDate"))
                            selectedStartDate = c.getTime();
                        else
                            selectedEndDate = c.getTime();

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
        datePicker.setTitle("Select start date");
        datePicker.show();
    }
}