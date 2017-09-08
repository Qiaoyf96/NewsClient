package com.newsclient.data;

import android.graphics.Bitmap;

import com.newsclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class DTagList {
    public ArrayList<HashMap<String, Object>> lstImageitem;
    public HashSet<String> lstItem;
    boolean is_initialized;
    DTagList() {
        lstImageitem = new ArrayList<HashMap<String, Object>>();
    }

    public void initialize(){
        if (is_initialized == true)
                return;
        is_initialized = true;
        HashMap<String, Object> map = new HashMap<String, Object>();
        lstItem = new HashSet<String>();
        map.put("ItemImage", R.mipmap.beijing);
        map.put("ItemText", "北京");
        lstImageitem.add(map);
        lstItem.add("北京");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.entertainment);
        map.put("ItemText", "娱乐");
        lstImageitem.add(map);
        lstItem.add("娱乐");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.delicious);
        map.put("ItemText", "美食");
        lstItem.add("美食");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.tourism);
        map.put("ItemText", "旅行");
        lstItem.add("旅行");
        lstImageitem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.technology);
        map.put("ItemText", "科技");
        lstImageitem.add(map);
        lstItem.add("科技");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.cars);
        map.put("ItemText", "汽车");
        lstImageitem.add(map);
        lstItem.add("汽车");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.finance);
        map.put("ItemText", "财经");
        lstImageitem.add(map);
        lstItem.add("财经");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.military);
        map.put("ItemText", "军事");
        lstImageitem.add(map);
        lstItem.add("军事");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.sports);
        map.put("ItemText", "体育");
        lstImageitem.add(map);
        lstItem.add("体育");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.health);
        map.put("ItemText", "健康");
        lstImageitem.add(map);
        lstItem.add("健康");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.weather);
        map.put("ItemText", "天气");
        lstImageitem.add(map);
        lstItem.add("天气");
    }

    public void removetag(int i){
        lstImageitem.remove(i);
    }

    public boolean addtag(String newtag){
        if (lstItem.contains(newtag))
            return false;
        Random random = new Random();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemText", newtag);
        int imagekey = random.nextInt(3);
        if (imagekey == 0)
            map.put("ItemImage", R.mipmap.blue);
        if (imagekey == 1)
            map.put("ItemImage", R.mipmap.orange);
        if (imagekey == 2)
            map.put("ItemImage", R.mipmap.green);

        lstImageitem.add(map);
        lstItem.add(newtag);
        return true;
    }
}
