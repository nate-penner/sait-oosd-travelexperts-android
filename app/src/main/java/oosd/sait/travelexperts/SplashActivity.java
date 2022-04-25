package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import oosd.sait.travelexperts.data.API;
import oosd.sait.travelexperts.data.DBHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        API.map("products", "http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/product");
        API.map("packages", "http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/package");
        API.map("bookings", "http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/booking");
        API.map("customers", "http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/customer");
        API.map("regions", "http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/region");
        API.map("agents", "http://10.0.2.2:8080/Group5_REST_service_war_exploded/api/agent");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("nate", "this ran");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                finish();
            }
        }, 3000);
    }
}