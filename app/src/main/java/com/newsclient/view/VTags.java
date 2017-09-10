package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.newsclient.R;
import com.newsclient.data.DTagList;
import com.newsclient.data.Data;

import java.util.HashMap;

public class VTags extends Activity {
    static VTags v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        v = VTags.this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        GridView tags_gridview = (GridView) findViewById(R.id.activity_tags_Gridview);

        Data app = (Data)getApplication();

        DTagList tl = app.dtaglist;

        SimpleAdapter saImageitems = new SimpleAdapter(this, tl.lstImageitem, R.layout.tags_item,
                new String[]{"ItemImage", "ItemText"}, new int[]{R.id.tags_item_ItemImage, R.id.tags_item_ItemText});
        tags_gridview.setAdapter(saImageitems);

        tags_gridview.setOnItemClickListener(new ItemClickListener());
    }

    class ItemClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
                                View arg1,//The view within the AdapterView that was clicked
                                int arg2,//The position of the view in the adapter
                                long arg3//The row id of the item that was clicked
        ) {
            //在本例中arg2=arg3
            HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            //显示所选Item的ItemText
            Intent intent = new Intent(v, VTagList.class);
            try {
                intent.putExtra("tag_id", arg2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }
}
