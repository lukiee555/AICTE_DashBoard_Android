package com.example.lokeshsoni.dashboard.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.adapter.RatingAdapter;
import com.example.lokeshsoni.dashboard.modal.Beneficiary;
import com.example.lokeshsoni.dashboard.modal.Rating;
import com.example.lokeshsoni.dashboard.modal.Reviews;
import com.example.lokeshsoni.dashboard.services.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class InitiativeDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RatingAdapter adapter;
    List<Rating> ratingList = new ArrayList<>();
    String initiativeId;
    TextView textViewDesc, textViewTitle;
    List<Reviews> reviewsList = new ArrayList<>();
    Button uploadExcelButton;
    Button viewRatings ;
    Button viewBeneficiaries, viewInstitutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiative_details);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDesc = findViewById(R.id.textViewDesc);
        recyclerView = findViewById(R.id.recyclerView);
        uploadExcelButton = findViewById(R.id.uploadExcelButton);
        viewBeneficiaries = findViewById(R.id.viewBeneficiaries);
        viewInstitutes = findViewById(R.id.viewInstitutes);
        viewRatings = findViewById(R.id.viewRating);
        Bundle bundle = getIntent().getExtras();
        textViewDesc.setText(bundle.getString("desc"));
        textViewTitle.setText(bundle.getString("title"));
        initiativeId= bundle.getString("id");
        adapter = new RatingAdapter(ratingList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);




        new GetRatingList().execute();

        uploadExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadExcel().execute();
            }
        });
        viewRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitiativeDetailsActivity.this,AllRatingActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)reviewsList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        viewBeneficiaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BeneficiaryListActivity.class);
                intent.putExtra("initiativeId",initiativeId);
                startActivity(intent);
            }
        });
        viewInstitutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InstituteListActivity.class);
                intent.putExtra("initiativeId",initiativeId);
                startActivity(intent);
            }
        });
    }
    public class UploadExcel extends AsyncTask{
        String response;
        int code;
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
                dialog = new ProgressDialog(InitiativeDetailsActivity.this);
                dialog.setMessage("Please wait...");
                dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            RestClient client = new RestClient("https://aicte.herokuapp.com/initiative/"+initiativeId+"/batch",getApplicationContext());
            System.out.println("https://aicte.herokuapp.com/initiative/"+initiativeId+"/batch");
            client.addMultiParamImage("excelFile",new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/file.xlsx"));
            try {
                client.executePostMultipart();
                response = client.getResponse();
                code = client.getCode();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return response;
        }

        @Override
        protected void onPostExecute(Object o) {
            dialog.dismiss();
            if(code==200){
                Log.d("Response",response);
                ratingList.clear();
                reviewsList.clear();
                new GetRatingList().execute();

            }else{
                Toast.makeText(InitiativeDetailsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("Error Code", String.valueOf(code));
            }
            super.onPostExecute(o);
        }
    }
    public class GetRatingList extends AsyncTask{

        String response;
        int code;
        ProgressDialog dialog;
        RestClient client;
        @Override
        protected void onPreExecute() {
            System.out.print("dsfd");
            dialog = new ProgressDialog(InitiativeDetailsActivity.this);
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
            client = new RestClient("https://aicte.herokuapp.com/initiative/"+initiativeId+"/ratings",getApplicationContext());

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
                    System.out.println(response);
                    JSONObject object = new JSONObject(response);
                    JSONObject summary = object.getJSONObject("summary");
                    JSONArray reviews = object.getJSONArray("ratings");
                    Log.d("Array",reviews.toString());
                    for(int i =0; i < reviews.length();i++){
                        JSONObject jsonObject = reviews.getJSONObject(i);

                        JSONArray parameters = jsonObject.getJSONArray("parameters");
                        HashMap<String,String> params = new HashMap<>();
                        System.out.println("dfgdfg"+parameters.length());
                       for(int j = 0; j<parameters.length();j++){
                            params.put(parameters.getJSONObject(j).getString("name"),parameters.getJSONObject(j).getString("value"));
                       }
                        JSONObject benfecry = jsonObject.getJSONObject("beneficiary");
                        Beneficiary beneficiary = new Beneficiary(benfecry.getString("id"),benfecry.getString("name"),benfecry.getString("email"),benfecry.getString("initiative"));
                        reviewsList.add(new Reviews(jsonObject.getString("id"),jsonObject.getString("initiative"),params,beneficiary));


                    }
                    Iterator<String> iter = summary.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        String value = summary.getString(key);
                        Rating rating = new Rating(key, Double.parseDouble(value));
                        ratingList.add(rating);

                    }

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
