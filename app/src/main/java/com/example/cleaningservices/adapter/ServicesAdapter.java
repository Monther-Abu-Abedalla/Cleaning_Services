package com.example.cleaningservices.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<ServiceType> arrServices;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int checkPosition = -1;
    public ServicesAdapter(Activity activity, ArrayList<ServiceType> arrServices) {
        this.activity = activity;
        this.arrServices = arrServices;
        notifyDataSetChanged();
    }
    public void SetServices(ArrayList<ServiceType> arrServices){
        this.arrServices = new ArrayList<>();
        this.arrServices = arrServices;
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        TextView tvServiceType = itemView.findViewById(R.id.tvServiceType);
        SimpleDraweeView ivServiceTypeImage = itemView.findViewById(R.id.ivServiceTypeImage);
        CardView cvServiceTypeCard = itemView.findViewById(R.id.newcvServiceTypeCard);
        void bind(final ServiceType serviceType , ServicesAdapter.MyViewHolder holder ){
            Log.e("tes","bind");
            if (checkPosition == -1 ){
                Log.e("tes","if");
                cvServiceTypeCard.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                holder.tvServiceType.setTextColor(Color.WHITE);
            }
            if (checkPosition == getAdapterPosition()){
                Log.e("tes","if if");
                cvServiceTypeCard.setCardBackgroundColor(activity.getResources().getColor(R.color.primary_color));
                holder.tvServiceType.setTextColor(Color.WHITE);
            }else{
                Log.e("tes","if else");
                cvServiceTypeCard.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                holder.tvServiceType.setTextColor(activity.getResources().getColor(R.color.black));
            }

            cvServiceTypeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               TextView tvMyService = activity.findViewById(R.id.textView10);
                TextView tvShowAll = activity.findViewById(R.id.textView11);
                holder.cvServiceTypeCard.setCardBackgroundColor(activity.getResources().getColor(R.color.primary_color));
                holder.tvServiceType.setTextColor(Color.WHITE);
                    if(checkPosition != getAdapterPosition()){
                        notifyItemChanged(checkPosition);
                        checkPosition = getAdapterPosition();
                        db.collection("services_details").whereEqualTo("serviceId", arrServices.get(getAdapterPosition()).getId()).get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot it) {
                                        ArrayList<ServiceDetails> arrServicesDetails = new ArrayList<>();
                                        for (int i = 0; i < it.getDocuments().size(); i++) {
                                            try {
                                                arrServicesDetails.add(new ServiceDetails(it.getDocuments().get(i).get("serviceDetailsId").toString(),
                                                        it.getDocuments().get(i).get("serviceId").toString(),
                                                        it.getDocuments().get(i).get("serviceDetails").toString(),
                                                        it.getDocuments().get(i).get("imageResource").toString()));
                                            } catch (Exception e) {
                                                showSnackBar(R.string.error_occurred, R.color.red);
                                            }
                                        }
                                        if (arrServicesDetails.size() == 0) {
                                            showSnackBar(R.string.no_services_for_type, R.color.dark);
                                            tvMyService.setVisibility(View.GONE);
                                            tvShowAll.setVisibility(View.GONE);
                                        }else {
                                            tvMyService.setVisibility(View.VISIBLE);
                                            tvShowAll.setVisibility(View.VISIBLE);
                                        }
                                        ServiceDetailsAdapter adapter = new ServiceDetailsAdapter(activity, arrServicesDetails);
                                        RecyclerView rvServiceTypeDetails = activity.findViewById(R.id.rvServiceTypeDetails);
                                        rvServiceTypeDetails.setLayoutManager(new GridLayoutManager(activity, 2));
                                        rvServiceTypeDetails.setAdapter(adapter);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                showSnackBar(R.string.error_occurred, R.color.red);
                            }
                        });
                    }

            }

            });
        }
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.new_service_service_type_item, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.tvServiceType.setText(arrServices.get(position).getService());
        holder.ivServiceTypeImage.setImageURI(Uri.parse(arrServices.get(position).getImageResource()));
        holder.bind(arrServices.get(position),holder);

//        holder.cvServiceTypeCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tvMyService = activity.findViewById(R.id.textView10);
//                TextView tvShowAll = activity.findViewById(R.id.textView11);
//                holder.cvServiceTypeCard.setCardBackgroundColor(activity.getResources().getColor(R.color.primary_color));
//                holder.tvServiceType.setTextColor(Color.WHITE);
//
//                db.collection("services_details").whereEqualTo("serviceId", arrServices.get(position).getId()).get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot it) {
//                                ArrayList<ServiceDetails> arrServicesDetails = new ArrayList<>();
//                                for (int i = 0; i < it.getDocuments().size(); i++) {
//                                    try {
//                                        arrServicesDetails.add(new ServiceDetails(it.getDocuments().get(i).get("serviceDetailsId").toString(),
//                                                it.getDocuments().get(i).get("serviceId").toString(),
//                                                it.getDocuments().get(i).get("serviceDetails").toString(),
//                                                it.getDocuments().get(i).get("imageResource").toString()));
//                                    } catch (Exception e) {
//                                        showSnackBar(R.string.error_occurred, R.color.red);
//                                    }
//                                }
//                                if (arrServicesDetails.size() == 0) {
//                                    showSnackBar(R.string.no_services_for_type, R.color.dark);
//                                    tvMyService.setVisibility(View.GONE);
//                                    tvShowAll.setVisibility(View.GONE);
//                                }else {
//                                    tvMyService.setVisibility(View.VISIBLE);
//                                    tvShowAll.setVisibility(View.VISIBLE);
//                                }
//                                ServiceDetailsAdapter adapter = new ServiceDetailsAdapter(activity, arrServicesDetails);
//                                RecyclerView rvServiceTypeDetails = activity.findViewById(R.id.rvServiceTypeDetails);
//                                rvServiceTypeDetails.setLayoutManager(new GridLayoutManager(activity, 2));
//                                rvServiceTypeDetails.setAdapter(adapter);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @NotNull Exception e) {
//                        showSnackBar(R.string.error_occurred, R.color.red);
//                    }
//                });
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return arrServices.size();
    }

    private void showSnackBar(int stringResource, int colorResource) {
        View homeContainer = activity.findViewById(R.id.homeContainer);
        Snackbar snackbar = Snackbar.make(homeContainer, activity.getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(colorResource));
        snackbar.show();
    }

}
