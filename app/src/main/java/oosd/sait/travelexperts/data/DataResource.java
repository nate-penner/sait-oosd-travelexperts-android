package oosd.sait.travelexperts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

public interface DataResource<T, IDT> {
    int insert(T data);
    T getById(IDT id);
    int update(T data);
    int delete(T data);
    int deleteById(IDT id);
    Collection<T> getList();
}