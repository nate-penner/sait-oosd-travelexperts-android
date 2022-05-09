package oosd.sait.travelexperts.data;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @deprecated This code is kept for future reference only. Replaced by {@link CustomerResource}
 * @author Nate Penner
 * */
public class CustomerManager extends DataSource<Customer, Integer> {

    public CustomerManager(Context context) {
        super(context);
    }

    @Override
    public int insert(Customer data) {
        return 0;
    }

    @Override
    public Customer getById(Integer id) {
        return null;
    }

    @Override
    public int update(Customer data) {
        return 0;
    }

    @Override
    public int delete(Customer data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Customer> getList() {
        List<Customer> customerList = new ArrayList<>();

        String[] cols = {
                "CustomerId", "CustFirstName", "CustLastName", "CustAddress", "CustCity", "CustProv",
                "CustPostal", "CustCountry", "CustHomePhone", "CustBusPhone", "CustEmail", "AgentId"
        };

        Cursor cursor = db.query("Customers", cols, null, null, null, null, null);

        while (cursor.moveToNext()) {
            customerList.add(
                    new Customer(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9),
                            cursor.getString(10),
                            cursor.getInt(11)
                    )
            );
        }

        return customerList;
    }
}
