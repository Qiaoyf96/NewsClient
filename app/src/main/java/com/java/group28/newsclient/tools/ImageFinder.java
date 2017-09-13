package com.java.group28.newsclient.tools;

import android.os.StrictMode;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ImageFinder {
    private static ArrayList<String> urlarray = new ArrayList<String>();
    public synchronized static String findImageByKeyword(String keyword){
        try{
            String finalurl;
            String content;
            urlarray = new ArrayList<String>();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            keyword = keyword.replace(" ", "").replace("\t","").replace("　", "");
            String searchurl = "http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word=" + keyword + "&cg=star&pn=0&rn=30&itg=0&z=0&fr=&width=&height=&lm=-1&ic=0&s=0&st=-1&gsm=0";
            content = Http.sendGet(searchurl);
            Pattern pt = Pattern.compile("thumbURL\"\\:\"(.+?)\"");
            Matcher match = pt.matcher(content);
            while(match.find()){
                String s = match.group(1);
                urlarray.add(s);
            }
            int idx = 1;
            if (urlarray.size() < 2) idx = urlarray.size() - 1;
            finalurl = urlarray.get(idx);

            URL url = new URL(finalurl);
            return finalurl;
        }catch (Exception e){
            return null;
        }
    }
}
