package com.example.cleaningservices.intro;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentsIntroAdapter extends FragmentStateAdapter {

    public FragmentsIntroAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
       switch (position){
           case 0 :
            fragment = new First_intro_screen();
            break;

           case 1:
            fragment = new Second_intro_screen();
               break;

           case 2:
               fragment = new Third_intro_screen();
               break;
       }
    return fragment;
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}

