package com.example.cleaningservices.user_screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cleaningservices.R;
import com.example.cleaningservices.adapter.NewServicesAdapter;
import com.example.cleaningservices.adapter.ServicesAdapter;
import com.example.cleaningservices.model.ServiceType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewServiceFragment extends Fragment {


    RecyclerView rvServicesTypes;
    SwipeRefreshLayout refreshLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tv;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewServiceFragment newInstance(String param1, String param2) {
        NewServiceFragment fragment = new NewServiceFragment();
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
        View view =  inflater.inflate(R.layout.fragment_new_service, container, false);
        rvServicesTypes = view.findViewById(R.id.rec_service_type);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        tv = view.findViewById(R.id.textView12);
        tv.setVisibility(View.GONE);
        onRefresh();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewServiceFragment.this.onRefresh();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                requireActivity().startActivity(new Intent(getActivity(),HomeActivity.class));
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
                        NewServicesAdapter adapter = new NewServicesAdapter(getActivity(), arrServices);
                        rvServicesTypes.setLayoutManager(new GridLayoutManager(getContext(), 3));
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