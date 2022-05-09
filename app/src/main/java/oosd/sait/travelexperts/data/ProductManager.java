package oosd.sait.travelexperts.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @deprecated Replaced by {@link ProductResource}. This is kept for reference only and should not
 * be used
 * @author Nate Penner
 * */
public class ProductManager extends DataSource<Product, Integer> {

    public ProductManager(Context context) {
        super(context);
    }

    @Override
    public int insert(Product data) {
        ContentValues values = new ContentValues();
        values.put("ProdName", data.getProductName());

        return (int)db.insert("Products", null, values);
    }

    @Override
    public Product getById(Integer id) {
        String sql = "SELECT * FROM Products WHERE ProductId=?";
        String[] args = { id+"" };
        Cursor cursor = db.rawQuery(sql, args);
        cursor.moveToNext();
        return new Product(cursor.getInt(0), cursor.getString(1));
    }

    @Override
    public int update(Product data) {
        String[] args = { data.getProductId()+"" };
        ContentValues values = new ContentValues();
        values.put("ProdName", data.getProductName());

        return db.update("Products", values, "ProductId=?", args);
    }

    @Override
    public int delete(Product data) {
        String[] args = { data.getProductId() + "" };

        return db.delete("Products", "ProductId=?", args);
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Product> getList() {
        List<Product> productsList = new ArrayList<>();

        String[] cols = {"ProductId", "ProdName"};
        Cursor cursor = db.query("Products", cols, null, null, null, null, null);

        while (cursor.moveToNext()) {
            productsList.add(new Product(
               cursor.getInt(0), cursor.getString(1)
            ));
        }
        return productsList;
    }
}
