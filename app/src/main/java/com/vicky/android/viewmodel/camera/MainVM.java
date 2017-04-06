package com.vicky.android.viewmodel.camera;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.vicky.android.baselib.camera.CameraManager;
import com.vicky.android.baselib.runtimepermission.PermissionsManager;
import com.vicky.android.baselib.runtimepermission.PermissionsResultAction;
import com.vicky.android.view.activity.camera.MainActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Author:  wangqiji
 * Description:
 * Date:
 ************************************************************/
public class MainVM extends AbstractViewModel<MainActivity> {

    private boolean getPermisions = false;

    public void onBindView(@NonNull MainActivity view) {
        super.onBindView(view);
        checkPermissions();
    }

    public void checkPermissions(){

        if (getView() != null){

            final List<String> list = new ArrayList<>();

            if (!PermissionsManager.getInstance().hasPermission(getView(), Manifest.permission.CAMERA)) {
                list.add(Manifest.permission.CAMERA);
            }
            if (!PermissionsManager.getInstance().hasPermission(getView(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                list.add(Manifest.permission.CAMERA);
            }

            if (list.size() > 0) {

                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getView(), list.toArray(new String[list.size()]), new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                        getPermisions = true;
                    }

                    @Override
                    public void onDenied(String permission) {
                        checkPermissions();
                    }
                });
            } else {

                getPermisions = true;
            }
        }
    }

}
