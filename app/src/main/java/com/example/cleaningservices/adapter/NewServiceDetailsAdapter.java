package com.example.cleaningservices.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleaningservices.R;
import com.example.cleaningservices.model.ServiceDetails;
import com.example.cleaningservices.user_screens.BottomSheet;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewServiceDetailsAdapter extends RecyclerView.Adapter<NewServiceDetailsAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<ServiceDetails> arrServiceDetails;
    int count = 0;

    public NewServiceDetailsAdapter(Activity activity, ArrayList<ServiceDetails> arrServiceDetails) {
        this.activity = activity;
        this.arrServiceDetails = arrServiceDetails;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        TextView tvServiceDetailsName = itemView.findViewById(R.id.tvServiceDetailsName);
        CardView cardView = itemView.findViewById(R.id.card);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.new_service_service_details_item, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Button button = activity.findViewById(R.id.button);
        holder.tvServiceDetailsName.setText(arrServiceDetails.get(position).getService());
        BottomAppBar bottomNavigationView = activity.findViewById(R.id.bottomAppBar);
        FloatingActionButton floatingActionButton = activity.findViewById(R.id.fab);

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (holder.cardView.getCardBackgroundColor().getDefaultColor()
                        == activity.getResources().getColor(R.color.selctedItem)) {
                    holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                        count--;
                        if (count == 0){
                            button.setVisibility(View.GONE);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            floatingActionButton.setVisibility(View.VISIBLE);
                        }

                } else {
                    count++;
                    holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.selctedItem));
                    if (count != 0 ){
                        button.setVisibility(View.VISIBLE);
                        bottomNavigationView.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.GONE);
                    }


                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new BottomSheet(activity);
                bottomSheet.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrServiceDetails.size();
    }

}
