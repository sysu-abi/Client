package com.sysu.ceres.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sysu.ceres.R;
import com.sysu.ceres.model.Task;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskDetailFragment extends Fragment {

    private static final String ARG_CURRENT_TASK = "current_task";

    private Task currentTask;

    public static TaskDetailFragment newInstance(Task item) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_TASK, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentTask = (Task) getArguments().getSerializable(ARG_CURRENT_TASK);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_detail, container, false);
        final TextView task_title = root.findViewById(R.id.task_detail_title);
        final TextView task_detail = root.findViewById(R.id.task_detail_detail);

        task_title.setText(currentTask.getTitle());
        task_detail.setText(currentTask.getDetail());

        return root;
    }
}