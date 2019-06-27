package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.sysu.ceres.R;
import com.sysu.ceres.fragment.StatisticsFragment;
import com.sysu.ceres.fragment.TaskListFragment;

public class StatisticListActivity extends AppCompatActivity {
    private static final String ARG_SURVEY_SID = "survey_sid";
    //todo
    int sid;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_list);

        sid = getIntent().getIntExtra(ARG_SURVEY_SID, -1);
        if(sid == -1) {
            Log.d("no sid ", "-1");
        }

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_statistic, StatisticsFragment.newInstance(sid));
        transaction.commit();
    }
}
