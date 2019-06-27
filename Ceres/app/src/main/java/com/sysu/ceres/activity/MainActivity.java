package com.sysu.ceres.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.fragment.MineFragment;
import com.sysu.ceres.fragment.TaskListFragment;
import com.sysu.ceres.model.Task;

public class MainActivity extends AppCompatActivity
        implements TaskListFragment.OnListFragmentInteractionListener,
        MineFragment.OnFragmentInteractionListener {

    private static final String ARG_CURRENT_TASK = "current_task";
    private String current_user = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.fragment_container, TaskListFragment.newInstance(0, "asc"));
                    transaction.commit();
                    return true;
                case R.id.navigation_mine:
                    transaction.replace(R.id.fragment_container, MineFragment.newInstance(current_user));
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    // 设置默认进来是tab 显示的页面
    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, TaskListFragment.newInstance(0, "asc"));
        transaction.commit();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 2) {
//            if (CeresConfig.currentUser != null) {
//                current_user = CeresConfig.currentUser.getName();
//                BottomNavigationView navView = findViewById(R.id.nav_view);
//                navView.setSelectedItemId(R.id.navigation_mine);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_user = getResources().getString(R.string.regist_or_login);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setDefaultFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CeresConfig.currentUser != null) {
            current_user = CeresConfig.currentUser.getName();
        } else {
            current_user = getResources().getString(R.string.regist_or_login);
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onListFragmentInteraction(Task item) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TaskDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_TASK, item);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onFragmentInteraction(String name) {
        if (CeresConfig.currentUser == null){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 0);
        } else {
            Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
            startActivityForResult(intent, 0);
        }
    }
}
