package com.sysu.ceres.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.adapter.MyTaskRecyclerViewAdapter;
import com.sysu.ceres.R;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Task;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TaskListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_GET_TASK_METHOD = "tasklist_method";
    private static final String ARG_GET_TASK_ORDER = "tasklist_order";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private List<Task> myTaskList = new ArrayList<>();
    private MyTaskRecyclerViewAdapter myListViewAdapter;

    // 0- default; 1-ddl; 2-money; 3-starttime; 4-myjointask; 5-mypublishtask
    private int getListMethod = 0;
    private String getListOrder = "asc";

    ObserverOnNextListener<TaskList> listener = new ObserverOnNextListener<TaskList>() {
        @Override
        public void onNext(TaskList tasklist) {
            Log.d(TAG, "onNext: " + tasklist.toString());
            myTaskList = tasklist.getTaskList();
            for (Task sub : myTaskList) {
                Log.d(TAG, "onNext: " + sub.toString());
            }
            myListViewAdapter.setList(myTaskList);
        }
    };
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskListFragment newInstance(int method, String mode) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GET_TASK_METHOD, method);
        args.putString(ARG_GET_TASK_ORDER, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            getListMethod = getArguments().getInt(ARG_GET_TASK_METHOD);
            getListOrder = getArguments().getString(ARG_GET_TASK_ORDER);
        }

    }

    void getTaskList() {
        switch (getListMethod) {
            case 0:
                ApiMethods.getTaskList(new MyObserver<TaskList>(getActivity(), listener));
                break;
            case 1:
                ApiMethods.getTaskListByDDL(new MyObserver<TaskList>(getActivity(), listener), getListOrder);
                break;
            case 2:
                ApiMethods.getTaskListByMoney(new MyObserver<TaskList>(getActivity(), listener), getListOrder);
                break;
            case 3:
                ApiMethods.getTaskListByStartTime(new MyObserver<TaskList>(getActivity(), listener), getListOrder);
                break;
            case 4:
                ApiMethods.getJoinTasks(new MyObserver<TaskList>(getActivity(), listener),
                        CeresConfig.currentUser.getUid().intValue());
                break;
            case 5:
                ApiMethods.getPublishTasks(new MyObserver<TaskList>(getActivity(), listener),
                        CeresConfig.currentUser.getUid().intValue());
                break;
            default:
                ApiMethods.getTaskList(new MyObserver<TaskList>(getActivity(), listener));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        NiceSpinner niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("Default", "DDL", "Money", "StartTime"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                // This example uses String, but your type can be any
                //String item = parent.getItemAtPosition(position);
                getListMethod = position;
                getTaskList();
            }
        });

        CheckBox checkBox = view.findViewById(R.id.desc_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    getListOrder = "desc";
                }else{
                    getListOrder = "asc";
                }
                getTaskList();
            }
        });

        if (getListMethod == 4 || getListMethod == 5) {
            niceSpinner.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
        }

        getTaskList();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.task_list);
        myListViewAdapter = new MyTaskRecyclerViewAdapter(myTaskList, mListener);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(myListViewAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Task item);
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        mListener.onListFragmentInteraction(new Task("id", "content", "details"));
//    }
}
