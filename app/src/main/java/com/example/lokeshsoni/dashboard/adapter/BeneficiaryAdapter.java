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
import com.example.lokeshsoni.dashboard.modal.Beneficiary;
import com.example.lokeshsoni.dashboard.modal.Reviews;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lokeshsoni on 31/03/18.
 */

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.MyViewHolder> {
    private Context context;
    private List<Beneficiary> beneficiaryList;


    @Override
    public BeneficiaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_beneficiary, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BeneficiaryAdapter.MyViewHolder holder, int position) {
        Beneficiary beneficiary  = beneficiaryList.get(position);
        holder.beneficiaryName.setText(beneficiary.getName());
        holder.beneficiaryEmail.setText(beneficiary.getEmail());
        System.out.println("college: "+beneficiary.getInstitute());
        holder.beneficiaryInstitute.setText(beneficiary.getInstitute());

    }

    @Override
    public int getItemCount() {
        return beneficiaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView beneficiaryName,beneficiaryEmail,beneficiaryInstitute;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            beneficiaryName = itemView.findViewById(R.id.beneficiaryName);
            beneficiaryEmail = itemView.findViewById(R.id.beneficiaryEmail);
            beneficiaryInstitute = itemView.findViewById(R.id.beneficiaryCollege);

        }

    }

    public BeneficiaryAdapter(Context context, List<Beneficiary> beneficiaryList) {
        this.context = context;
        this.beneficiaryList = beneficiaryList;
    }
}
