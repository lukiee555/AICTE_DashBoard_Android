package com.example.lokeshsoni.dashboard.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lokeshsoni.dashboard.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddInitiativesActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText editTextName, editTextDesc;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_initiatives);

        submitButton = findViewById(R.id.submit_name);
        editTextName = findViewById(R.id.input_name);
        editTextDesc = findViewById(R.id.input_desc);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    createInitiative(editTextName.getText().toString(),editTextDesc.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                editTextName.setText("");
                editTextDesc.setText("");
                // Hiding KeyBoard
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }



    public  void createInitiative(final String name , final String desc) throws JSONException {

        showpDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://aicte.herokuapp.com/initiative";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ID = jsonObject.getString("id");
                            Intent intent = new Intent(AddInitiativesActivity.this,AddParametersActivity.class);
                            intent.putExtra("InitiativeName", name);
                            intent.putExtra("ID",ID);
                            intent.putExtra("DESC",desc);
                            startActivity(intent);
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
                        Toast.makeText(AddInitiativesActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("owner", "1");
                params.put("name", name);
                params.put("desc", desc);
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
