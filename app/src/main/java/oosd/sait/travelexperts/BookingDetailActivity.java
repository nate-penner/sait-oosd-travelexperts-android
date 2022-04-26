package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import oosd.sait.travelexperts.data.Booking;
import oosd.sait.travelexperts.data.BookingDetails;

public class BookingDetailActivity extends AppCompatActivity {
    TextView tvHeader;
    ListView lvDetails;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        tvHeader = findViewById(R.id.tvHeader);
        lvDetails = findViewById(R.id.lvDetails);

        Booking booking = (Booking)getIntent().getSerializableExtra("booking");

        tvHeader.setText("Details - " + (int)booking.getTravelerCount() + " traveler(s)");

        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(new Locale("en", "CA"));

        Arrays.stream(booking.getBookingDetails()).forEach(details -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("destination", "Trip to " + details.getDestination());
            map.put("description", "Route: " + details.getDescription());
            map.put("tripStart", details.getTripStart());
            map.put("tripEnd", details.getTripEnd());
            map.put("basePrice", dollarFormat.format(details.getBasePrice()));
            data.add(map);
        });

        String[] from = {
                "destination", "description", "tripStart", "tripEnd", "basePrice"
        };
        int[] to = {
                R.id.tvDestination, R.id.tvDescription, R.id.tvTripStart, R.id.tvTripEnd,
                R.id.tvBasePrice
        };
        adapter = new SimpleAdapter(
                BookingDetailActivity.this,
                data,
                R.layout.booking_details_layout,
                from, to
        );
        lvDetails.setAdapter(adapter);
    }
}