package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * Data class representing some minimal information about a travel agent
 * */
public class AgentMin implements Serializable {
    private int id;
    private String firstName, lastName;

    public AgentMin(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return MessageFormat.format("{0}.{1}", firstName.toLowerCase(), lastName.toLowerCase());
    }

    @NonNull
    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
