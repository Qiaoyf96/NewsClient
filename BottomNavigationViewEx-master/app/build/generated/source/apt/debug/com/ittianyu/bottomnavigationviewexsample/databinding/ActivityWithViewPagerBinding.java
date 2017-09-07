package com.ittianyu.bottomnavigationviewexsample.databinding;
import com.ittianyu.bottomnavigationviewexsample.R;
import com.ittianyu.bottomnavigationviewexsample.BR;
import android.view.View;
public class ActivityWithViewPagerBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.bnve, 1);
        sViewsWithIds.put(R.id.vp, 2);
    }
    // views
    public final android.widget.RelativeLayout activityWithViewPager;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnve;
    public final android.support.v4.view.ViewPager vp;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityWithViewPagerBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.activityWithViewPager = (android.widget.RelativeLayout) bindings[0];
        this.activityWithViewPager.setTag(null);
        this.bnve = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[1];
        this.vp = (android.support.v4.view.ViewPager) bindings[2];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityWithViewPagerBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityWithViewPagerBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityWithViewPagerBinding>inflate(inflater, com.ittianyu.bottomnavigationviewexsample.R.layout.activity_with_view_pager, root, attachToRoot, bindingComponent);
    }
    public static ActivityWithViewPagerBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityWithViewPagerBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ittianyu.bottomnavigationviewexsample.R.layout.activity_with_view_pager, null, false), bindingComponent);
    }
    public static ActivityWithViewPagerBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityWithViewPagerBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_with_view_pager_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityWithViewPagerBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}