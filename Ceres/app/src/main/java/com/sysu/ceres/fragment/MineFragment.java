package com.sysu.ceres.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sysu.ceres.R;
import com.sysu.ceres.activity.CreateMessageActivity;
import com.sysu.ceres.activity.EditTaskActivity;
import com.sysu.ceres.activity.MyTaskActivity;
import com.sysu.ceres.model.Task;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "username";
    private static final String ARG_LIST_TYPE = "mytask_type";
    private static final String ARG_CURRENT_TASK = "current_task";

    // TODO: Rename and change types of parameters
    private String username = null;

    private OnFragmentInteractionListener mListener;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String name) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        } else {
            username = getResources().getString(R.string.regist_or_login);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        final TextView mine_name = view.findViewById(R.id.mine_name);
        mine_name.setText(username);
        final Button get_mycreatetask_btn = view.findViewById(R.id.get_mycreate_task_btn);
        final Button get_myjointask_btn = view.findViewById(R.id.get_myjoin_task_btn);
        final Button create_task_btn = view.findViewById(R.id.create_task_btn);

        mine_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onFragmentInteraction(mine_name.getText().toString());
                }
            }
        });
        get_mycreatetask_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.equals(getResources().getString(R.string.regist_or_login))) {
                    mListener.onFragmentInteraction(mine_name.getText().toString());
                } else {
                    Intent intent = new Intent(getActivity(), MyTaskActivity.class);
                    //false-mycreatetask; true-myjointask
                    intent.putExtra(ARG_LIST_TYPE, false);
                    startActivityForResult(intent, 0);
                }
            }
        });
        get_myjointask_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.equals(getResources().getString(R.string.regist_or_login))) {
                    mListener.onFragmentInteraction(mine_name.getText().toString());
                } else {
                    Intent intent = new Intent(getActivity(), MyTaskActivity.class);
                    //false-mycreatetask; true-myjointask
                    intent.putExtra(ARG_LIST_TYPE, true);
                    startActivityForResult(intent, 0);
                }
            }
        });

        create_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.equals(getResources().getString(R.string.regist_or_login))) {
                    mListener.onFragmentInteraction(mine_name.getText().toString());
                } else {
                    Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                    Bundle bundle = new Bundle();
                    Task task = null;
                    bundle.putSerializable(ARG_CURRENT_TASK, task);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String name);
    }
}
