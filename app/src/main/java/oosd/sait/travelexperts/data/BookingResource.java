package oosd.sait.travelexperts.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A data source for obtaining booking information from an API, which implements {@link DataResource}
 * (see for documentation of interface methods)
 * @see Booking for information about the type of data used by this resource
 * @apiNote Only methods used so far in this project are implemented
 * @author Nate Penner
 * */
public class BookingResource implements DataResource<Booking, Integer> {
    @Override
    public int insert(Booking data) {
        APIRequest request = new APIRequest(
                API.get("bookings"),
                "/create",
                "PUT",
                "application/json",
                "application/json"
        );
        return 0;
    }

    @Override
    public Booking getById(Integer id) {
        APIRequest request = new APIRequest(
                API.get("bookings"),
                "/get/"+id,
                "GET",
                "application/json",
                "application/json"
        );
        return null;
    }

    /**
     * Gets a list of bookings from the API for a customer by ID
     * @param customerId The ID of the customer to query
     * @return A collection of bookings
     * */
    public Collection<Booking> getBookingsByCustomer(int customerId) {
        List<Booking> bookingsList = new ArrayList<>();
        APIRequest request = new APIRequest(
                API.get("bookings"),
                "/get/by-customer/"+customerId,
                "GET",
                "application/json",
                "application/json"
        );

        String data = request.send();

        try {
            JSONArray bookingsData = new JSONArray(data);

            for (int i = 0; i < bookingsData.length(); i++) {
                JSONObject booking = bookingsData.getJSONObject(i);
                JSONArray details = booking.getJSONArray("details");
                BookingDetails[] bookingDetails = new BookingDetails[details.length()];

                for (int j = 0; j < details.length(); j++) {
                    JSONObject detailsObj = details.getJSONObject(j);
                    bookingDetails[j] = new BookingDetails(
                            Timestamp.valueOf(detailsObj.getString("tripStart")).toString(),
                            Timestamp.valueOf(detailsObj.getString("tripEnd")).toString(),
                            detailsObj.getString("description"),
                            detailsObj.getString("destination"),
                            detailsObj.getDouble("basePrice")
                    );
                }

                bookingsList.add(
                    new Booking(
                        booking.getInt("bookingId"),
                        Timestamp.valueOf(booking.getString("bookingDate")).toString(),
                        booking.getString("bookingNo"),
                        booking.getDouble("travelerCount"),
                        bookingDetails
                    )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("nate", "something went terribly wrong: " + e.getMessage());
        }

        return bookingsList;
    }

    @Override
    public int update(Booking data) {
        APIRequest request = new APIRequest(
                API.get("bookings"),
                "/update",
                "POST",
                "application/json",
                "application/json"
        );


        return 0;
    }

    @Override
    public int delete(Booking data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        APIRequest request = new APIRequest(
                API.get("bookings"),
                "/delete/"+id,
                "DELETE",
                "application/json",
                "application/json"
        );
        return 0;
    }

    @Override
    public Collection<Booking> getList() {
        APIRequest request = new APIRequest(
                API.get("bookings"),
                "/list",
                "GET",
                "application/json",
                "application/json"
        );
        return null;
    }
}
