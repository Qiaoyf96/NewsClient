package com.newsclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

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
        for(int i = 1; i < app.dtaglist.lstImageitem.size(); i++){
            displaytags.add((String)app.dtaglist.lstImageitem.get(i).get("ItemText"));
            isselected.put(i, false);
        }

        adapter = new VSingleItemSelected(this, displaytags, isselected);
        tags_lv.setAdapter(adapter);
        tags_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ViewGroup.LayoutParams params = tags_lv.getLayoutParams();
        try {
            View listviewitem = adapter.getView(0, null, tags_lv);
            listviewitem.measure(0, 0);
            params.height = listviewitem.getMeasuredHeight() * app.dtaglist.lstImageitem.size() - listviewitem.getMeasuredHeight();
        }catch (Exception e){
            params.height = 0;
        }
        tags_lv.setLayoutParams(params);
        Button delete_sureBtn = (Button) findViewById(R.id.Settings_deletebutton);
        delete_sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = adapter.getIsSelected().size() - 1; i >= 0; i--) {
                    if (adapter.getIsSelected().get(i).equals(true)) {
                        isselected.put(i, false);
                        displaytags.remove(i);
                        app.dtaglist.removetag(i + 1);
                        isselected.remove(isselected.size() - 1);
                    }
                }
                tags_lv.setAdapter(adapter);
                tags_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                ViewGroup.LayoutParams params = tags_lv.getLayoutParams();
                try {
                    View listviewitem = adapter.getView(1, null, tags_lv);
                    listviewitem.measure(0, 0);
                    params.height = listviewitem.getMeasuredHeight() * app.dtaglist.lstImageitem.size() - listviewitem.getMeasuredHeight();
                }catch (Exception e){
                    params.height = 0;
                }
                tags_lv.setLayoutParams(params);
                adapter.notifyDataSetChanged();
            }
        });
        Button add_sureBtn = (Button) findViewById(R.id.Settings_addbutton);
        add_sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_tag = (EditText)findViewById(R.id.Settings_textinput);
                String tag = input_tag.getText().toString();
                TextInputLayout parent = (TextInputLayout)findViewById(R.id.settings_textinputlayout);
                parent.setErrorEnabled(false);
                if (tag.length() == 0){
                    parent.setError("Tag cannot be empty");
                }
                else if (tag.length() > 6){
                    parent.setError("Tag length must less than 6");
                }
                else{
                    boolean success = app.dtaglist.addtag(tag);
                    if (success == false){
                        parent.setError("Existed Tag");
                    }
                    else{
                        input_tag.setText("");
                        isselected.put(app.dtaglist.lstImageitem.size() - 1, false);
                        displaytags.add(tag);
                        tags_lv.setAdapter(adapter);
                        tags_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                        ViewGroup.LayoutParams params = tags_lv.getLayoutParams();
                        params.height = 195 * app.dtaglist.lstImageitem.size();
                        tags_lv.setLayoutParams(params);
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        Button aboutBtn = (Button) findViewById(R.id.settings_about);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(VSettings.this,
                        VAbout.class);
                startActivity(aboutIntent);
            }
        });
    }
}
