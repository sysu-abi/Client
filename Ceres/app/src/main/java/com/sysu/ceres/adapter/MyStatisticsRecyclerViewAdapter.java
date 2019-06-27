package com.sysu.ceres.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysu.ceres.R;
import com.sysu.ceres.model.Statistic;

import java.util.List;

public class MyStatisticsRecyclerViewAdapter extends RecyclerView.Adapter<MyStatisticsRecyclerViewAdapter.ViewHolder> {

    private List<Statistic> mValues;

    public MyStatisticsRecyclerViewAdapter(List<Statistic> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_statistics_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.question_number.setText(String.valueOf(mValues.get(position).getQid()));
        holder.statistic_answer_a.setText(String.valueOf(mValues.get(position).getCountA()));
        holder.statistic_answer_b.setText(String.valueOf(mValues.get(position).getCountB()));
        holder.statistic_answer_c.setText(String.valueOf(mValues.get(position).getCountC()));
        holder.statistic_answer_d.setText(String.valueOf(mValues.get(position).getCountD()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView question_number;
        public final TextView statistic_answer_a;
        public final TextView statistic_answer_b;
        public final TextView statistic_answer_c;
        public final TextView statistic_answer_d;
        public Statistic mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            question_number = (TextView) view.findViewById(R.id.question_number);
            statistic_answer_a = (TextView) view.findViewById(R.id.statistic_answer_a);
            statistic_answer_b = (TextView) view.findViewById(R.id.statistic_answer_b);
            statistic_answer_c = (TextView) view.findViewById(R.id.statistic_answer_c);
            statistic_answer_d = (TextView) view.findViewById(R.id.statistic_answer_d);
        }

    }
}
