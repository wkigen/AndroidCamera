package com.vicky.android.renderer;

import android.app.Activity;
import android.graphics.SurfaceTexture;

import com.vicky.android.baselib.camera.CameraManager;
import com.vicky.android.gpuimage.GPUImageFilter;
import com.vicky.android.gpuimage.GPUImageRenderer;
import com.vicky.android.gpuimage.Rotation;
import com.vicky.android.utils.GLESUtils;
import com.vicky.android.view.view.CameraGLSurfaceView;

import org.opencv.jni.OpencvHelper;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by vicky on 2017/4/6.
 */
public class NomalRenderer extends GPUImageRenderer implements SurfaceTexture.OnFrameAvailableListener{

    private Activity activity;
    private CameraGLSurfaceView surfaceView;

    public NomalRenderer(Activity activity,CameraGLSurfaceView surfaceView,GPUImageFilter filter) {
        super(filter);
        this.activity = activity;
        this.surfaceView = surfaceView;
        setRotation(Rotation.ROTATION_90, false, false);
    }

    @Override
    public void onSurfaceCreated(final GL10 unused, final EGLConfig config) {
        super.onSurfaceCreated(unused,config);

        CameraManager.getInstance().openFront();
        CameraManager.getInstance().setPreviewCallBack(this);
        CameraManager.getInstance().setDisplayOrientation(activity);

        mSurfaceTexture = new SurfaceTexture(GLESUtils.createTexture());
        mSurfaceTexture.setOnFrameAvailableListener(this);
    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        super.onSurfaceChanged(gl,width,height);
        CameraManager.getInstance().startPreview(mSurfaceTexture, width, height);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        surfaceView.requestRender();
    }

    @Override
    protected void operateDate(IntBuffer intBuffer,int width,int height){
        //OpencvHelper.gray(intBuffer.array(), width, height);
        OpencvHelper.dermabrasion(intBuffer.array(), width, height,100);
    }

}
