package com.vicky.android.viewmodel.camera;

import android.Manifest;
import android.support.annotation.NonNull;

import com.vicky.android.baselib.runtimepermission.PermissionsManager;
import com.vicky.android.baselib.runtimepermission.PermissionsResultAction;
import com.vicky.android.bean.camera.FilterBean;
import com.vicky.android.gpuimage.GPUImageBilateralFilter;
import com.vicky.android.gpuimage.GPUImageBrightnessFilter;
import com.vicky.android.gpuimage.GPUImageCGAColorspaceFilter;
import com.vicky.android.gpuimage.GPUImageDilationFilter;
import com.vicky.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import com.vicky.android.gpuimage.GPUImageDissolveBlendFilter;
import com.vicky.android.gpuimage.GPUImageDivideBlendFilter;
import com.vicky.android.gpuimage.GPUImageEmbossFilter;
import com.vicky.android.gpuimage.GPUImageExclusionBlendFilter;
import com.vicky.android.gpuimage.GPUImageExposureFilter;
import com.vicky.android.gpuimage.GPUImageFalseColorFilter;
import com.vicky.android.gpuimage.GPUImageFilter;
import com.vicky.android.gpuimage.GPUImageGammaFilter;
import com.vicky.android.gpuimage.GPUImageGaussianBlurFilter;
import com.vicky.android.gpuimage.GPUImageGlassSphereFilter;
import com.vicky.android.gpuimage.GPUImageGrayscaleFilter;
import com.vicky.android.gpuimage.GPUImageHalftoneFilter;
import com.vicky.android.gpuimage.GPUImageHardLightBlendFilter;
import com.vicky.android.gpuimage.GPUImageHazeFilter;
import com.vicky.android.gpuimage.GPUImageHighlightShadowFilter;
import com.vicky.android.gpuimage.GPUImageHueBlendFilter;
import com.vicky.android.gpuimage.GPUImageHueFilter;
import com.vicky.android.gpuimage.GPUImageKuwaharaFilter;
import com.vicky.android.gpuimage.GPUImageLaplacianFilter;
import com.vicky.android.gpuimage.GPUImageLevelsFilter;
import com.vicky.android.gpuimage.GPUImageLightenBlendFilter;
import com.vicky.android.gpuimage.GPUImageLinearBurnBlendFilter;
import com.vicky.android.gpuimage.GPUImageLookupFilter;
import com.vicky.android.gpuimage.GPUImageLuminosityBlendFilter;
import com.vicky.android.gpuimage.GPUImageMixBlendFilter;
import com.vicky.android.gpuimage.GPUImageMonochromeFilter;
import com.vicky.android.gpuimage.GPUImageMultiplyBlendFilter;
import com.vicky.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import com.vicky.android.gpuimage.GPUImageNormalBlendFilter;
import com.vicky.android.gpuimage.GPUImageOpacityFilter;
import com.vicky.android.gpuimage.GPUImageOverlayBlendFilter;
import com.vicky.android.gpuimage.GPUImagePixelationFilter;
import com.vicky.android.gpuimage.GPUImagePosterizeFilter;
import com.vicky.android.gpuimage.GPUImageRGBDilationFilter;
import com.vicky.android.gpuimage.GPUImageRGBFilter;
import com.vicky.android.gpuimage.GPUImageSaturationBlendFilter;
import com.vicky.android.gpuimage.GPUImageSaturationFilter;
import com.vicky.android.gpuimage.GPUImageScreenBlendFilter;
import com.vicky.android.gpuimage.GPUImageSepiaFilter;
import com.vicky.android.gpuimage.GPUImageSharpenFilter;
import com.vicky.android.gpuimage.GPUImageSketchFilter;
import com.vicky.android.gpuimage.GPUImageSmoothToonFilter;
import com.vicky.android.gpuimage.GPUImageSobelEdgeDetection;
import com.vicky.android.gpuimage.GPUImageSobelThresholdFilter;
import com.vicky.android.gpuimage.GPUImageSoftLightBlendFilter;
import com.vicky.android.gpuimage.GPUImageSourceOverBlendFilter;
import com.vicky.android.gpuimage.GPUImageSphereRefractionFilter;
import com.vicky.android.gpuimage.GPUImageSubtractBlendFilter;
import com.vicky.android.gpuimage.GPUImageSwirlFilter;
import com.vicky.android.gpuimage.GPUImageThresholdEdgeDetection;
import com.vicky.android.gpuimage.GPUImageToneCurveFilter;
import com.vicky.android.renderer.NomalRenderer;
import com.vicky.android.view.activity.camera.MainActivity;
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
    private NomalRenderer renderer;
    private List<FilterBean> filterList = new ArrayList<>();

    public void onBindView(@NonNull MainActivity view) {
        super.onBindView(view);
        checkPermissions();
        loadFilter();
    }

    public void setRenderer(NomalRenderer renderer){
        this.renderer = renderer;
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

    private void loadFilter(){

        filterList.add(new FilterBean("GPUImageBilateralFilter", new GPUImageBilateralFilter())) ;
    }

    public List<FilterBean> getFilter(){
        return filterList;
    }

    public void selectFilter(int position){
        if (position >=0 && position < filterList.size()){
            renderer.setFilter(filterList.get(position).getFilter());
        }
    }
}
