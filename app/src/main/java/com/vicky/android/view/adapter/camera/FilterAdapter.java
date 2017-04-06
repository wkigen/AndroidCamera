package com.vicky.android.view.adapter.camera;

import android.content.Context;

import com.vicky.android.R;
import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;
import com.vicky.android.bean.camera.FilterBean;

/**
 * Created by vicky on 2017/4/6.
 */
public class FilterAdapter extends AdapterViewAdapter<FilterBean> {

    public FilterAdapter(Context context) {
        super(context, R.layout.item_filter);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, FilterBean model) {
        viewHolderHelper.setText(R.id.tv_name, model.getName());
    }

    /**
     * 制定item子元素点击事件/长按事件
     */
    @Override
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {

    }
}
