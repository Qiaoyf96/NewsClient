package com.newsclient.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.newsclient.R;
import com.newsclient.data.DSingleNews;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/** * Created by zyh on 2015/9/15. */
public class PicGetter {
    private Context mContext;
    private Bitmap mBitmap;

    public PicGetter(Context context) {
        this.mContext= context;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    /**     * 检查复用的ImageView中是否存在其他图片的下载任务，如果存在就取消并且返回ture 否则返回 false
     * @param imageUrl
     * @param imageView
     * @return     */
    private boolean cancelPotentialTask(String imageUrl, ImageView imageView) {
        DownLoadTask task = getDownLoadTask(imageView);
        if (task != null) {
            String url = task.url;
            if (url == null || !url.equals(imageUrl)){
                task.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    private Bitmap getBitmapDrawableFromMemoryCache(DSingleNews news) {
        return news.news_intropic.bitmap;
    }

    /**     * 添加图片到缓存中     * @param imageUrl     * @param drawable     */
    private void addBitmapDrawableToMemoryCache(DSingleNews news,Bitmap drawable) {
        if (getBitmapDrawableFromMemoryCache(news) == null ) {
            news.news_intropic.bitmap = drawable;
        }
    }

    /**     * 获取当前ImageView 的图片下载任务     * @param imageView     * @return     */
    private DownLoadTask getDownLoadTask(ImageView imageView){
        if (imageView != null){
            Drawable drawable  = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                return  ((AsyncDrawable) drawable).getDownLoadTaskFromAsyncDrawable();
            }
        }
        return null;
    }

    /**     * 新建一个类 继承BitmapDrawable     * 目的： BitmapDrawable 和DownLoadTask建立弱引用关联     */
    class AsyncDrawable extends  BitmapDrawable{
        private  WeakReference<DownLoadTask> downLoadTaskWeakReference;
        public AsyncDrawable(Resources resources, Bitmap bitmap, DownLoadTask downLoadTask) {
            super(resources,bitmap);
            downLoadTaskWeakReference = new WeakReference<DownLoadTask>(downLoadTask);
        }
        private DownLoadTask getDownLoadTaskFromAsyncDrawable() {
            return downLoadTaskWeakReference.get();
        }
    }

    /**     * 异步加载图片     * DownLoadTash 和 ImagaeView建立弱引用关联。     */
    class DownLoadTask extends AsyncTask<DSingleNews,Void,Bitmap> {
        String url;
        private WeakReference<ImageView> imageViewWeakReference;
        public DownLoadTask(ImageView imageView) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(DSingleNews... params) {
            DSingleNews news = params[0];
            Bitmap bitmap = downLoadBitmap(news.news_pictures);
            BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(),bitmap);
            addBitmapDrawableToMemoryCache(news,bitmap);
            return bitmap;
        }

        /**         * 验证ImageView 中的下载任务是否相同 如果相同就返回         * @return         */
        private ImageView getAttachedImageView() {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null) {
                DownLoadTask task = getDownLoadTask(imageView);
                if (this == task ) {
                    return  imageView;
                }
            }
            return null;
        }

        /**         * 下载图片 这里使用google 推荐使用的OkHttp         * @param url         * @return         */
        private Bitmap downLoadBitmap(String uri) {
            Bitmap bitmap;
            InputStream is;
            try {

                is = GetImageByUrl(uri);

                bitmap = BitmapFactory.decodeStream(is);
                is.close();

                return bitmap;

            } catch (MalformedURLException e) {

            } catch (IOException e) {
            }
            return null;
        }

        private InputStream GetImageByUrl(String uri) throws MalformedURLException {
            URL url = new URL(uri);
            URLConnection conn;
            InputStream is;
            try {
                conn = url.openConnection();
                conn.connect();
                is = conn.getInputStream();

                // 或者用如下方法

                // is=(InputStream)url.getContent();
                return is;
            } catch (IOException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            super.onPostExecute(bm);
            ImageView imageView = getAttachedImageView();
            if ( imageView != null && bm != null){
                imageView.setImageBitmap(bm);
            }
        }
    }

    public void setImageView(ImageView iv, DSingleNews news) {
        Bitmap bm = getBitmapDrawableFromMemoryCache(news);
        if (bm != null) {
            iv.setImageBitmap(bm);
        } else if (cancelPotentialTask(news.news_pictures, iv)) {            //执行下载操作
            DownLoadTask task = new DownLoadTask(iv);
            AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(),mBitmap,task);
            iv.setImageDrawable(asyncDrawable);
            task.execute(news);
        }
    }
}




