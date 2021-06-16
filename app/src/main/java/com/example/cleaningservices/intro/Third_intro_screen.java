package com.example.cleaningservices.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cleaningservices.R;
import com.example.cleaningservices.login_signup.LoginActivity;

import static android.content.Context.MODE_PRIVATE;


public class Third_intro_screen extends Fragment {
    Button go_to_login_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_third_intro_screen, container, false);
        go_to_login_btn = view.findViewById(R.id.go_to_login_btn);
        go_to_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences isFirstTimeOpened = requireActivity().getSharedPreferences("isFirstTimeOpened", MODE_PRIVATE);
                SharedPreferences.Editor isFirstTimeOpenedEditor = isFirstTimeOpened.edit();
                isFirstTimeOpenedEditor.putBoolean("isFirstTimeOpened", false);
                isFirstTimeOpenedEditor.apply();

                startActivity(new Intent(requireActivity() , LoginActivity.class));
                requireActivity().finish();
            }
        });


        return view;
    }
}