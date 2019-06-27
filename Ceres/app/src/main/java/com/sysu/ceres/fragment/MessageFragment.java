package com.sysu.ceres.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.activity.CreatSurveyActivity;
import com.sysu.ceres.activity.CreateMessageActivity;
import com.sysu.ceres.activity.LoginActivity;
import com.sysu.ceres.adapter.MyMessageRecyclerViewAdapter;
import com.sysu.ceres.R;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Message;
import com.sysu.ceres.model.MessageList;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MessageFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_TASK_ID = "tid";
    // TODO: Customize parameters
    private int tid = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Message> myMessageList = new ArrayList<>();
    private MyMessageRecyclerViewAdapter myMessageRecyclerViewAdapter;

    ObserverOnNextListener<MessageList> listener = new ObserverOnNextListener<MessageList>() {
        @Override
        public void onNext(MessageList messageList) {
            Log.d(TAG, "onNext: " + messageList.toString());
            myMessageList = messageList.getMessages();
            for (Message sub : myMessageList) {
                Log.d(TAG, "onNext: " + sub.toString());
            }
            myMessageRecyclerViewAdapter.setList(myMessageList);
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MessageFragment newInstance(int tid) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, tid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tid = getArguments().getInt(ARG_TASK_ID);
            Log.d(TAG, "onNext: " + tid);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tid != -1) {
            ApiMethods.getMessageList(new MyObserver<MessageList>(getActivity(), listener), tid);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            if (tid != -1) {
                ApiMethods.getMessageList(new MyObserver<MessageList>(getActivity(), listener), tid);
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        ApiMethods.getMessageList(new MyObserver<MessageList>(view.getContext(), listener), tid);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CeresConfig.currentUser == null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(getActivity(), CreateMessageActivity.class);
                    intent.putExtra(ARG_TASK_ID, tid);
                    startActivity(intent);
                }
            }
        });

        // Set the adapter
        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.message_list);
            myMessageRecyclerViewAdapter = new MyMessageRecyclerViewAdapter(myMessageList, mListener);
            recyclerView.setAdapter(myMessageRecyclerViewAdapter);
        }
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
        void onListFragmentInteraction(Message item);
    }
}
