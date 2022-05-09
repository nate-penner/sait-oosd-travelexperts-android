package oosd.sait.travelexperts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

/**
 * @deprecated This is still used in this code, but is marked for deprecation and should not be used
 * going forward. Use {@link DataResource} instead.
 * @author Nate Penner
 * */
public abstract class DataSource<T, IDT> {
    private Context context;
    protected SQLiteDatabase db;
    private DBHelper helper;

    public DataSource(Context context) {
        this.context = context;

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public abstract int insert(T data);
    public abstract T getById(IDT id);
    public abstract int update(T data);
    public abstract int delete(T data);
    public abstract int deleteById(IDT id);
    public abstract Collection<T> getList();
}
