package oosd.sait.travelexperts.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * APIRequest simplifies the process of making an HTTP request to an API endpoint, allowing you
 * to easily send and receive string data
 * @author Nate Penner
 * */
public class APIRequest {
    private final String endPoint;    // The endpoint (without a trailing /)
    private final String path;        // The route to the API endpoint
    private final String method;      // The HTTP request method

    // The mime types for the request and response data (eg. text/plain, application/json)
    private final String requestMimeType, responseMimeType;

    // Constructor
    public APIRequest(String endPoint, String path, String method, String requestMimeType, String responseMimeType) {
        this.endPoint = endPoint;
        this.path = path;
        this.method = method;
        this.requestMimeType = requestMimeType;
        this.responseMimeType = responseMimeType;
    }

    /**
     * Send the request
     * @return The response, as a string (accepted as responseMimeType)
     * */
    public String send() {
        return send(null);
    }

    /**
     * Send the request, and some data along with it
     * @param data The data to send, as a string. This data should be of type requestMimeType
     * @return The string response (accepted as responseMimeType), or null if an error occurs, or null
     * if the response code is anything other than 200 OK
     * */
    public String send(String data) {
        String response = null;
        try {
            URL url = new URL(endPoint+path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", responseMimeType);

            if (data != null) {
                conn.setRequestProperty("Content-Type", requestMimeType);

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(conn.getOutputStream())
                );

                writer.write(data);
                writer.flush();
                writer.close();
            }

            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null)
                    buffer.append(line);

                reader.close();
                response = buffer.toString();
            }
        } catch (MalformedURLException e) {
            Log.d("nate6", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("nate6", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("nate6", e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
}
