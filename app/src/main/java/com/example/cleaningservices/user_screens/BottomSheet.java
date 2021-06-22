package com.example.cleaningservices.user_screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.example.cleaningservices.R;
import com.example.cleaningservices.adapter.TimeAdapter;
import com.example.cleaningservices.fragments.PendingServicesFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@SuppressLint("ViewConstructor")
public class BottomSheet extends BaseBottomSheet {
    AutoCompleteTextView etChoosePlace;
    Button confirm_button_shape_bottom_sheet;
    RecyclerView timeRec;
    CalendarView calendar;
    SeekBar seekBar;
    Calendar finalCalendar = Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Activity activity;

    public BottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
        activity = hostActivity;
    }

    public BottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
        activity = hostActivity;
    }

    @NonNull
    @Override
    public final View onCreateSheetContentView(@NonNull Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.confirm_service_bottom_sheet, this, false);
        Log.e("tttt",Calendar.getInstance().getTime()+"");




        etChoosePlace = root.findViewById(R.id.etChoosePlace);
        confirm_button_shape_bottom_sheet = root.findViewById(R.id.confirm_button_shape_bottom_sheet);
        calendar = root.findViewById(R.id.calendarView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_selectable_list_item,COUNTRIES);
        etChoosePlace.setAdapter(adapter);
        confirm_button_shape_bottom_sheet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etChoosePlace.getText().toString().length() == 0 && seekBar.getProgress()== 0 ) &&
                      !TimeAdapter.isSelected){
                showSnackBar(root,R.string.fill_all_bottomSheet,R.color.dark);
                dismiss();
              }else {
                  ProgressDialog progressDialog =ProgressDialog.show(getContext(),"Loading","Please Waite");
                  progressDialog.create();
               finalCalendar.set(Calendar.HOUR_OF_DAY,TimeAdapter.finalTime);
                    HashMap<String,Object> hashMap = new HashMap<String,Object>();
                    hashMap.put("id", UUID.randomUUID().toString());
                    hashMap.put("time",finalCalendar.getTimeInMillis());
                    hashMap.put("money",seekBar.getProgress()*10/3);
                    db.collection("pending_services").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            dismiss();
                            showSnackBar(root,R.string.added_successfully,R.color.dark);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            dismiss();
                            showSnackBar(root,R.string.failed_toAdd,R.color.dark);
                        }
                    });
                }



            }
        });


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth,0,0,0);
                finalCalendar = calendar;
            }
        });



        TextView tvTime = root.findViewById(R.id.tvTime);
        TextView tvHours = root.findViewById(R.id.textView15);


        ArrayList<String> times = new ArrayList<>();
        times.add("3:00 PM");
        times.add("1:00 PM");
        times.add("11:00 AM");
        times.add("9:00 Am");

        tvTime.setText(getResources().getString(R.string.per_hour, 0));
        tvHours.setText(getResources().getString(R.string.four_ours, 0));


        timeRec = root.findViewById(R.id.time_rec);
        timeRec.setLayoutManager(new GridLayoutManager(getContext(),4));

        TimeAdapter adapter2 = new TimeAdapter(getContext(), times);
        timeRec.setAdapter(adapter2);



        calendar.setMinDate(System.currentTimeMillis() - 1000);

        seekBar = root.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTime.setText(getResources().getString(R.string.per_hour, progress*10/3));

                tvHours.setText(getResources().getString(R.string.four_ours, progress/5));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return root;
    }

    private static final String[] COUNTRIES = new String[] {
            "مكتب", "مؤسسة", "محل تجاري", "عيادة", "منزل"
    };

    private void showSnackBar(View root,int stringResource, int colorResource) {
        View homeContainer = root.findViewById(R.id.constraintLayoutBottomSheet);
        Snackbar snackbar = Snackbar.make(homeContainer, root.getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(root.getResources().getColor(colorResource));
        snackbar.show();
    }


}

