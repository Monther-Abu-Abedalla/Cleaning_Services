package com.example.cleaningservices.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cleaningservices.fragments.CompletedServicesFragment;
import com.example.cleaningservices.fragments.PendingServicesFragment;

import org.jetbrains.annotations.NotNull;

public class CompletedPendingServicesViewPagerAdapter extends FragmentStateAdapter {

    FragmentActivity fragmentActivity;

    public CompletedPendingServicesViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                Log.e("msg_counter", "case 0");
                fragment = new CompletedServicesFragment();
                break;

            case 1:
                Log.e("msg_counter", "case 1");
                fragment = new PendingServicesFragment();
                break;
        }
        return fragment;
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}

