package com.newsclient.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsclient.R;
import com.newsclient.tools.ImageFinder;
import com.newsclient.tools.Network;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class DTagList {
    public static ArrayList<HashMap<String, Object>> lstImageitem = new ArrayList<HashMap<String, Object>>();
    public static ArrayList<String> lstItem = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> lstdetail = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> readedlist = new ArrayList<String>();
    public static ArrayList<Integer> category = new ArrayList<Integer>();
    public static boolean is_initialized = false;
    static int totaltag = 0;

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
        map.put("ItemImage", R.mipmap.technology);
        map.put("ItemText", "科技");
        lstImageitem.add(map);
        lstItem.add("科技");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.education);
        map.put("ItemText", "教育");
        lstImageitem.add(map);
        lstItem.add("教育");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.military);
        map.put("ItemText", "军事");
        lstImageitem.add(map);
        lstItem.add("军事");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.national);
        map.put("ItemText", "国内");
        lstImageitem.add(map);
        lstItem.add("国内");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.society);
        map.put("ItemText", "社会");
        lstImageitem.add(map);
        lstItem.add("社会");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.culture);
        map.put("ItemText", "文化");
        lstImageitem.add(map);
        lstItem.add("文化");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.cars);
        map.put("ItemText", "汽车");
        lstImageitem.add(map);
        lstItem.add("汽车");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.international);
        map.put("ItemText", "国际");
        lstImageitem.add(map);
        lstItem.add("国际");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.sports);
        map.put("ItemText", "体育");
        lstImageitem.add(map);
        lstItem.add("体育");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.finance);
        map.put("ItemText", "财经");
        lstImageitem.add(map);
        lstItem.add("财经");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.health);
        map.put("ItemText", "健康");
        lstImageitem.add(map);
        lstItem.add("健康");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.entertainment);
        map.put("ItemText", "娱乐");
        lstImageitem.add(map);
        lstItem.add("娱乐");
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.mipmap.beijing);
        map.put("ItemText", "北京");
        lstImageitem.add(map);
        lstItem.add("北京");
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
        map.put("ItemImage", R.mipmap.weather);
        map.put("ItemText", "天气");
        lstImageitem.add(map);
        lstItem.add("天气");
        for(int i = 0; i < lstItem.size(); i++) {
            ArrayList<String> as = new ArrayList<String>();
            lstdetail.add(as);
            category.add(new Integer(i));
        }
        totaltag = category.size();
    }

    public static void removetag(int i){
        lstdetail.remove(i);
        lstImageitem.remove(i);
        lstItem.remove(i);
        category.remove(i);
    }

    public static boolean addtag(String newtag){
        if (lstItem.indexOf(newtag) != -1)
            return false;
        totaltag = totaltag + 1;
        category.add(new Integer(totaltag));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemText", newtag);
        String imageurl = ImageFinder.findImageByKeyword(newtag);
        try {
            if (imageurl != null) {
                URL url = new URL(imageurl);
                URLConnection urlcon = url.openConnection();
                urlcon.setConnectTimeout(1000);
                urlcon.setReadTimeout(1000);
                urlcon.connect();

                Bitmap btmap = BitmapFactory.decodeStream(urlcon.getInputStream());

                if (btmap != null) map.put("ItemImage", btmap);
                else{
                    Random random = new Random();
                    int imagekey = random.nextInt(3);
                    if (imagekey == 0)
                        map.put("ItemImage", R.mipmap.blue);
                    if (imagekey == 1)
                        map.put("ItemImage", R.mipmap.orange);
                    if (imagekey == 2)
                        map.put("ItemImage", R.mipmap.green);
                }
            }
            else{
                Random random = new Random();
                int imagekey = random.nextInt(3);
                if (imagekey == 0)
                    map.put("ItemImage", R.mipmap.blue);
                if (imagekey == 1)
                    map.put("ItemImage", R.mipmap.orange);
                if (imagekey == 2)
                    map.put("ItemImage", R.mipmap.green);
            }
        }catch (Exception e){
            Random random = new Random();
            int imagekey = random.nextInt(3);
            if (imagekey == 0)
                map.put("ItemImage", R.mipmap.blue);
            if (imagekey == 1)
                map.put("ItemImage", R.mipmap.orange);
            if (imagekey == 2)
                map.put("ItemImage", R.mipmap.green);
        }


        lstImageitem.add(map);
        lstItem.add(newtag);
        ArrayList<String> as = new ArrayList<String>();
        lstdetail.add(as);
        return true;
    }

    public static boolean addNewsToTag(int tagidx, String news_id){
        if (tagidx == -1){
            readedlist.add(news_id);
            return true;
        }
        else{
            int idx = lstdetail.get(tagidx).indexOf(news_id);
            if (idx == -1){
                lstdetail.get(tagidx).add(news_id);
                return true;
            }
            else{
                lstdetail.get(tagidx).remove(idx);
                return false;
            }
        }
    }

    public static DSingleTag getNewsById(int id){
        DSingleTag current_tag = new DSingleTag(id);
        current_tag.set(lstdetail.get(id));
        return current_tag;
    }

    public static void build(int id) {
        ArrayList<String> taglist = lstdetail.get(id);
        if (taglist.size() < 10) {
            enlarge(id);
        }
    }

    public static void enlarge(int id) {
        ArrayList<String> taglist = lstdetail.get(id);
        int size = 0;
        while (true) {
            Collections.shuffle(DNewsList._news_list);
            for (DSingleNews news : DNewsList._news_list) {
                if (news.news_tag == id && !taglist.contains(news.news_id)) {
                    boolean ff = true;
                    for (String s: Data.blockwordlist) {
                        if (news.news_title.contains(s)) {
                            ff = false;
                            break;
                        }
                    }
                    if (!ff) continue;
                    taglist.add(news.news_id);
                    size++;
                    if (size >= 10) return;
                }
            }
            if (!Network.isConnected()) return;
            DNewsList.enlarge(id);
        }
    }

    public static boolean isInTagList(int id, String news_id) {
        int idx = lstdetail.get(id).indexOf(news_id);
        if (idx == -1)
            return false;
        return true;
    }
}
