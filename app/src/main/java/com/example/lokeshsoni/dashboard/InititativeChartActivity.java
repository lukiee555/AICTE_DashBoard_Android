package com.example.lokeshsoni.dashboard;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lokeshsoni.dashboard.activities.BeneficiaryListActivity;
import com.example.lokeshsoni.dashboard.modal.Beneficiary;
import com.example.lokeshsoni.dashboard.services.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class InititativeChartActivity extends AppCompatActivity {

    BarChart barChart;
    String initiativeId;
    List<BarChartModel> barChartModelListQty = new ArrayList<>();
    List<BarChartModel> barChartModelListQly = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inititative_chart);
         barChart = findViewById(R.id.bar_chart_vertical);
         initiativeId = "1";
        new GetReport().execute();

    }
    public class GetReport extends AsyncTask {

        String response;
        int code;
        ProgressDialog dialog;
        RestClient client;
        @Override
        protected void onPreExecute() {
            System.out.print("dsfd");
            dialog = new ProgressDialog(InititativeChartActivity.this);
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
            client = new RestClient("https://aicte.herokuapp.com/initiative/"+initiativeId+"/report",getApplicationContext());

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
                    JSONObject object = new JSONObject(response);
                    int standard = object.getInt("standard");
                    JSONArray jsonArray = object.getJSONArray("institutes");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        BarChartModel barChartModel = new BarChartModel();
                        barChartModel.setBarValue(jsonObject.getInt("qualityNum"));
                        int qualityIndicator = jsonObject.getInt("qualityIndicator");
                        if(qualityIndicator==0)barChartModel.setBarColor(getResources().getColor(R.color.red_400));
                        if(qualityIndicator==1)barChartModel.setBarColor(getResources().getColor(R.color.orange_400));
                        if(qualityIndicator==2)barChartModel.setBarColor(getResources().getColor(R.color.green_400));
                        barChartModel.setBarTag(jsonObject.getString("name"));
                        barChartModel.setBarText(qualityIndicator+"");
                       barChartModelListQly.add(barChartModel);

                        BarChartModel barChartModelQny = new BarChartModel();
                        barChartModelQny.setBarValue(jsonObject.getInt("quantityNum"));
                        int quantityIndicator = jsonObject.getInt("quantityIndicator");
                        if(quantityIndicator==0)barChartModelQny.setBarColor(getResources().getColor(R.color.red_400));
                        if(quantityIndicator==1)barChartModelQny.setBarColor(getResources().getColor(R.color.orange_400));
                        if(quantityIndicator==2)barChartModelQny.setBarColor(getResources().getColor(R.color.green_400));
                        barChartModelQny.setBarTag(jsonObject.getString("name"));
                        barChartModelQny.setBarText(quantityIndicator+"");
                        barChartModelListQty.add(barChartModelQny);
                    }
                    barChart.setBarMaxValue(standard);
                    barChart.addBar(barChartModelListQty);
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
