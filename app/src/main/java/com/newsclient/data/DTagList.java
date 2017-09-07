package com.newsclient.data;

import android.graphics.Bitmap;

import com.newsclient.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DTagList {
    public ArrayList<HashMap<String, Object>> lstImageitem;
    boolean is_initialized;
    DTagList() {
        lstImageitem = new ArrayList<HashMap<String, Object>>();
    }

    public void initialize(){
        if (is_initialized == true)
                return;
        is_initialized = true;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.beijing);
        map.put("ItemText", "北京");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.entertainment);
        map.put("ItemText", "娱乐");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.delicious);
        map.put("ItemText", "美食");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.tourism);
        map.put("ItemText", "旅行");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.technology);
        map.put("ItemText", "科技");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.cars);
        map.put("ItemText", "汽车");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.finance);
        map.put("ItemText", "财经");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.military);
        map.put("ItemText", "军事");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.sports);
        map.put("ItemText", "体育");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.health);
        map.put("ItemText", "健康");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.weather);
        map.put("ItemText", "天气");
        lstImageitem.add(map);
    }
}
