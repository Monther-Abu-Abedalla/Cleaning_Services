package com.example.cleaningservices.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cleaningservices.R;
import com.example.cleaningservices.adapter.CompletedPendingServicesAdapter;
import com.example.cleaningservices.model.PendingServices;
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
 * Use the {@link PendingServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingServicesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView rvPendingServices;
    private SwipeRefreshLayout refreshLayout;
    public PendingServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingServicesFragment newInstance(String param1, String param2) {
        PendingServicesFragment fragment = new PendingServicesFragment();
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
        View root = inflater.inflate(R.layout.fragment_pending_services, container, false);

        rvPendingServices = root.findViewById(R.id.rvPendingServices);
        refreshLayout = root.findViewById(R.id.refreshPending);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myRefresh();
            }
        });
        myRefresh();
        return root;
    }

    private void showSnackBar(Activity activity, int stringResource, int colorResource) {
        View homeContainer = activity.findViewById(R.id.homeContainer);
        Snackbar snackbar = Snackbar.make(homeContainer, activity.getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(activity.getResources().getColor(colorResource));
        snackbar.show();
    }

    private void myRefresh(){

        ArrayList<PendingServices> arrServices = new ArrayList<>();
        db.collection("pending_services").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {

                            arrServices.add(
                                    new PendingServices(queryDocumentSnapshots.getDocuments().get(i).get("id").toString(),
                                            Long.parseLong( queryDocumentSnapshots.getDocuments().get(i).get("time").toString()),
                                            queryDocumentSnapshots.getDocuments().get(i).get("money").toString(),
                                            Integer.parseInt(queryDocumentSnapshots.getDocuments().get(i).get("Hours").toString())
                                    )
                            );
                        }
                        if (refreshLayout.isRefreshing()){
                            refreshLayout.setRefreshing(false);
                        }
                        CompletedPendingServicesAdapter adapter = new CompletedPendingServicesAdapter(getActivity(), arrServices, "pending");
                        rvPendingServices.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvPendingServices.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                showSnackBar(requireActivity(), R.string.error_occurred, R.color.red);
            }
        });
    }

}