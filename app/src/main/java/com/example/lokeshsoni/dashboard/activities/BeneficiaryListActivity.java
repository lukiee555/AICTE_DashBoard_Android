package com.example.lokeshsoni.dashboard.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.adapter.BeneficiaryAdapter;
import com.example.lokeshsoni.dashboard.adapter.RatingAdapter;
import com.example.lokeshsoni.dashboard.modal.Beneficiary;
import com.example.lokeshsoni.dashboard.modal.Rating;
import com.example.lokeshsoni.dashboard.modal.Reviews;
import com.example.lokeshsoni.dashboard.services.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BeneficiaryListActivity extends AppCompatActivity {

    String initiativeId;
    RecyclerView beneficiaryRecycler;
    BeneficiaryAdapter adapter;
    List<Beneficiary> beneficiaryList= new ArrayList<>();
    TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list);
        count = findViewById(R.id.count);
        initiativeId = getIntent().getExtras().getString("initiativeId");
        beneficiaryRecycler = findViewById(R.id.beneficiaryRecycler);
        adapter = new BeneficiaryAdapter(getApplicationContext(),beneficiaryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        beneficiaryRecycler.setLayoutManager(mLayoutManager);
        beneficiaryRecycler.setItemAnimator(new DefaultItemAnimator());
        beneficiaryRecycler.setAdapter(adapter);
        new GetBeneficiaryList().execute();

    }
    public class GetBeneficiaryList extends AsyncTask {

        String response;
        int code;
        ProgressDialog dialog;
        RestClient client;
        @Override
        protected void onPreExecute() {
            System.out.print("dsfd");
            dialog = new ProgressDialog(BeneficiaryListActivity.this);
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
            client = new RestClient("https://aicte.herokuapp.com/initiative/"+initiativeId+"/beneficiaries",getApplicationContext());

            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object[] params) {


            try {
                client.executeGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response = client.getResponse();
            code = client.getCode();
            return response;
        }
        @Override
        protected void onPostExecute( Object o) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(code ==200){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Beneficiary beneficiary = new Beneficiary(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("email"),"",jsonObject.getString("institute"));
                        beneficiaryList.add(beneficiary);
                    }
                    count.setText("Total: "+beneficiaryList.size());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                System.out.print(code+"");
            }
            super.onPostExecute(0);
        }
    }

}
