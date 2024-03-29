package com.example.cleaningservices.adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleaningservices.R;
import com.example.cleaningservices.model.PendingServices;
import com.example.cleaningservices.model.ServiceType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CompletedPendingServicesAdapter extends RecyclerView.Adapter<CompletedPendingServicesAdapter.MyViewHolder> {

    private final Activity activity;
    private final ArrayList<PendingServices> arrCompletedPendingServices;
    private final String sendType;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CompletedPendingServicesAdapter(Activity activity, ArrayList<PendingServices> arrCompletedPendingServices, String sendType) {
        this.activity = activity;
        this.arrCompletedPendingServices = arrCompletedPendingServices;
        this.sendType = sendType;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        TextView tvTimeLikeCard = itemView.findViewById(R.id.tvTimeLikeCard);
        TextView tvRequiredServicePrice = itemView.findViewById(R.id.tvRequiredServicePrice);
        TextView tvRemindDays = itemView.findViewById(R.id.tvRemindDays);
        TextView tvTime = itemView.findViewById(R.id.tvTime);
        AppCompatRatingBar rpCompletedPendingService = itemView.findViewById(R.id.rpCompletedPendingService);
        TextView tvServiceNumber = itemView.findViewById(R.id.tvServiceNumber);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.completed_pending_service_item, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Random random = new Random();
        holder.tvServiceNumber.setText(arrCompletedPendingServices.get(position).getId().substring(0, 5));
        Long time = arrCompletedPendingServices.get(position).getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String month = String.format(Locale.US, "%tB", calendar);
        String hours = calendar.get(Calendar.HOUR_OF_DAY) + ": 00";

        holder.tvTime.setText(hours);
        holder.tvTimeLikeCard.setText(day + "\n" + month);
        Log.e("rr", day);
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("#D4A26A");
        arr.add("#3A7057");
        arr.add("#B0B0B0");

        holder.tvTimeLikeCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(
                arr.get(new Random().nextInt(arr.size())))
        ));

        if (sendType.equalsIgnoreCase("completed")) {
            holder.rpCompletedPendingService.setVisibility(View.VISIBLE);
            holder.tvRemindDays.setVisibility(View.GONE);
            holder.rpCompletedPendingService.setRating(random.nextInt(5));

        } else if (sendType.equalsIgnoreCase("pending")) {
            Calendar currentCalendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + arrCompletedPendingServices.get(position).getHours());
            Log.e("hours", "Calnder:  " + calendar.get(Calendar.HOUR_OF_DAY));
            holder.rpCompletedPendingService.setVisibility(View.GONE);
            holder.tvRemindDays.setVisibility(View.VISIBLE);
            Long remindDays =
                    TimeUnit.MILLISECONDS.toDays(Math.abs(currentCalendar.getTimeInMillis() - calendar.getTimeInMillis()));
            holder.tvRemindDays.setText(activity.getResources().getString(R.string.remind_days, remindDays));
            if (currentCalendar.after(calendar)) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("id", arrCompletedPendingServices.get(position).getId());
                hashMap.put("time", arrCompletedPendingServices.get(position).getTime());
                hashMap.put("money", arrCompletedPendingServices.get(position).getMoney());
                hashMap.put("Hours", arrCompletedPendingServices.get(position).getHours());
                db.collection("completed_services").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection("pending_services").whereEqualTo("id", arrCompletedPendingServices.get(position).getId()).get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        queryDocumentSnapshots.forEach(queryDocumentSnapshot -> {
                                                    queryDocumentSnapshot.getReference().delete();
                                                }
                                        );
                          }
                 });
                    }
                });

            }
        }
    }

    @Override
    public int getItemCount() {
        return arrCompletedPendingServices.size();
    }

    private void showSnackBar(int stringResource, int colorResource) {
        View homeContainer = activity.findViewById(R.id.homeContainer);
        Snackbar snackbar = Snackbar.make(homeContainer, activity.getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(colorResource));
        snackbar.show();
    }

}
