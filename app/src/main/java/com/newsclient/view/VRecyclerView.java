package com.newsclient.view;

/**
 * show news items on recent list
 *
 *
 */
import com.newsclient.R;
import com.newsclient.data.DSingleNews;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VRecyclerView {

    private List<DSingleNews> newsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity activity;
    private int targetLayout;
    private int sourceLayout;
    private int[] itemsId;

    VRecyclerView(List<DSingleNews> list, Activity activity, int sourceLayout, int targetLayout, int[] itemsId){
        this.newsList = list;
        this.activity = activity;
        this.sourceLayout = sourceLayout;
        this.targetLayout = targetLayout;
        this.itemsId = itemsId;
    }

    void generate(){
        mRecyclerView = (RecyclerView) activity.findViewById(targetLayout);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter());
    }

    class RecyclerShowItemGroup{
        TextView title;
        TextView source;

        RecyclerShowItemGroup(View[] args){
            title = (TextView) args[0];
            source = (TextView) args[1];
        }

        void bindValue(int index){
            title.setText(newsList.get(index).displayTitle(index));
            source.setText(newsList.get(index).displaySource());
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.InnerViewHolder>{
        @Override
        public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            InnerViewHolder holder = new InnerViewHolder(LayoutInflater.from(
                    activity).inflate(sourceLayout, parent,
                    false),
                    itemsId);
            return holder;
        }

        @Override
        public void onBindViewHolder(InnerViewHolder holder, int position) {

            holder.item.bindValue(position);
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class InnerViewHolder extends RecyclerView.ViewHolder
        {
            RecyclerShowItemGroup item;

            public InnerViewHolder(View view, int[] args)
            {
                super(view);
                this.initContentList(view, args);
            }

            void initContentList(View parentView, int[] args){
                View[] views = new View[args.length];
                for (int i = 0; i < args.length; i++){
                    views[i] = parentView.findViewById(args[i]);
                }
                item = new RecyclerShowItemGroup(views);
            }
        }
    }
}
