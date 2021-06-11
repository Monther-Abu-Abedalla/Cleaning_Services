package com.example.cleaningservices.intro;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cleaningservices.R;

public class Second_intro_screen extends Fragment {
     ImageView move_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_secondt_intro_screen, container, false);
        move_btn = (ImageView)root.findViewById(R.id.move_btn);



        move_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ViewPager2 view = getActivity().findViewById(R.id.viewPager);
                    view.setCurrentItem(2);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });


        return root;

    }
}