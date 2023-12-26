package com.example.apiget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = "https://uatezone.octimsbd.com/api/company";

        ArrayList<String> NameArrayList = new ArrayList<>();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i< jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Code = jsonObject.getString("Code");
                        String CompanyId = jsonObject.getString("CompanyId");
                        String ConcernId = jsonObject.getString("ConcernId");
                        String Name = jsonObject.getString("Name");
                        NameArrayList.add(Code);
                        NameArrayList.add(CompanyId);
                        NameArrayList.add(ConcernId);
                        NameArrayList.add(Name);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_expandable_list_item_1, NameArrayList);
                    binding.listview.setAdapter(arrayAdapter);

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
}
