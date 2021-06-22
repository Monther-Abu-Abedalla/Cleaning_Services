package com.example.cleaningservices.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleaningservices.R;
import com.example.cleaningservices.model.ServiceDetails;
import com.example.cleaningservices.model.ServiceType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    public static Boolean isSelected = false;
    private List<String> mData;
    private LayoutInflater mInflater;
    int checkPosition = -1;
    Activity activity;
    int time;

    public static int finalTime;

    public TimeAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void SetTime(ArrayList<String> mData){
        this.mData = new ArrayList<>();
        this.mData = mData;
        notifyDataSetChanged();
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.time_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
        holder.bind(mData.get(position) , holder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        CardView cvServiceTypeCard;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView17);
            cvServiceTypeCard = itemView.findViewById(R.id.cardTime);
        }

        void bind(final String mData , TimeAdapter.ViewHolder holder ){
            Log.e("tes","bind");
            if (checkPosition == -1 ){
                Log.e("tes","if");
               cvServiceTypeCard.setCardBackgroundColor(Color.WHITE);
                holder.myTextView.setTextColor(Color.WHITE);
            }
            if (checkPosition == getAdapterPosition()){
                Log.e("tes","if if");
                cvServiceTypeCard.setCardBackgroundColor(Color.BLACK);
                holder.myTextView.setTextColor(Color.WHITE);
            }else{
                Log.e("tes","if else");
                cvServiceTypeCard.setCardBackgroundColor(Color.WHITE);
                holder.myTextView.setTextColor(Color.BLACK);
            }

            cvServiceTypeCard.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View v) {
                  isSelected=true;
                  Log.e("ttt",isSelected+"");
                   cvServiceTypeCard.setCardBackgroundColor(Color.parseColor("#3A7057"));
                   holder.myTextView.setTextColor(Color.WHITE);
                    if(checkPosition != getAdapterPosition()){
                        notifyItemChanged(checkPosition);
                        checkPosition = getAdapterPosition();
                    }
                   finalTime = Integer.parseInt(holder.myTextView.getText().toString()
                           .substring(0,myTextView.getText().toString().lastIndexOf(":")));

                    if(holder.myTextView.getText().toString().endsWith("PM")){
                        finalTime = Integer.parseInt(holder.myTextView.getText().toString().charAt(0)+"")+12;
                    }
                }
            });
        }
    }

    String getItem(int id) {
        return mData.get(id);
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
