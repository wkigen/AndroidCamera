package com.vicky.android.view.activity.camera;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.vicky.android.R;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.view.activity.base.BaseActivity;
import com.vicky.android.camera.CameraGLSurfaceView;
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
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onStop(){
        super.onStop();

    }
}
