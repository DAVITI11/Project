package com.example.project;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pair<String, String>> NamePass = new ArrayList<>();
    String pas="", usNm="";
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
            ChangeFragment(new ClientFrmg());
        } else {
            ChangeFragment(new LoginFrm());
        }
        startAutoRefresh();
    }

    public void ChangeFragment(Fragment fm) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cont, fm)
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
                ArrayList<Pair<String, String>> tempList = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String password = obj.getString("password");
                    String name = obj.getString("name");

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
                URL url = new URL("http://10.96.161.72:8080/add_user");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data = "name=" + name + "&password=" + password;

                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

                int response = conn.getResponseCode();
                Log.d("SERVER", "Response code: " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        startAutoRefresh();
    }

    public void AddUserInfo(String firstname, String lastname, String email, String phone, String username) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.96.161.72:8080/add_userinfo");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data =
                        "firstname=" + firstname +
                                "&lastname=" + lastname +
                                "&email=" + email +
                                "&phone=" + phone +
                                "&username=" + username;

                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

                int response = conn.getResponseCode();
                Log.d("SERVER", "Response code: " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void AddCarInfo(String carType, String model, String year,
                           String price, String description, String imagePath) {

        new Thread(() -> {
            try {
                URL url = new URL("http://10.96.161.72:8080/add_car");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data =
                        "car_type=" + carType +
                                "&model=" + model +
                                "&year=" + year +
                                "&price=" +  price +
                                "&description=" + description +
                                "&image=" + imagePath +
                                "&owner=" + usNm;

                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

                int response = conn.getResponseCode();
                Log.d("SERVER", "Response code: " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getCarInfo(CarCallback callback) {
        new Thread(() -> {
            ArrayList<Car> tempList = new ArrayList<>();

            try {
                URL url = new URL("http://10.96.161.72:8080/get_carinfo");
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
                    String imagePath = obj.getString("image");
                    String owner = obj.getString("owner");

                    tempList.add(new Car(carType, model, year, price, desc, imagePath, owner));
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
                pas = pass;
                usNm = name;
                Log.d("check", "true");
                return true;
            }
        }
        return false;
    }

    public boolean CheckName(String name) {
        for (Pair<String, String> p : NamePass) {
            if (p.first.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getPas() {
        return pas;
    }

    public String getUsNm() {
        return usNm;
    }

    public void GetUserInfo(UserInfoCallback callback) {

        new Thread(() -> {

            try {

                String url = "http://10.96.161.72:8080/get_userinfo?username=" + usNm;

                String json = httpGet(url);

                JSONArray arr = new JSONArray(json);

                if (arr.length() == 0) {
                    handler.post(() -> callback.onResult(null));
                    return;
                }

                JSONObject obj = arr.getJSONObject(0);

                UserInfo info = new UserInfo(
                        obj.optString("firstname", ""),
                        obj.optString("lastname", ""),
                        obj.optString("email", ""),
                        obj.optString("phone", ""),
                        obj.optString("username", "")
                );

                handler.post(() -> callback.onResult(info));

            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> callback.onResult(null));
            }

        }).start();
    }

    public void sendMessage(String sender, String receiver, String message) {

        new Thread(() -> {
            try {

                URL url = new URL("http://10.96.161.72:8080/send_message");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data =
                        null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data = "sender=" + URLEncoder.encode(sender, StandardCharsets.UTF_8) +
                            "&receiver=" + URLEncoder.encode(receiver, StandardCharsets.UTF_8) +
                            "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8) +
                            "&timestamp=" + System.currentTimeMillis();
                }

                assert data != null;
                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

                conn.getInputStream(); // force request execution

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void getMessages(ChatCallback callback) {

        new Thread(() -> {

            ArrayList<Message> list = new ArrayList<>();

            try {
                URL url = new URL("http://10.96.161.72:8080/get_messages?user=" + URLEncoder.encode(usNm, "UTF-8"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                JSONArray arr = new JSONArray(jsonBuilder.toString());

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    list.add(new Message(
                            obj.getString("sender"),
                            obj.getString("receiver"),
                            obj.getString("message"),
                            obj.getString("timestamp")
                    ));
                }

                runOnUiThread(() -> callback.onMessagesLoaded(list));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
    public interface ChatCallback {
        void onMessagesLoaded(ArrayList<Message> list);
    }
}