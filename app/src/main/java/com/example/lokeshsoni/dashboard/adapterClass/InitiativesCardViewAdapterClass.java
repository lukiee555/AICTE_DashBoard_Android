package com.example.lokeshsoni.dashboard.adapterClass;

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
import com.example.lokeshsoni.dashboard.InitiativeDescriptionClass;
import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.modal.InitiativesCardView;

import java.util.List;

/**
 * Created by hp on 27-03-2018.
 */

public class InitiativesCardViewAdapterClass extends RecyclerView.Adapter<InitiativesCardViewAdapterClass.MyViewHolder> {
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InitiativesCardView initiativesCardView = initiativesCardViewsList.get(position);
        holder.initCardViewName.setText(initiativesCardView.getInit_card_view_name());

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
            initCradViewLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, InitiativeDescriptionClass.class);
                    context.startActivity(i);
                }
            });
        }
    }
    public InitiativesCardViewAdapterClass(Context context, List<InitiativesCardView> initiativesCardViewsList){
        this.context = context;
        this.initiativesCardViewsList = initiativesCardViewsList;
    }
}
