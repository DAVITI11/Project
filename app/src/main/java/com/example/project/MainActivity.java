package com.example.project;

import android.os.Bundle;
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

    ArrayList<Pair<String,String>> NamePass;

    FrameLayout cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cont = findViewById(R.id.cont);
        NamePass = new ArrayList<>();
      //  loadUsersFromServer();
        // ChangeFragment(new LoginFrm());
        ChangeFragment(new ClientFrmg());
    }

    public void ChangeFragment(Fragment fm) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cont, fm)
                .commit();
    }

    public void loadUsersFromServer() {

        new Thread(() -> {
            try {
                // Fetch JSON
                String json = httpGet("http://10.96.161.72:8080/users");

                JSONArray arr = new JSONArray(json);

                // Clear old list
                NamePass.clear();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String pass = obj.getString("password");
                    String name = obj.getString("name");

                    NamePass.add(new Pair<>(name, pass));

                    Log.d("JSON", "User: " + name + " Password: " + pass);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



        }).start();
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

    public boolean CheckUser(String name, String pass) {

        for (Pair<String, String> p : NamePass) {
            if (p.first.equals(name) && p.second.equals(pass)) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Pair<String,String>> GetAllInfo(){
        return NamePass;
    }
}
