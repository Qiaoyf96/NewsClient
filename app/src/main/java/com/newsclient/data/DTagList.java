package com.newsclient.data;

import com.newsclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class DTagList {
    public static ArrayList<HashMap<String, Object>> lstImageitem = new ArrayList<HashMap<String, Object>>();
    static ArrayList<String> lstItem = new ArrayList<String>();
    static ArrayList<ArrayList<String>> lstdetail = new ArrayList<ArrayList<String>>();
    static ArrayList<String> readedlist = new ArrayList<String>();
    static boolean is_initialized = false;

    public static ArrayList<HashMap<String, Object>> getListItem() {
        return lstImageitem;
    }

    public void initialize(){
        if (is_initialized == true)
                return;
        is_initialized = true;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.favoriteicon);
        map.put("ItemText", "我最喜欢");
        lstImageitem.add(map);
        lstItem.add("我最喜欢");
        map = new HashMap<String, Object>();
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
        for(int i = 0; i < 12; i++) {
            ArrayList<String> as = new ArrayList<String>();
            lstdetail.add(as);
        }
    }

    public static void removetag(int i){
        lstdetail.remove(i);
        lstImageitem.remove(i);
        lstItem.remove(i);
    }

    public static boolean addtag(String newtag){
        if (lstItem.indexOf(newtag) != -1)
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
        ArrayList<String> as = new ArrayList<String>();
        lstdetail.add(as);
        return true;
    }

    public static void addNewsToTag(int tagidx, String news_id){
        if (tagidx == -1){
            readedlist.add(news_id);
        }
        else{
            int idx = lstdetail.get(tagidx).indexOf(news_id);
            if (idx == -1){
                lstdetail.get(tagidx).add(news_id);
            }
            else{
                lstdetail.get(tagidx).remove(idx);
            }
        }
    }

    public static DSingleTag getNewsById(int id){
        DSingleTag current_tag = new DSingleTag();
        current_tag.set(lstdetail.get(id));
        return current_tag;
    }
}
