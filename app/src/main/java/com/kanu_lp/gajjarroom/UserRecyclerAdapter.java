package com.kanu_lp.gajjarroom;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Kanu
 * Created on 2/25/2018
 */
public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {
    private Context mContext;

    private List<User> mDataList;

    private ClickListener clickListener;

    public UserRecyclerAdapter(Context context, List<User> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder=null;
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist, parent, false);
                viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final User entity = mDataList.get(position);
        holder.textFirstname.setText(entity.getFirstName());
        holder.textLastname.setText(entity.getLastName());
        holder.textAge.setText(String.valueOf(entity.getAge()));

    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = (ClickListener) clickListener;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_firstname)
        TextView textFirstname;
        @BindView(R.id.text_lastname)
        TextView textLastname;
        @BindView(R.id.text_age)
        TextView textAge;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

}
