package com.newsclient.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup;

import com.newsclient.R;
import com.newsclient.data.DTagList;
import com.newsclient.data.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class VSettings extends Activity{
    VSingleItemSelected adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switch1 = (Switch)findViewById(R.id.settings_switch1);
        Switch switch2 = (Switch)findViewById(R.id.settings_switch2);

        final Data app = (Data)getApplication();
        switch1.setChecked(app.is_4G_mode_on);
        switch2.setChecked(app.is_night_shift_on);

        switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    app.is_4G_mode_on = true;

                } else {
                    app.is_4G_mode_on = false;
                }

            }
        });
        switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    app.is_night_shift_on = true;
                } else {
                    app.is_night_shift_on = false;
                }

            }
        });

        final ListView tags_lv = (ListView)findViewById(R.id.settings_listview);

        DTagList tl = app.dtaglist;
        final HashMap<Integer,Boolean> isselected = new HashMap<Integer,Boolean>();
        final ArrayList<String> displaytags = new ArrayList<String>();
        for(int i = 0; i < app.dtaglist.lstImageitem.size(); i++){
            displaytags.add((String)app.dtaglist.lstImageitem.get(i).get("ItemText"));
            isselected.put(i, false);
        }

        adapter = new VSingleItemSelected(this, displaytags, isselected);
        tags_lv.setAdapter(adapter);
        tags_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ViewGroup.LayoutParams params = tags_lv.getLayoutParams();
        params.height = 195 * app.dtaglist.lstImageitem.size();
        tags_lv.setLayoutParams(params);
        Button delete_sureBtn = (Button) findViewById(R.id.Settings_deletebutton);
        delete_sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = adapter.getIsSelected().size() - 1; i >= 0; i--) {
                    if (adapter.getIsSelected().get(i).equals(true)) {
                        isselected.put(i, false);
                        displaytags.remove(i);
                        app.dtaglist.removetag(i);
                    }
                }
                tags_lv.setAdapter(adapter);
                tags_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                ViewGroup.LayoutParams params = tags_lv.getLayoutParams();
                params.height = 195 * app.dtaglist.lstImageitem.size();
                tags_lv.setLayoutParams(params);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
