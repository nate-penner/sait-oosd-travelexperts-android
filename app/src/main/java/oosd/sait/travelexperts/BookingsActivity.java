package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.Booking;
import oosd.sait.travelexperts.data.BookingDetails;
import oosd.sait.travelexperts.data.BookingResource;
import oosd.sait.travelexperts.data.Customer;

public class BookingsActivity extends AppCompatActivity {
    TextView tvHeader;
    ListView lvBookings;
    BookingResource dataSource;
    Collection<Booking> bookingsList;
    SimpleAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        tvHeader = findViewById(R.id.tvHeader);
        lvBookings = findViewById(R.id.lvDetails);

        dataSource = new BookingResource();
        Customer customer = (Customer)getIntent().getSerializableExtra("customer");
        int customerId = customer.getCustomerId();

        tvHeader.setText("Bookings - " + customer.getFirstName() + " " + customer.getLastName());

        loadBookings(customerId);
    }

    public void loadBookings(int customerId) {
        new AsyncRunnable<>(
                () -> {
                    // New thread for network activity
                    return dataSource.getBookingsByCustomer(customerId);
                },
                (result) -> {
                    // Back on UI thread
                    ArrayList<HashMap<String, String>> data = new ArrayList<>();
                    result.forEach(booking -> {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("bookingNo", booking.getBookingNo());
                        map.put("bookingDate", booking.getBookingDate());
                        map.put("bookingId", booking.getId()+"");
                        data.add(map);
                    });
                    String[] from = {"bookingNo", "bookingDate"};
                    int[] to = {R.id.tvBookingNo, R.id.tvBookingDate};
                    adapter = new SimpleAdapter(
                            BookingsActivity.this,
                            data,
                            R.layout.bookings_layout,
                            from, to
                    );
                    lvBookings.setAdapter(adapter);
                    bookingsList = result;

                    lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d("nate", "You clicked number " + i);
                            Log.d("nate", "l is " + l);
                            HashMap<String, String> getMap = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                            int id = Integer.parseInt(getMap.get("bookingId"));
                            Booking booking = bookingsList.stream()
                                    .filter(b -> b.getId() == id)
                                    .findFirst().get();

                            BookingDetails[] getDetails = booking.getBookingDetails();

                            Intent intent = new Intent(getApplicationContext(), BookingDetailActivity.class);
                            intent.putExtra("booking", booking);
                            startActivity(intent);
                        }
                    });
                },BookingsActivity.this
        ).start();
    }
}