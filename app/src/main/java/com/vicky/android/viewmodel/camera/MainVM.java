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
        filterList.add(new FilterBean("GPUImageDilationFilter", new GPUImageDilationFilter())) ;
        filterList.add(new FilterBean("GPUImageDirectionalSobelEdgeDetectionFilter", new GPUImageDirectionalSobelEdgeDetectionFilter())) ;
        filterList.add(new FilterBean("GPUImageDissolveBlendFilter", new GPUImageDissolveBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageDivideBlendFilter", new GPUImageDivideBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageEmbossFilter", new GPUImageEmbossFilter())) ;
        filterList.add(new FilterBean("GPUImageExclusionBlendFilter", new GPUImageExclusionBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageExposureFilter", new GPUImageExposureFilter())) ;
        filterList.add(new FilterBean("GPUImageFalseColorFilter", new GPUImageFalseColorFilter())) ;
        filterList.add(new FilterBean("GPUImageFilter", new GPUImageFilter())) ;
        filterList.add(new FilterBean("GPUImageGammaFilter", new GPUImageGammaFilter())) ;
        filterList.add(new FilterBean("GPUImageGaussianBlurFilter", new GPUImageGaussianBlurFilter())) ;
        filterList.add(new FilterBean("GPUImageGlassSphereFilter", new GPUImageGlassSphereFilter())) ;
        filterList.add(new FilterBean("GPUImageGrayscaleFilter", new GPUImageGrayscaleFilter())) ;
        filterList.add(new FilterBean("GPUImageHalftoneFilter", new GPUImageHalftoneFilter())) ;
        filterList.add(new FilterBean("GPUImageHardLightBlendFilter", new GPUImageHardLightBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageHazeFilter", new GPUImageHazeFilter())) ;
        filterList.add(new FilterBean("GPUImageHighlightShadowFilter", new GPUImageHighlightShadowFilter())) ;
        filterList.add(new FilterBean("GPUImageHueBlendFilter", new GPUImageHueBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageHueFilter", new GPUImageHueFilter())) ;
        filterList.add(new FilterBean("GPUImageKuwaharaFilter", new GPUImageKuwaharaFilter())) ;
        filterList.add(new FilterBean("GPUImageLaplacianFilter", new GPUImageLaplacianFilter())) ;
        filterList.add(new FilterBean("GPUImageLevelsFilter", new GPUImageLevelsFilter())) ;
        filterList.add(new FilterBean("GPUImageLightenBlendFilter", new GPUImageLightenBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageLinearBurnBlendFilter", new GPUImageLinearBurnBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageLookupFilter", new GPUImageLookupFilter())) ;
        filterList.add(new FilterBean("GPUImageLuminosityBlendFilter", new GPUImageLuminosityBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageMonochromeFilter", new GPUImageMonochromeFilter())) ;
        filterList.add(new FilterBean("GPUImageMultiplyBlendFilter", new GPUImageMultiplyBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageNonMaximumSuppressionFilter", new GPUImageNonMaximumSuppressionFilter())) ;
        filterList.add(new FilterBean("GPUImageNormalBlendFilter", new GPUImageNormalBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageOpacityFilter", new GPUImageOpacityFilter())) ;
        filterList.add(new FilterBean("GPUImageOverlayBlendFilter", new GPUImageOverlayBlendFilter())) ;
        filterList.add(new FilterBean("GPUImagePixelationFilter", new GPUImagePixelationFilter())) ;
        filterList.add(new FilterBean("GPUImagePosterizeFilter", new GPUImagePosterizeFilter())) ;
        filterList.add(new FilterBean("GPUImageRGBDilationFilter", new GPUImageRGBDilationFilter())) ;
        filterList.add(new FilterBean("GPUImageRGBFilter", new GPUImageRGBFilter())) ;
        filterList.add(new FilterBean("GPUImageSaturationBlendFilter", new GPUImageSaturationBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageSaturationFilter", new GPUImageSaturationFilter())) ;
        filterList.add(new FilterBean("GPUImageScreenBlendFilter", new GPUImageScreenBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageSepiaFilter", new GPUImageSepiaFilter()));
        filterList.add(new FilterBean("GPUImageSharpenFilter", new GPUImageSharpenFilter())) ;
        filterList.add(new FilterBean("GPUImageSketchFilter", new GPUImageSketchFilter())) ;
        filterList.add(new FilterBean("GPUImageSmoothToonFilter", new GPUImageSmoothToonFilter())) ;
        filterList.add(new FilterBean("GPUImageSobelEdgeDetection", new GPUImageSobelEdgeDetection())) ;
        filterList.add(new FilterBean("GPUImageSobelThresholdFilter", new GPUImageSobelThresholdFilter())) ;
        filterList.add(new FilterBean("GPUImageSoftLightBlendFilter", new GPUImageSoftLightBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageSourceOverBlendFilter", new GPUImageSourceOverBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageSphereRefractionFilter", new GPUImageSphereRefractionFilter())) ;
        filterList.add(new FilterBean("GPUImageSubtractBlendFilter", new GPUImageSubtractBlendFilter())) ;
        filterList.add(new FilterBean("GPUImageSwirlFilter", new GPUImageSwirlFilter())) ;
        filterList.add(new FilterBean("GPUImageThresholdEdgeDetection", new GPUImageThresholdEdgeDetection())) ;
        filterList.add(new FilterBean("GPUImageToneCurveFilter", new GPUImageToneCurveFilter())) ;
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
