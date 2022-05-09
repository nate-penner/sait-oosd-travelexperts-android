package oosd.sait.travelexperts.data;

import androidx.annotation.NonNull;

/**
 * Data class representing a Canadian province
 * @author Nate Penner
 * */
public class Province {
    private String name, code;

    public Province(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
