package oosd.sait.travelexperts.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

/**
 * <h1>DataResource interface</h1>
 *
 * <p>An interface for performing CRUD operations on a database or API endpoint</p>
 * @author Nate Penner
 * */
public interface DataResource<T, IDT> {
    /**
     * Performs an insert operation
     * @param data Data of type T to be inserted
     * @return The number of records affected
     * */
    int insert(T data);

    /**
     * Retrieves a record by its ID
     * @param id The id of the object to find, which is of type IDT
     * @return The data of type T
     * */
    T getById(IDT id);

    /**
     * Performs an update operation
     * @param data The data used to update the record, of type T
     * @return The number of records affected
     * */
    int update(T data);

    /**
     * Performs a delete operation
     * @param data The data to be deleted of type T
     * @return The number of records affected
     * */
    int delete(T data);

    /**
     * Deletes a record based on its ID
     * @param id The id of type IDT to be deleted
     * @return The number of records affected
     * */
    int deleteById(IDT id);

    /**
     * Performs a read operation, retrieving all objects of type T
     * @return A collection of data of type T
     * */
    Collection<T> getList();
}