package com.newsclient.view;

/**
 * show news items on recent list
 *
 *
 */

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsclient.data.DSingleNews;

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

        //mLayoutManager = new RecyclerLayoutManager();
        //mLayoutManager = new GridLayoutManager(activity, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter());
    }

    class RecyclerShowItemGroup{

        ImageView img;
        VAlignTextView title;
        TextView source;
        TextView time;

        RecyclerShowItemGroup(View[] args){
            img = new ImageView(activity);
            title = (VAlignTextView) args[0];
            source = (TextView) args[1];
            time = (TextView) args[2];
        }

        void bindValue(int index){
            DSingleNews news = newsList.get(index);
            title.setText(news.displayTitle(index));
            source.setText(news.displaySource());
            time.setText(news.displayTime());
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

    class RecyclerLayoutManager extends RecyclerView.LayoutManager{


        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
            // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
            // 实际就是把View放到了Recycler中的一个集合中。
            detachAndScrapAttachedViews(recycler);
            calculateChildrenSite(recycler);
        }

        private void calculateChildrenSite(RecyclerView.Recycler recycler) {
            int totalHeight = 0;
            for (int i = 0; i < getItemCount(); i++) {
                View view = recycler.getViewForPosition(i);
                addView(view);
                //我们自己指定ItemView的尺寸。
                //measureChildWithMargins();
                measureChildWithMargins(view, 200, 0);
                int width = getDecoratedMeasuredWidth(view);
                int height = getDecoratedMeasuredHeight(view);
                Rect mTmpRect = new Rect();
                calculateItemDecorationsForChild(view, mTmpRect);
                if (i % 2 == 0) { //当i能被2整除时，是左，否则是右。
                    //左
                    layoutDecoratedWithMargins(view, 0, totalHeight, 200,
                            totalHeight + height);
                } else {
                    //右，需要换行
                    layoutDecoratedWithMargins(view, 200, totalHeight,
                            400, totalHeight + height);
                    totalHeight = totalHeight + height;
                    //LogUtils.e(i + "->" + totalHeight);
                }
            }
        }


    }
}
