package com.example.cleaningservices.user_screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cleaningservices.R;
import com.example.cleaningservices.adapter.ServicesAdapter;
import com.example.cleaningservices.model.ServiceType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rvServicesTypes;
    SwipeRefreshLayout refreshLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvServicesTypes = view.findViewById(R.id.rvServicesTypes);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        TextView tvMyService = view.findViewById(R.id.textView10);
        TextView tvShowAll = view.findViewById(R.id.textView11);

        tvMyService.setVisibility(View.GONE);
        tvShowAll.setVisibility(View.GONE);
            onRefresh();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HomeFragment.this.onRefresh();
            }
        });



        return view;
    }

    private void onRefresh() {
        ArrayList<ServiceType> arrServices = new ArrayList<>();
        db.collection("services_types").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            try {
                                String serviceId = queryDocumentSnapshots.getDocuments().get(i).get("id").toString();
                                String serviceName = queryDocumentSnapshots.getDocuments().get(i).get("serviceName").toString();
                                String imageResource = queryDocumentSnapshots.getDocuments().get(i).get("imageResource").toString();
                                arrServices.add(new ServiceType(serviceId, serviceName, imageResource));
                            }
                            catch (Exception e) {
                                showSnackBar(R.string.enter_a_valid_data_in_db, R.color.red);

                            }
                        }
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        ServicesAdapter adapter = new ServicesAdapter(getActivity(), arrServices);
                        rvServicesTypes.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                        rvServicesTypes.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
    }

    private void showSnackBar(int stringResource, int colorResource) {
        View homeContainer = requireActivity().findViewById(R.id.homeContainer);
        Snackbar snackbar = Snackbar.make(homeContainer, requireActivity().getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(requireActivity().getResources().getColor(colorResource));
        snackbar.show();
    }

}