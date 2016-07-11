package com.example.yelowflash.assignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YELOWFLASH on 07/09/2016.
 */
public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.Holder> {
    private final Context mContext;
    List<Issue> issueList;

    public IssueAdapter(Context context, ArrayList<Issue> issueArrayList) {
        mContext = context;
        issueList = issueArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_issue, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Issue issue = issueList.get(position);
        holder.tvTitle.setText(issue.getTitle());
        holder.tvName.setText(issue.getName());
        holder.tvDesc.setText(issue.getDesc());
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_user_name)
        TextView tvName;
        @Bind(R.id.tv_desc)
        TextView tvDesc;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
