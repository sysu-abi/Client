package com.sysu.ceres.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sysu.ceres.R;
import com.sysu.ceres.adapter.MyStatisticsRecyclerViewAdapter;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Statistic;
import com.sysu.ceres.model.StatisticList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StatisticsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_SID = "survey-id";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Statistic> myStatisticList;
    private long sid;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StatisticsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StatisticsFragment newInstance(long sidArg) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_SID, sidArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            sid = getArguments().getLong(ARG_SID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_list, container, false);

        ObserverOnNextListener<StatisticList> statistic_list_listener = new ObserverOnNextListener<StatisticList>() {
            @Override
            public void onNext(StatisticList statisticList) {
                Log.d(TAG, "onNext: " + statisticList.toString());
                myStatisticList = statisticList.getStatistics();
            }
        };

        ApiMethods.getStatisticsList(new MyObserver<StatisticList>(view.getContext(), statistic_list_listener), sid);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyStatisticsRecyclerViewAdapter(myStatisticList));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
