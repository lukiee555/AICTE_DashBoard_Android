package com.example.lokeshsoni.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddParameters extends AppCompatActivity {

    private TextView nameTextView , parameters;
    Context context;
    private Button addParam;
    private EditText parameter;
    private ProgressDialog pDialog;
    String id = "";

    public AddParameters() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parameters);
        context = getApplicationContext();

        String name = getIntent().getStringExtra("InitiativeName");
         id = getIntent().getStringExtra("ID");


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        nameTextView = findViewById(R.id.tv_InitiativeName);
        addParam = findViewById(R.id.bt_AddParameters);
        parameter = findViewById(R.id.et_param);
        parameters = findViewById(R.id.tv_parameter);


        nameTextView.setText(name);
        addParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParameter(parameter.getText().toString());
                parameter.setText("");
            }
        });


    }

    public void addParameter(final String paramName){
        showpDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://aicte.herokuapp.com/parameter";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            parameters.append(jsonObject.getString("name") + "\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hidepDialog();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        hidepDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("initiative", id);
                params.put("name", paramName);

                return params;
            }
        };
        requestQueue.add(postRequest);


    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
