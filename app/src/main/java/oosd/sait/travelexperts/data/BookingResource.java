package oosd.sait.travelexperts.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                int packageId = 0;
                try {
                    booking.getInt("packageId");
                } catch (Exception e) {}
                bookingsList.add(
                    new Booking(
                        booking.getInt("bookingId"),
                        Timestamp.valueOf(booking.getString("bookingDate")).toString(),
                        booking.getString("bookingNo"),
                        booking.getDouble("travelerCount"),
                        booking.getInt("customerId"),
                        packageId
                    )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
