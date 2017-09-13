package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.newsclient.R;
import com.newsclient.data.DTagList;
import com.newsclient.data.Data;

import java.util.HashMap;

import static com.newsclient.data.DTagList.category;

public class VTags extends Activity {
    static VTags v;
    Data app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Data)getApplication();
        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_tags);
        v = VTags.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTheme((app.is_night_shift_on) ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_tags);

        GridView tags_gridview = (GridView) findViewById(R.id.activity_tags_Gridview);

        DTagList tl = app.dtaglist;

        SimpleAdapter saImageitems = new SimpleAdapter(this, tl.lstImageitem, R.layout.tags_item,
                new String[]{"ItemImage", "ItemText"}, new int[]{R.id.tags_item_ItemImage, R.id.tags_item_ItemText});
        saImageitems.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                       String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }else{
                    return false;                 }
            }
        });
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
                intent.putExtra("tag_id", category.get(arg2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }
}
