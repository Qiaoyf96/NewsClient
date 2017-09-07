package com.ittianyu.bottomnavigationviewexsample.databinding;
import com.ittianyu.bottomnavigationviewexsample.R;
import com.ittianyu.bottomnavigationviewexsample.BR;
import android.view.View;
public class ActivityStyleBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.bnve_normal, 1);
        sViewsWithIds.put(R.id.bnve_no_animation, 2);
        sViewsWithIds.put(R.id.bnve_no_shifting_mode, 3);
        sViewsWithIds.put(R.id.bnve_no_item_shifting_mode, 4);
        sViewsWithIds.put(R.id.bnve_no_text, 5);
        sViewsWithIds.put(R.id.bnve_no_icon, 6);
        sViewsWithIds.put(R.id.bnve_no_animation_shifting_mode, 7);
        sViewsWithIds.put(R.id.bnve_no_animation_item_shifting_mode, 8);
        sViewsWithIds.put(R.id.bnve_no_animation_shifting_mode_item_shifting_mode, 9);
        sViewsWithIds.put(R.id.bnve_no_shifting_mode_item_shifting_mode_text, 10);
        sViewsWithIds.put(R.id.bnve_no_animation_shifting_mode_item_shifting_mode_text, 11);
        sViewsWithIds.put(R.id.bnve_no_shifting_mode_item_shifting_mode_and_icon, 12);
        sViewsWithIds.put(R.id.bnve_no_item_shifting_mode_icon, 13);
        sViewsWithIds.put(R.id.bnve_no_animation_shifting_mode_item_shifting_mode_icon, 14);
        sViewsWithIds.put(R.id.bnve_with_padding, 15);
        sViewsWithIds.put(R.id.bnve_center_icon_only, 16);
        sViewsWithIds.put(R.id.bnve_smaller_text, 17);
        sViewsWithIds.put(R.id.bnve_bigger_icon, 18);
        sViewsWithIds.put(R.id.bnve_custom_typeface, 19);
        sViewsWithIds.put(R.id.bnve_icon_selector, 20);
        sViewsWithIds.put(R.id.bnve_icon_margin_top, 21);
        sViewsWithIds.put(R.id.bnve_unchecked_first_time, 22);
    }
    // views
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveBiggerIcon;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveCenterIconOnly;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveCustomTypeface;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveIconMarginTop;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveIconSelector;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoAnimation;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoAnimationItemShiftingMode;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoAnimationShiftingMode;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoAnimationShiftingModeItemShiftingMode;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoAnimationShiftingModeItemShiftingModeIcon;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoAnimationShiftingModeItemShiftingModeText;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoIcon;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoItemShiftingMode;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoItemShiftingModeIcon;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoShiftingMode;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoShiftingModeItemShiftingModeAndIcon;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoShiftingModeItemShiftingModeText;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNoText;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveNormal;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveSmallerText;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveUncheckedFirstTime;
    public final com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bnveWithPadding;
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityStyleBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds);
        this.bnveBiggerIcon = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[18];
        this.bnveCenterIconOnly = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[16];
        this.bnveCustomTypeface = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[19];
        this.bnveIconMarginTop = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[21];
        this.bnveIconSelector = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[20];
        this.bnveNoAnimation = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[2];
        this.bnveNoAnimationItemShiftingMode = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[8];
        this.bnveNoAnimationShiftingMode = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[7];
        this.bnveNoAnimationShiftingModeItemShiftingMode = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[9];
        this.bnveNoAnimationShiftingModeItemShiftingModeIcon = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[14];
        this.bnveNoAnimationShiftingModeItemShiftingModeText = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[11];
        this.bnveNoIcon = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[6];
        this.bnveNoItemShiftingMode = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[4];
        this.bnveNoItemShiftingModeIcon = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[13];
        this.bnveNoShiftingMode = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[3];
        this.bnveNoShiftingModeItemShiftingModeAndIcon = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[12];
        this.bnveNoShiftingModeItemShiftingModeText = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[10];
        this.bnveNoText = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[5];
        this.bnveNormal = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[1];
        this.bnveSmallerText = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[17];
        this.bnveUncheckedFirstTime = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[22];
        this.bnveWithPadding = (com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx) bindings[15];
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
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

    public static ActivityStyleBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityStyleBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityStyleBinding>inflate(inflater, com.ittianyu.bottomnavigationviewexsample.R.layout.activity_style, root, attachToRoot, bindingComponent);
    }
    public static ActivityStyleBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityStyleBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ittianyu.bottomnavigationviewexsample.R.layout.activity_style, null, false), bindingComponent);
    }
    public static ActivityStyleBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityStyleBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_style_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityStyleBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}