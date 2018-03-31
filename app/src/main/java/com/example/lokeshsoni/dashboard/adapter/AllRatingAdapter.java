package com.example.lokeshsoni.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.modal.Reviews;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lokeshsoni on 30/03/18.
 */

public class AllRatingAdapter extends RecyclerView.Adapter<AllRatingAdapter.MyViewHolder> {
    private Context context;
    private List<Reviews> reviewsList;


    @Override
    public AllRatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviewscardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllRatingAdapter.MyViewHolder holder, int position) {
        Reviews reviews = reviewsList.get(position);
        holder.beneficiaryName.setText(reviews.getBeneficiary().getName());
        float avg  = 0;
        Iterator it = reviews.getParamRatings().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            avg = avg + Float.parseFloat(pair.getValue().toString());
            Log.d("Ratings Values",pair.getValue().toString());

        }
        Log.d("SIZE", String.valueOf(reviews.getParamRatings().size()));
        if(reviews.getParamRatings().size() != 0){
            avg = avg / reviews.getParamRatings().size();
            Log.d("AVG", String.valueOf(avg));
        }else {
            avg = 0;
        }
        holder.ratingBar.setRating(avg);


    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView beneficiaryName;
        private RatingBar ratingBar;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            beneficiaryName = itemView.findViewById(R.id.beneficiaryName);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }

    }

    public AllRatingAdapter(Context context, List<Reviews> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }
}
