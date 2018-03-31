package com.example.lokeshsoni.dashboard.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.adapter.InstituteAdapter;
import com.example.lokeshsoni.dashboard.modal.Institute;
import com.example.lokeshsoni.dashboard.services.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstituteListActivity extends AppCompatActivity {

    String initiativeId;
    RecyclerView instituteRecycler;
    InstituteAdapter adapter;
    List<Institute> instituteList= new ArrayList<>();
    TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_list);
        initiativeId = getIntent().getExtras().getString("initiativeId");
        instituteRecycler = findViewById(R.id.instituteRecycler);
        adapter = new InstituteAdapter(getApplicationContext(),instituteList);
        count = findViewById(R.id.count);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        instituteRecycler.setLayoutManager(mLayoutManager);
        instituteRecycler.setItemAnimator(new DefaultItemAnimator());
        instituteRecycler.setAdapter(adapter);
        new InstituteListActivity.GetInstituteList().execute();

    }
    public class GetInstituteList extends AsyncTask {

        String response;
        int code;
        ProgressDialog dialog;
        RestClient client;
        @Override
        protected void onPreExecute() {
            System.out.print("dsfd");
            dialog = new ProgressDialog(InstituteListActivity.this);
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
            client = new RestClient("https://aicte.herokuapp.com/initiative/"+initiativeId+"/institutes",getApplicationContext());

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
                        Institute institute = new Institute(jsonObject.getString("name"),jsonObject.getString("email"));
                        instituteList.add(institute);
                    }
                    count.setText("Total: "+instituteList.size());
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
