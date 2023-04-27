package com.example.platforma_ctf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonActivity extends AppCompatActivity {

    private static final String TAG = "JsonActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        textView = findViewById(R.id.text_view);

        // Call the async task to fetch JSON data from the internet
        new FetchJsonDataTask().execute();
    }

    private class FetchJsonDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonString = null;

            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonString = buffer.toString();

            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            return jsonString;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            if (jsonString == null) {
                return;
            }

            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String body = jsonObject.getString("body");

                    builder.append(title).append("\n\n");
                    builder.append(body).append("\n\n");
                }

                textView.setText(builder.toString());

            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON data", e);
            }
        }
    }
}
