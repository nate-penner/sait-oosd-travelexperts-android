package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collection;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.Booking;
import oosd.sait.travelexperts.data.BookingResource;

public class BookingsActivity extends AppCompatActivity {
    TextView tvHeader;
    ListView lvBookings;
    BookingResource dataSource;
    ArrayAdapter<Booking> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        tvHeader = findViewById(R.id.tvHeader);
        lvBookings = findViewById(R.id.lvBookings);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvBookings.setAdapter(adapter);

        dataSource = new BookingResource();

        int customerId = getIntent().getIntExtra("customerId", 0);

        loadBookings(customerId);
    }

    public void loadBookings(int customerId) {
        adapter.clear();
        new AsyncRunnable<>(
                () -> {
                    // New thread for network activity
                    Collection<Booking> bookingsList = dataSource.getBookingsByCustomer(customerId);
                    return bookingsList;
                },
                (result) -> {
                    // Back on UI thread
                    result.forEach(adapter::add);
                },BookingsActivity.this
        ).start();
    }
}