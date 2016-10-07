package com.hotel.hotelroomreservation.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.config.Config;
import com.hotel.hotelroomreservation.model.Room;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ania on 29.09.2016.
 */
public class RoomsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_view_activity);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_URL);

        // Write data
//        String name = "Hanna";
//        Room person = new Room();
//        person.setName(name);
//
//        ref.child("Room").setValue(person);

        // Read data
        ref.child("Room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }
            @Override public void onCancelled(FirebaseError error) { }
        });


        //new HttpRequestTask().execute(new Pair<Context, String>(this, "Manfred"));
    }

    private class HttpRequestTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            context = params[0].first;
            String name = params[0].second;

            try {
                URL url = new URL("http://hotelroomsreservation.appspot.com/hello");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                // Build name data request params
                Map<String, String> nameValuePairs = new HashMap<>();
                nameValuePairs.put("name", name);
                String postParams = buildPostDataString(nameValuePairs);

                // Execute HTTP Post
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postParams);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect();

                // Read response
                int responseCode = connection.getResponseCode();
                StringBuilder response = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    return response.toString();
                }
                return "Error: " + responseCode + " " + connection.getResponseMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        private String buildPostDataString(Map<String, String> params) throws UnsupportedEncodingException, UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}
