package com.sysu.ceres.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysu.ceres.R;
import com.sysu.ceres.fragment.TaskListFragment.OnListFragmentInteractionListener;
import com.sysu.ceres.model.Task;
import com.sysu.ceres.utils.TimeStringUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    public void setList(List<Task> newlist) {
        mValues = newlist;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mDetail.setText(mValues.get(position).getDetail());
        holder.mMoney.setText(mValues.get(position).getMoney().toString());
        holder.mEndTime.setText(TimeStringUtil.getDateToString(mValues.get(position).getEndTime()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mDetail;
        public final TextView mMoney;
        public final TextView mEndTime;

        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.task_title);
            mDetail = (TextView) view.findViewById(R.id.task_detail);
            mMoney = (TextView) view.findViewById(R.id.task_money);
            mEndTime = (TextView) view.findViewById(R.id.task_endtime);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDetail.getText() + "'";
        }
    }
}
