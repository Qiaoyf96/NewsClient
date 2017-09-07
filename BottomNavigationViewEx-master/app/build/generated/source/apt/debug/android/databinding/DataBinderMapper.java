
package android.databinding;
import com.ittianyu.bottomnavigationviewexsample.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 25;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.ittianyu.bottomnavigationviewexsample.R.layout.activity_badge_view:
                    return com.ittianyu.bottomnavigationviewexsample.databinding.ActivityBadgeViewBinding.bind(view, bindingComponent);
                case com.ittianyu.bottomnavigationviewexsample.R.layout.activity_main:
                    return com.ittianyu.bottomnavigationviewexsample.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case com.ittianyu.bottomnavigationviewexsample.R.layout.activity_with_view_pager:
                    return com.ittianyu.bottomnavigationviewexsample.databinding.ActivityWithViewPagerBinding.bind(view, bindingComponent);
                case com.ittianyu.bottomnavigationviewexsample.R.layout.frag_base:
                    return com.ittianyu.bottomnavigationviewexsample.databinding.FragBaseBinding.bind(view, bindingComponent);
                case com.ittianyu.bottomnavigationviewexsample.R.layout.activity_style:
                    return com.ittianyu.bottomnavigationviewexsample.databinding.ActivityStyleBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -1252126915: {
                if(tag.equals("layout/activity_badge_view_0")) {
                    return com.ittianyu.bottomnavigationviewexsample.R.layout.activity_badge_view;
                }
                break;
            }
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return com.ittianyu.bottomnavigationviewexsample.R.layout.activity_main;
                }
                break;
            }
            case 1708421832: {
                if(tag.equals("layout/activity_with_view_pager_0")) {
                    return com.ittianyu.bottomnavigationviewexsample.R.layout.activity_with_view_pager;
                }
                break;
            }
            case 753483210: {
                if(tag.equals("layout/frag_base_0")) {
                    return com.ittianyu.bottomnavigationviewexsample.R.layout.frag_base;
                }
                break;
            }
            case 1840176183: {
                if(tag.equals("layout/activity_style_0")) {
                    return com.ittianyu.bottomnavigationviewexsample.R.layout.activity_style;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}