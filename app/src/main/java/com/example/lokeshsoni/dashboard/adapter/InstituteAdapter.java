package com.example.lokeshsoni.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lokeshsoni.dashboard.R;
import com.example.lokeshsoni.dashboard.modal.Institute;

import java.util.List;

/**
 * Created by lokeshsoni on 31/03/18.
 */

public class InstituteAdapter extends RecyclerView.Adapter<InstituteAdapter.MyViewHolder> {
    private Context context;
    private List<Institute> instituteList;


    @Override
    public InstituteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_institute, parent, false);

        return new InstituteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InstituteAdapter.MyViewHolder holder, int position) {
        Institute institute  = instituteList.get(position);
        holder.instituteName.setText(institute.getName());
        holder.instituteEmail.setText(institute.getEmail());

    }

    @Override
    public int getItemCount() {
        return instituteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView instituteName,instituteEmail;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            instituteName = itemView.findViewById(R.id.instituteName);
            instituteEmail = itemView.findViewById(R.id.instituteEmail);

        }

    }

    public InstituteAdapter(Context context, List<Institute> instituteList) {
        this.context = context;
        this.instituteList = instituteList;
    }
}
