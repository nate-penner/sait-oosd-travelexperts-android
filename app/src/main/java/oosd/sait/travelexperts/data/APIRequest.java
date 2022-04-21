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

public class APIRequest {
    private String endPoint;
    private String path;
    private String method;
    private String requestMimeType, responseMimeType;

    public APIRequest(String endPoint, String path, String method, String requestMimeType, String responseMimeType) {
        this.endPoint = endPoint;
        this.path = path;
        this.method = method;
        this.requestMimeType = requestMimeType;
        this.responseMimeType = responseMimeType;
    }

    public String send() {
        return send(null);
    }

    public String send(String data) {
        String response = null;
        try {
            URL url = new URL(endPoint+path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMimeType() {
        return requestMimeType;
    }

    public void setRequestMimeType(String requestMimeType) {
        this.requestMimeType = requestMimeType;
    }

    public String getResponseMimeType() {
        return responseMimeType;
    }

    public void setResponseMimeType(String responseMimeType) {
        this.responseMimeType = responseMimeType;
    }
}
