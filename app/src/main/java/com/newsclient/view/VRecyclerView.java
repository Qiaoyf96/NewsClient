package com.newsclient.view;

/**
 * show news items on recent list
 *
 *
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DSingleNews;
import com.newsclient.tools.PicGetter;

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

        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL_LIST));
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
            title.setText(news.displayTitle());
            source.setText(news.displaySource());
            time.setText(news.displayTime());
            if (news.news_intropic != null){
                img.setImageBitmap(news.news_intropic.bitmap);
            }
            else{
                ConnectivityManager mConnectivityManager = (ConnectivityManager) VRecents.context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    PicGetter p = new PicGetter(VRecents.context);
                    p.setImageView(img, news);
                }
            }
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

class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        //Log.v("recyclerview - itemdecoration", "onDraw()");

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}