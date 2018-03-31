package com.example.lokeshsoni.dashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lokeshsoni.dashboard.activities.InitiativeDetailsActivity;
import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.modal.InitiativesCardView;

import java.util.List;

/**
 * Created by hp on 27-03-2018.
 */

public class InitiativesAdapter extends RecyclerView.Adapter<InitiativesAdapter.MyViewHolder> {
    private Context context;
    private List<InitiativesCardView> initiativesCardViewsList;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view =  LayoutInflater
               .from(parent.getContext())
               .inflate(R.layout.initiative_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final InitiativesCardView initiativesCardView = initiativesCardViewsList.get(position);
        holder.initCardViewName.setText(initiativesCardView.getInit_card_view_name());

        holder.initCradViewLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, InitiativeDetailsActivity.class);
                i.putExtra("id",initiativesCardViewsList.get(position).getId());
                i.putExtra("title",initiativesCardViewsList.get(position).getInit_card_view_name());
                i.putExtra("desc",initiativesCardViewsList.get(position).getDesc());
                context.startActivity(i);
            }
        });
        Glide.with(context)
                .load(initiativesCardView.getInit_card_view_image())
                        .into(holder.initCardViewImage);



    }

    @Override
    public int getItemCount() {
        return initiativesCardViewsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView initCardViewName;
        private ImageView initCardViewImage;
        private LinearLayout initCradViewLinearLayout;



        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            initCardViewName = itemView.findViewById(R.id.init_card_view_name);
            initCardViewImage = itemView.findViewById(R.id.init_card_view_image);
            initCradViewLinearLayout = itemView.findViewById(R.id.init_card_view_linear_layout);


        }


    }
    public InitiativesAdapter(Context context, List<InitiativesCardView> initiativesCardViewsList){
        this.context = context;
        this.initiativesCardViewsList = initiativesCardViewsList;
    }
}
