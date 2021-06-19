package com.example.cleaningservices.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cleaningservices.R;
import com.example.cleaningservices.model.ServiceDetails;
import com.facebook.drawee.view.SimpleDraweeView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.grpc.ServiceDescriptor;

public class ServiceDetailsAdapter extends RecyclerView.Adapter<ServiceDetailsAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<ServiceDetails> arrServiceDetails;

    public ServiceDetailsAdapter(Activity activity, ArrayList<ServiceDetails> arrServiceDetails) {
        this.activity = activity;
        this.arrServiceDetails = arrServiceDetails;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        TextView tvServiceDetailsName = itemView.findViewById(R.id.tvServiceDetailsName);
        SimpleDraweeView ivServiceDetailsImage = itemView.findViewById(R.id.ivServiceDetailsImage);

    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.service_details_item, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.tvServiceDetailsName.setText(arrServiceDetails.get(position).getService());
        holder.ivServiceDetailsImage.setImageURI(Uri.parse(arrServiceDetails.get(position).getImageResource()));
    }

    @Override
    public int getItemCount() {
        return arrServiceDetails.size();
    }

}
