package com.example.cleaningservices.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleaningservices.R;
import com.example.cleaningservices.model.ServiceType;

import java.util.ArrayList;

public class ServicesTypesAdapter extends RecyclerView.Adapter<ServicesTypesAdapter.MyViewHolder> {

    private final Activity activity;
    private final ArrayList<ServiceType> arrServices;

    public ServicesTypesAdapter(Activity activity, ArrayList<ServiceType> arrServices) {
        this.activity = activity;
        this.arrServices = arrServices;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
        }
        CardView cvService = itemView.findViewById(R.id.cvServiceTypeCard);
        ImageView ivServiceTypeImage = itemView.findViewById(R.id.ivServiceTypeImage);
        TextView tvServiceType = itemView.findViewById(R.id.tvServiceType);
        View cardBackground = itemView.findViewById(R.id.cardBackground);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.service_type_item, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ivServiceTypeImage.setImageResource(arrServices.get(position).getImageResource());
        holder.tvServiceType.setText(arrServices.get(position).getService());
        if (position == 0) {
            holder.tvServiceType.setTextColor(activity.getResources().getColor(R.color.white));
            holder.cardBackground.setBackground(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.service_type_card_background, null));
        }
        else {
            holder.tvServiceType.setTextColor(activity.getResources().getColor(R.color.black));
            holder.cardBackground.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }

        holder.cvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvServiceType.setTextColor(activity.getResources().getColor(R.color.white));
                holder.cardBackground.setBackground(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.service_type_card_background, null));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrServices.size();
    }


}
