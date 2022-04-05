package oosd.sait.travelexperts.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

public class ProductManager extends DataSource<Product, Integer> {

    public ProductManager(Context context) {
        super(context);
    }

    @Override
    public int insert(Product data) {
        return 0;
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
        return 0;
    }

    @Override
    public int delete(Product data) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Collection<Product> getList() {
        return null;
    }
}
