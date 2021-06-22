package com.example.cleaningservices.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cleaningservices.R;
import com.example.cleaningservices.adapter.CompletedPendingServicesViewPagerAdapter;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletedPendingServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedPendingServicesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialButton btnShowCompletedServices;
    private MaterialButton btnShowPendingServices;
    private ViewPager2 vpCompletedPendingServices;


    public CompletedPendingServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletedPendingServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletedPendingServicesFragment newInstance(String param1, String param2) {
        CompletedPendingServicesFragment fragment = new CompletedPendingServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_completed_pending_services, container, false);

        btnShowCompletedServices = root.findViewById(R.id.btnShowCompletedServices);
        btnShowPendingServices = root.findViewById(R.id.btnShowPendingServices);
        vpCompletedPendingServices = root.findViewById(R.id.vpCompletedPendingServices);

        CompletedPendingServicesViewPagerAdapter adapter = new CompletedPendingServicesViewPagerAdapter(getActivity());
        vpCompletedPendingServices.setAdapter(adapter);
        vpCompletedPendingServices.setCurrentItem(0);

        btnShowCompletedServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vpCompletedPendingServices.getCurrentItem() == 1) {
                    vpCompletedPendingServices.setCurrentItem(0);
                }
            }
        });

        btnShowPendingServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vpCompletedPendingServices.getCurrentItem() == 0) {
                    vpCompletedPendingServices.setCurrentItem(1);
                }
            }
        });

        vpCompletedPendingServices.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnShowCompletedServices.setBackgroundTintList(ContextCompat.getColorStateList(requireActivity(), R.color.primary_color));
                    btnShowCompletedServices.setTextColor(requireActivity().getResources().getColor(R.color.white));
                    btnShowPendingServices.setBackgroundTintList(ContextCompat.getColorStateList(requireActivity(), R.color.less_gray));
                    btnShowPendingServices.setTextColor(requireActivity().getResources().getColor(R.color.black));
                } else if (position == 1) {
                    btnShowCompletedServices.setBackgroundTintList(ContextCompat.getColorStateList(requireActivity(), R.color.less_gray));
                    btnShowCompletedServices.setTextColor(requireActivity().getResources().getColor(R.color.black));
                    btnShowPendingServices.setBackgroundTintList(ContextCompat.getColorStateList(requireActivity(), R.color.primary_color));
                    btnShowPendingServices.setTextColor(requireActivity().getResources().getColor(R.color.white));
                }
                super.onPageSelected(position);
            }
        });


        return root;
    }


}