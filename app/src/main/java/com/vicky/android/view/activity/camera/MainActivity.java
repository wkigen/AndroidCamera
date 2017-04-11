package com.vicky.android.view.activity.camera;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vicky.android.R;
import com.vicky.android.baselib.camera.CameraManager;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.widget.SimplePopupWindow;
import com.vicky.android.view.activity.base.BaseActivity;
import com.vicky.android.view.adapter.camera.FilterAdapter;
import com.vicky.android.view.view.CameraGLSurfaceView;
import com.vicky.android.viewmodel.camera.MainVM;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:  wangqiji
 * Description:
 * Date:
 */
public class MainActivity extends BaseActivity<MainActivity, MainVM> implements IView {

    @Bind(R.id.surfaceView)
    CameraGLSurfaceView surfaceView;
    @Bind(R.id.im_show_filter)
    ImageButton imShowFilter;
    @Bind(R.id.fl_main)
    FrameLayout flMain;

    private FilterAdapter adapter;
    private SimplePopupWindow filterPW;

    @Override
    protected int tellMeLayout() {
        return R.layout.camera_activity_main;
    }

    @Override
    public Class<MainVM> getViewModelClass() {
        return MainVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        adapter = new FilterAdapter(this);
        adapter.setDatas(getViewModel().getFilter());

        imShowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilter();
            }
        });

        getViewModel().setRenderer(surfaceView.getRenderer());
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        CameraManager.getInstance().stopPreview();
    }

    private void showFilter(){
        if (filterPW == null){

            View view  = getLayoutInflater().inflate(R.layout.item_filter_list, null);
            LinearLayout llMain = (LinearLayout)view.findViewById(R.id.ll_main);
            ListView listViewFilter = (ListView)view.findViewById(R.id.ls_filter);
            listViewFilter.setAdapter(adapter);
            listViewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getViewModel().selectFilter(position);
                    filterPW.dismiss();
                }
            });
            llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterPW.dismiss();
                }
            });
            filterPW = new SimplePopupWindow(activity, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            filterPW.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            filterPW.setOutsideTouchable(true);
            filterPW.setAnimationStyle(R.style.popwin_anim_right_style);
        }

        filterPW.showAtLocation(flMain, Gravity.RIGHT, 0, 0, false);
    }

    @Override
    public void onBackPressed() {
        if (filterPW!=null) {
            filterPW.dismiss();
            filterPW = null;
        }else {
            super.onBackPressed();
        }
    }

}
