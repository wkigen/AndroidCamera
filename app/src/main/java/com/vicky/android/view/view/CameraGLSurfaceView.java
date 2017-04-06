package com.vicky.android.view.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.vicky.android.baselib.camera.CameraManager;

import com.vicky.android.renderer.NomalRenderer;
import com.vicky.android.gpuimage.GPUImageFilter;

/**
 * Created by vicky on 2017/4/3.
 */
public class CameraGLSurfaceView extends GLSurfaceView {

    private Context             context;
    private NomalRenderer renderer;

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        renderer = new NomalRenderer((Activity)context,this,new GPUImageFilter());

        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        setRenderer(renderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        requestRender();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        CameraManager.getInstance().stopPreview();
    }

    public NomalRenderer getRenderer(){
        return renderer;
    }
}
