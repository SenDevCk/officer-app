package org.nic.lmd.fragments;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.nic.lmd.adapters.ManufacturerWeightAdapter;
import org.nic.lmd.adapters.MarketInspectionItemAdapter;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.officerapp.ManufactureFeeCalculationActivity;
import org.nic.lmd.officerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarketInspectionEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketInspectionEntryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    RecyclerView recyclerView;
    TextView text_head;
    NestedScrollView nest_ll;
    // TODO: Rename and change types of parameters
    private MarketInspectionTab mParam1;
    private int mParam2;
    private String mParam3;
    private boolean mParam4;
    private boolean mParam5;

    public MarketInspectionEntryFragment() {
        // Required empty public constructor
    }


    public static MarketInspectionEntryFragment newInstance(MarketInspectionTab param1, int param2,String param3,boolean param4,boolean param5) {
        MarketInspectionEntryFragment fragment = new MarketInspectionEntryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putBoolean(ARG_PARAM4, param4);
        args.putBoolean(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (MarketInspectionTab) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getBoolean(ARG_PARAM4);
            mParam5 = getArguments().getBoolean(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_market_inspection_entry, container, false);
        text_head=root.findViewById(R.id.text_head);
        text_head.setText(""+mParam1.getName());
        nest_ll=root.findViewById(R.id.nest_ll);
        nest_ll.setNestedScrollingEnabled(false);
        recyclerView=root.findViewById(R.id.recycler_mar_ins_entry);
        MarketInspectionItemAdapter manufacturerWeightAdapter = new MarketInspectionItemAdapter(getActivity(),mParam1,mParam2,mParam3,mParam4,mParam5);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(manufacturerWeightAdapter);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ( bottom < oldBottom) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(bottom);
                        }
                    }, 100);
                }
            }
        });
        return root;
    }
}