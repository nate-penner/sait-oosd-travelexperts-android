package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collection;

import oosd.sait.travelexperts.async.AsyncRunnable;
import oosd.sait.travelexperts.data.AgentMin;
import oosd.sait.travelexperts.data.AgentMinResource;
import oosd.sait.travelexperts.data.DataResource;

/**
 * Dummy login activity - allows an agent to login with firstname.lastname as their username and a default
 * password.
 * @author Nate Penner
 * */
public class LoginActivity extends AppCompatActivity {
    // Controls
    EditText etEmail, etPassword;
    Button btnLogin;

    // Data source
    DataResource<AgentMin, Integer> agentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup UI
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Setup agent data source
        agentData = new AgentMinResource();

        // Login button handler
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Validating...", Toast.LENGTH_SHORT)
                        .show();

                new AsyncRunnable<>(
                        // Get agent data on another thread
                        () -> agentData.getList(),
                        (agents) -> {
                            // Validate user and password
                            boolean validated = validate(
                                    etEmail.getText().toString(),
                                    etPassword.getText().toString(),
                                    agents
                            );
                            Log.d("nate", "validate: " + validated);

                            AgentMin agent = null;
                            if (validated)
                                agent = agents.stream().filter(a -> a.getUsername().equals(
                                        etEmail.getText().toString()
                                ))
                                .findFirst().get();

                            // Show result of validation
                            showToast(validated, agent);
                        },
                        LoginActivity.this
                ).start();
            }
        });
    }

    // Check user and password (dummy password)
    private boolean validate(String username, String password, Collection<AgentMin> agentList) {
        boolean validUser = agentList.stream().anyMatch(p -> p.getUsername().equals(username));
        boolean validPassword = password.equals("12345");

        return validUser && validPassword;
    }

    // show messages depending on what happens. Open main activity on successful login
    private void showToast(boolean validated, AgentMin agent) {
        Toast toast;
        if (validated) {
            toast = Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("agent", agent);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, 1000);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid credentials.", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}