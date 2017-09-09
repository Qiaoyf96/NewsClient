package com.newsclient.view;

/**
 * show news items on recent list
 *
 *
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DSingleNews;

import java.util.List;

public class VRecyclerView {

    List<DSingleNews> newsList;
    RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Activity activity;
    int targetLayout;
    int sourceLayout;
    int[] itemsId;

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
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter(this));
        setClickReflection();
    }

    void setClickReflection(){
        mAdapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(activity,  position + " click", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(activity, VDetails.class);
                try {
                    intent.putExtra("news_id", newsList.get(position).news_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                activity.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    class RecyclerShowItemGroup{

        ImageView img;
        VAlignTextView title;
        TextView source;
        TextView time;

        RecyclerShowItemGroup(View[] args){
            img = (ImageView) args[0];
            title = (VAlignTextView) args[1];
            source = (TextView) args[2];
            time = (TextView) args[3];
        }

        void bindValue(int index){
            DSingleNews news = newsList.get(index);
            title.setText(news.displayTitle(index));
            source.setText(news.displaySource());
            time.setText(news.displayTime());
            img.setImageBitmap(news.news_intropic);
        }
    }

    RecyclerShowItemGroup getGroup(View[] args){
        return new RecyclerShowItemGroup(args);
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
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.InnerViewHolder>{

    private Context activity;
    private int sourceLayout;
    private int[] itemsId;
    private List<DSingleNews> newsList;
    private VRecyclerView recyclerView;
    private OnItemClickLitener mOnItemClickLitener;

    // constructor
    RecyclerAdapter(VRecyclerView view){
        this.recyclerView = view;
        this.activity = this.recyclerView.activity;
        this.sourceLayout = this.recyclerView.sourceLayout;
        this.itemsId = this.recyclerView.itemsId;
        this.newsList = this.recyclerView.newsList;
    }
    // set click reflection
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener listener)
    {
        this.mOnItemClickLitener = listener;
    }

    // holder
    @Override
    public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InnerViewHolder holder = new InnerViewHolder(LayoutInflater.from(
                activity).inflate(sourceLayout, parent,
                false),
                itemsId);
        return holder;
    }

    @Override
    public void onBindViewHolder(final InnerViewHolder holder, final int position) {

        holder.item.bindValue(position);

        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    VRecents.v = holder.itemView;
                    VRecents.d = holder.itemView.getBackground();
                    holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.pressedBackground));

                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class InnerViewHolder extends RecyclerView.ViewHolder
    {
        VRecyclerView.RecyclerShowItemGroup item;

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
            item = recyclerView.getGroup(views);
        }
    }
}
