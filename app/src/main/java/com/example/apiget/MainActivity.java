package com.example.apiget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiget.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @NonNull ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = "https://uatezone.octimsbd.com/api/company";

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Add the title row
        TableRow titleRow = new TableRow(MainActivity.this);
        titleRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        // Add a single cell with the title text
        TextView titleTextView = new TextView(MainActivity.this);
        titleTextView.setText("All Company Information");
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 4f));
        titleRow.addView(titleTextView);

        // Add the title row to the table
        tableLayout.addView(titleRow);

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    // Add table headers if needed
                    TableRow headerRow = new TableRow(MainActivity.this);
                    headerRow.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    // Add header cells
                    addTableCell(headerRow, "Code");
                    addTableCell(headerRow, "Name");

                    // Add header row to the table
                    tableLayout.addView(headerRow);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String code = jsonObject.getString("Code");
                        String name = jsonObject.getString("Name");

                        // Create a new row for each item
                        TableRow row = new TableRow(MainActivity.this);
                        row.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));

                        // Add cells to the row
                        addTableCell(row, code);
                        addTableCell(row, name);

                        // Add the row to the table
                        tableLayout.addView(row);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void addTableCell(TableRow row, String text) {
        TextView textView = new TextView(MainActivity.this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(textView);
    }
}