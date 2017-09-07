package com.newsclient.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.newsclient.R;
import com.newsclient.data.Data;

public class VSettings extends Activity {
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
    }
}
