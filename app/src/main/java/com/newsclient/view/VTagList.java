package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.DSingleNews;
import com.newsclient.data.DSingleTag;
import com.newsclient.data.DTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VTagList extends Activity {
    int tagId;
    //final DSingleTag dt;

    @Override
    protected void onResume() {
        final DSingleTag dt = DTagList.getNewsById(tagId);

        String[] titleList = new String[dt.size];

        for (int i = 0; i < dt.size; i++) {
            titleList[i] = dt.news_list.get(i).news_title;
        }

        //ListView lv = (ListView) findViewById(R.id.listViewTag);

        CardView cv = (CardView) this.findViewById(R.id.card);

//        cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(VTagList.this, "点到了！！", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.taglist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CardAdapter adapter = new CardAdapter(this, R.layout.activity_cardview, new int[]{
                R.id.card_title,
                R.id.card_source,
                R.id.card_intropic,
                R.id.card_intro},
                dt);

        adapter.setOnItemClickLitener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(VTagList.this, VDetails.class);
                try {
                    intent.putExtra("news_id", dt.news_list.get(position).news_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                VTagList.this.startActivity(intent);
                //Toast toast = Toast.makeText(VTagList.this, "点到了" + position + "！！", Toast.LENGTH_SHORT);
                //toast.show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        recyclerView.setAdapter(adapter);

        // simpleAdapter
//        CardAdapter adapter = new CardAdapter(this, getItemData(dt), R.layout.activity_cardview,
//                new String[] {"card_title", "card_source", "card_intropic", "card_intro"},
//                new int[] {R.id.card_title, R.id.card_source, R.id.card_intropic, R.id.card_intro});
//
//
//        adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(View view, int position){
//                //
//                Toast toast = Toast.makeText(VTagList.this, "点到" + position + "了！！", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });

        //adapter.setViewBinder(new CardViewBinder(this, adapter.mOnItemClickListener));

        //lv.setAdapter(adapter);
        //lv.setDivider(null);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//
//
//
//                //点击后在标题上显示点击了第几行
//                Intent intent = new Intent(VTagList.this, VDetails.class);
//                try {
//                    intent.putExtra("news_id", dt.news_list.get(arg2).news_id);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                startActivity(intent);
//            }
//        });



        super.onResume();
    }

    private List<Map<String, Object>> getItemData(DSingleTag tag){
        // data need:
        // String card_title
        // String card_source
        // Bitmap card_intropic
        // String card_intro
        List<Map<String, Object>> list = new ArrayList<>();
        for (DSingleNews news : tag.news_list){
            Map<String, Object> map = new HashMap<>();
            map.put("card_title", news.displayTitle());
            map.put("card_source", news.displaySource() + "  " + news.displayTime());
            map.put("card_intropic", news.news_intropic);
            map.put("card_intro", news.displayIntro());
            list.add(map);
        }

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taglist);

        Intent intent = getIntent();
        tagId = intent.getIntExtra("tag_id", -1);

    }
}

//class CardAdapter extends SimpleAdapter{
//    OnItemClickListener mOnItemClickListener;
//
//    CardAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
//        super(context, data, resource, from, to);
//    }
//
//    public interface OnItemClickListener
//    {
//        void onItemClick(View view, int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.mOnItemClickListener = listener;
//    }
//}

class CardAdapter extends RecyclerView.Adapter<CardAdapter.InnerViewHolder>{


    private Activity activity;
    private int sourceLayout;
    private int[] itemsId;
    private List<DSingleNews> newsList;
    //private VRecyclerView recyclerView;
    private OnItemClickListener mOnItemClickListener;

    // constructor
    CardAdapter(Activity activity, int source, int[] itemsId, DSingleTag tag){
        this.activity = activity;
        this.sourceLayout = source;
        this.itemsId = itemsId;
        this.newsList = tag.news_list;
//        this.recyclerView = view;
//        this.activity = this.recyclerView.activity;
//        this.sourceLayout = this.recyclerView.sourceLayout;
//        this.itemsId = this.recyclerView.itemsId;
//        this.newsList = this.recyclerView.newsList;
    }
    // set click reflection
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public void setOnItemClickLitener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
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

        holder.item.bindValue(position, this.newsList.get(position));

        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
//                    VRecents.v = holder.itemView;
//                    VRecents.d = holder.itemView.getBackground();
//                    holder.itemView.setBackgroundColor(activity.getResources().getColor(R.color.pressedBackground));

                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
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
        CardItemGroup item;

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
            item = new CardItemGroup(views);
        }
    }
}

class CardItemGroup{
    VAlignTextView title;
    TextView source;
    ImageView intropic;
    VAlignTextView intro;

    CardItemGroup(View[] args){
        title = (VAlignTextView) args[0];
        source = (TextView) args[1];
        intropic = (ImageView) args[2];
        intro = (VAlignTextView) args[3];
    }

    void bindValue(int index, DSingleNews news){

        title.setText(news.displayTitle());
        source.setText(news.displaySource() + "   " + news.displayTime());
        if (news.news_intropic.bitmap != null){
            intropic.setImageBitmap(news.news_intropic.bitmap);
        }
        else{
            intropic.setVisibility(View.GONE);
        }
        intro.setText(news.displayIntro());
    }
}


class CardViewBinder implements SimpleAdapter.ViewBinder{

    Activity activity;
    CardAdapter.OnItemClickListener listener;
    CardViewBinder(){
        super();
        this.activity = null;
    }
    CardViewBinder(Activity activity, CardAdapter.OnItemClickListener listener){
        super();
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public boolean setViewValue(View view, Object o, String s) {
        // both title and intro

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(activity, "点到了！！", Toast.LENGTH_SHORT);
//                toast.show();
//
//            }
//        });

//        CardView cv = activity.findViewById(R.layout.activity_cardview);
//        cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(activity, "点到了！！", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, 0);
            }
        });

        if (view instanceof VAlignTextView && o instanceof String){
            if (((String)o) == null || ((String) o) == ""){
                ((VAlignTextView) view).setVisibility(View.GONE);
            }
            else{
                ((VAlignTextView) view).setText((String) o);
            }
            return true;
        }
        if (view instanceof TextView && o instanceof String){
            ((TextView) view).setText((String) o);
            return true;
        }
        if (view instanceof ImageView && o instanceof Bitmap){
            ((ImageView) view).setImageBitmap((Bitmap) o);
            return true;
        }
        return false;
    }
}