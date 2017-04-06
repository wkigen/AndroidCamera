package com.vicky.android.camera;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;

import com.vicky.android.baselib.camera.CameraManager;
import com.vicky.android.gpuimage.GPUImageFilter;
import com.vicky.android.gpuimage.GPUImageRenderer;
import com.vicky.android.gpuimage.Rotation;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by vicky on 2017/4/6.
 */
public class NomalRenderer extends GPUImageRenderer {

    public NomalRenderer(GPUImageFilter filter) {
        super(filter);

        setRotation(Rotation.ROTATION_90, false, false);
    }

    @Override
    public void onSurfaceCreated(final GL10 unused, final EGLConfig config) {
        super.onSurfaceCreated(unused,config);

        CameraManager.getInstance().openFront();
        CameraManager.getInstance().setPreviewCallBack(this);

    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        super.onSurfaceChanged(gl,width,height);
        CameraManager.getInstance().startPreview(mSurfaceTexture, width, height);
    }
}
