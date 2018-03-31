package com.example.lokeshsoni.dashboard.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.services.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

public class LoginActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor ;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        pref = getApplicationContext().getSharedPreferences("DashBoardPref", 0); //
        editor = pref.edit();


        if(pref.contains("access-token")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }


        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        ((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {

            username= loginUser.getEditText().getText().toString();
            password = loginPass.getEditText().getText().toString();
            new LoginUser().execute();

            }
        });

        ((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {



            }
        });



    }

    public class LoginUser extends AsyncTask{
        String response;
        int code;
        @Override
        protected void onPreExecute() {
            showpDialog();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            RestClient client = new RestClient("https://aicte.herokuapp.com/api/login",getApplicationContext());
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username",username);
                jsonObject.put("password",password);
                client.executePost(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response = client.getResponse();
            code = client.getCode();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            hidepDialog();
            if(code==200){
                Log.d("response",response);
                  try {
                 JSONObject jsonObject = new JSONObject(response);
                 editor.putString("access-token",jsonObject.getString("access_token") );

                 editor.commit();

                 Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                 startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }else{
                Log.d("response",response);
                Log.d("code",code+"");
            }
            super.onPostExecute(o);
        }
    }
    public void login(String username, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        try {
            showpDialog();
            String URL = "https://aicte.herokuapp.com/api/login";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("LOG_VOLLEY", response);
                    hidepDialog();
                  //  try {
                       // JSONObject jsonObject = new JSONObject(response);
                       // editor.putString("access-token",jsonObject.getString("access_token") );
                        //editor.commit();
                       // Toast.makeText(LoginActivity.this,pref.getString("access-token", null),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));


//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("LOG_VOLLEY", error.toString());
                    hidepDialog();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
