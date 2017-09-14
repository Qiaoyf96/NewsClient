package com.java.group28.newsclient.view;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.group28.newsclient.R;
import com.java.group28.newsclient.data.DSingleNews;
import com.java.group28.newsclient.data.Data;
import com.java.group28.newsclient.tools.Network;
import com.java.group28.newsclient.tools.PicGetter;

import java.util.List;

public class VRecyclerView {

    List<DSingleNews> newsList;
    public RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Activity activity;
    public int targetLayout;
    int sourceLayout;
    int[] itemsId;
    private boolean decorationExisted = false;
    Data app;

    VRecyclerView(List<DSingleNews> list, Activity activity, int sourceLayout, int targetLayout, int[] itemsId){
        this.newsList = list;
        this.activity = activity;
        this.sourceLayout = sourceLayout;
        this.targetLayout = targetLayout;
        this.itemsId = itemsId;
        this.app = (Data)activity.getApplication();
    }

    public void generate(){
        mRecyclerView = (RecyclerView) activity.findViewById(targetLayout);
        mRecyclerView.setBackgroundColor((app.is_night_shift_on)
                ? activity.getResources().getColor(R.color.dark_mainBackgroundColor)
                : activity.getResources().getColor(R.color.light_mainBackgroundColor));
        mLayoutManager = new LinearLayoutManager(activity);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        setClickReflection();

        if (!decorationExisted){
            mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,
                    DividerItemDecoration.VERTICAL_LIST));
            decorationExisted = true;
        }
    }

    public void refresh(){
        mAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        setClickReflection();

        if (!decorationExisted){
            mRecyclerView.addItemDecoration(new DividerItemDecoration(activity,
                    DividerItemDecoration.VERTICAL_LIST));
            decorationExisted = true;
        }
    }

    void setClickReflection(){
        mAdapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
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
            Data app = (Data) VRecyclerView.this.activity.getApplication();
            DSingleNews news = newsList.get(index);
            title.setBackgroundColor((app.is_night_shift_on)
                    ? activity.getResources().getColor(R.color.dark_mainBackgroundColor)
                    : activity.getResources().getColor(R.color.light_mainBackgroundColor));
            title.setTextColor((app.is_night_shift_on)
                    ? activity.getResources().getColor(R.color.dark_titleTextColor)
                    : activity.getResources().getColor(R.color.light_titleTextColor));
            title.setText(news.displayTitle());
            if (news.readed){
                title.setTextColor(activity.getResources().getColor(R.color.recentTitleVisitedColor));
            }
            source.setTextColor((app.is_night_shift_on)
                    ? activity.getResources().getColor(R.color.dark_sourceTextColor)
                    : activity.getResources().getColor(R.color.light_sourceTextColor));
            source.setText(news.displaySource());
            time.setTextColor((app.is_night_shift_on)
                    ? activity.getResources().getColor(R.color.dark_sourceTextColor)
                    : activity.getResources().getColor(R.color.light_sourceTextColor));
            time.setText(news.displayTime());

            if (app.is_4G_mode_on){
                img.setVisibility(View.GONE);
            }
            else{
                if (news.news_intropic.bitmap != null){
                    img.setImageBitmap(news.news_intropic.bitmap);
                }
                else{
                    if (Network.isConnected()) {
                        PicGetter p = new PicGetter(VRecents.context);
                        p.setImageView(img, news);
                    }
                    else {
                        img.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    RecyclerShowItemGroup getGroup(View[] args){
        return new RecyclerShowItemGroup(args);
    }

}
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.InnerViewHolder>{

    private Activity activity;
    private int sourceLayout;
    private int[] itemsId;
    private List<DSingleNews> newsList;
    private VRecyclerView recyclerView;
    private OnItemClickLitener mOnItemClickLitener;
    static int lastvisibleposition;
    Data app;

    // constructor
    RecyclerAdapter(VRecyclerView view){
        this.recyclerView = view;
        this.activity = this.recyclerView.activity;
        this.sourceLayout = this.recyclerView.sourceLayout;
        this.itemsId = this.recyclerView.itemsId;
        this.newsList = this.recyclerView.newsList;
        this.app = (Data)this.activity.getApplication();
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
        holder.itemView.setBackgroundColor((app.is_night_shift_on)
                ? activity.getResources().getColor(R.color.dark_mainBackgroundColor)
                : activity.getResources().getColor(R.color.light_mainBackgroundColor));

        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    VRecents.v = holder.itemView;
                    VRecents.d = holder.itemView.getBackground();

                    holder.item.title.setTextColor(activity.getResources().getColor(R.color.recentTitleVisitedColor));

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