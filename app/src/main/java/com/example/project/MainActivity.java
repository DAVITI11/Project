package com.example.project;

import static android.app.PendingIntent.getActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
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
    public void AddUserInfo(String firstname, String lastname, String email, String address){
        new Thread(() -> {
            try {
                URL url = new URL("http:10.96.161.72:8080/add_userinfo");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data = "firstname=" + firstname + "&lastname=" + lastname + "&email=" + email + "&address=" + address;

                conn.getOutputStream().write(data.getBytes());

                int response = conn.getResponseCode();
                Log.d("SERVER", "Response code: " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void AddCarInfo(String carType, String model, String year,
                           String price, String description, String base64Image) {

        new Thread(() -> {
            try {
                URL url = new URL("http://10.96.161.72:8080/add_car");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Build POST body
                String data =
                        "car_type=" + carType +
                                "&model=" + model +
                                "&year=" + year +
                                "&price=" + price +
                                "&description=" + description +
                                "&image=" + base64Image;

                conn.getOutputStream().write(data.getBytes());

                int response = conn.getResponseCode();
                Log.d("SERVER", "Car Added Response: " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void getCarInfo(CarCallback callback) {

        new Thread(() -> {
            ArrayList<Car> tempList = new ArrayList<>();

            try {
                URL url = new URL("http://10.0.2.2:8080/get_carinfo");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                JSONArray arr = new JSONArray(result.toString());

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String carType = obj.getString("car_type");
                    String model = obj.getString("model");
                    String year = obj.getString("year");
                    String price = obj.getString("price");
                    String desc = obj.getString("description");

                    Bitmap bitmap = null;
                    if (!obj.isNull("image")) {
                        String base64Image = obj.optString("image", "");
                        if (!base64Image.isEmpty()) {
                            // Remove possible prefix like "data:image/png;base64,"
                            if (base64Image.contains(",")) {
                                base64Image = base64Image.split(",")[1];
                            }
                            try {
                                byte[] bytes = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
                                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            } catch (Exception e) {
                                e.printStackTrace();
                                bitmap = null;
                            }
                        }
                    }


                    tempList.add(new Car(carType, model, year, price, desc, bitmap));
                }

                runOnUiThread(() -> callback.onCarListLoaded(tempList));

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