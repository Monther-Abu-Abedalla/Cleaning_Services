package com.example.cleaningservices.user_screens;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.arthurivanets.bottomsheets.config.BaseConfig;
import com.arthurivanets.bottomsheets.config.Config;
import com.example.cleaningservices.R;

public class BottomSheet extends BaseBottomSheet {

    public BottomSheet(@NonNull Activity hostActivity) {
        this(hostActivity, new Config.Builder(hostActivity).build());
    }

    public BottomSheet(@NonNull Activity hostActivity, @NonNull BaseConfig config) {
        super(hostActivity, config);
    }

    @NonNull
    @Override
    public final View onCreateSheetContentView(@NonNull Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.confirm_service_bottom_sheet, this, false);

        TextView tvTime = root.findViewById(R.id.tvTime);
        tvTime.setText(getResources().getString(R.string.per_hour, 17));

        return root;
    }

}

