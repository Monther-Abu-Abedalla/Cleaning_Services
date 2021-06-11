package com.example.cleaningservices.intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cleaningservices.R;
import com.example.cleaningservices.login_signup.LoginActivity;


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
                Intent i = new Intent(getContext() , LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });


//        try {
//            ViewPager2 view = getActivity().findViewById(R.id.viewPager);
//            view.setCurrentItem(1);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }



        return view;
    }
}