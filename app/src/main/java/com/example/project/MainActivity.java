package com.example.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    ArrayList<Pair<String,String>> NamePass = new ArrayList<>();
    Handler handler = new Handler();
    Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("isLoggedIn", false);

        if (loggedIn) {
            ChangeFragment(new ClientFrmg()); // Go directly to home
        } else {
            ChangeFragment(new LoginFrm()); // Show login screen
        }

        startAutoRefresh();  // moved here
    }

    public void ChangeFragment(Fragment fm) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cont, fm)
                .addToBackStack(null)
                .commit();
    }
    private String httpGet(String urlString) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
        } catch (Exception e) {
            Log.e("HTTP_ERROR", e.toString());
        }

        return result.toString();
    }

    private void loadUsersFromServer() {
        new Thread(() -> {

            String json = httpGet("http://10.96.161.72:8080/users");


            try {
                JSONArray arr = new JSONArray(json);
                ArrayList<Pair<String,String>> tempList = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String name = obj.getString("password");
                    String password = obj.getString("name");

                    tempList.add(new Pair<>(name, password));
                }

                handler.post(() -> {
                    NamePass.clear();
                    NamePass.addAll(tempList);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
    public void addUserToServer(String name, String password) {
        new Thread(() -> {
            try {
                URL url = new URL("http:10.96.161.72:8080/add_user");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data = "name=" + name + "&password=" + password;

                conn.getOutputStream().write(data.getBytes());

                int response = conn.getResponseCode();
                Log.d("SERVER", "Response code: " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startAutoRefresh() {
        refreshRunnable = () -> {
            loadUsersFromServer();
            handler.postDelayed(refreshRunnable, 3000);
        };
        handler.post(refreshRunnable);
    }

    public boolean CheckUser(String name, String pass) {
        for (Pair<String, String> p : NamePass) {
            Log.d("check", p.first + " " + p.second);
            if (p.first.equals(name) && p.second.equals(pass)) {
                return true;
            }
        }
        return false;
    }

}