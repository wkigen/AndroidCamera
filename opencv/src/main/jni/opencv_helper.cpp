#include "opencv_helper.h"
#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
#include "conver.h"
#include "utils.h"
#include <Math.h>
#include "log.h"

using namespace cv;
using namespace vk;




extern "C" {

JNIEXPORT jintArray JNICALL Java_org_opencv_jni_OpencvHelper_gray(
        JNIEnv *env, jclass obj, jintArray buf, int w, int h);



JNIEXPORT jintArray JNICALL Java_org_opencv_jni_OpencvHelper_gray(
        JNIEnv *env, jclass obj, jintArray buf, int w, int h) {

    jint *cbuf;
    cbuf = env->GetIntArrayElements(buf, JNI_FALSE );
    if (cbuf == NULL) {
        return 0;
    }

    Mat imgData(h, w, CV_8UC4, (unsigned char *) cbuf);

    uchar* ptr = imgData.ptr(0);
    for(int i = 0; i < w*h; i ++){
        //计算公式：Y(亮度) = 0.299*R + 0.587*G + 0.114*B
        int grayScale = (int)(ptr[4*i+2]*0.299 + ptr[4*i+1]*0.587 + ptr[4*i+0]*0.114);
        ptr[4*i+1] = grayScale;
        ptr[4*i+2] = grayScale;
        ptr[4*i+0] = grayScale;
    }

    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, cbuf);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}


JNIEXPORT void JNICALL Java_org_opencv_jni_OpencvHelper_dermabrasion(
        JNIEnv *env, jclass obj, jintArray buf, int width, int height,int level)
{

    jint *rgbData = (jint*) (env->GetPrimitiveArrayCritical( buf, JNI_FALSE));
    if (rgbData == NULL) {
        return;
    }

    long len = width * height;

    Mat imgData(height, width, CV_8UC4, (unsigned char *) rgbData);
    Mat yuvData(width,height,CV_8UC3,Scalar(0,0,0));
    Mat skinData(width,height,CV_8UC1,Scalar(0));
    Mat integralData;
    Mat integralSqrData;
    //转换成YCbCr
    Conver::RGBAToYCbCr(imgData.ptr(0),yuvData.ptr(0),len);
    //检测皮肤
    Utils::checkIsSkin(imgData.ptr(0),skinData.ptr(0),width,height);
    //算出积分图
    integral(yuvData,integralData,integralSqrData,CV_32S);

    int radius = width > height ? width * 0.02 : height * 0.02;

    uint8_t* skinPtr = skinData.ptr(0);
    uint8_t* yuvPtr = yuvData.ptr(0);
    uint8_t* imgPtr = imgData.ptr(0);
    uint32_t* integralPtr = (uint32_t*)integralData.ptr(0);
    uint32_t* integralSqrPtr = (uint32_t*)integralSqrData.ptr(0);

    for(int i = 1; i < height; i++){
        for(int j = 1; j < width; j++){
            int offset = i * width + j;
            if(skinPtr[offset] == 255){
                int iMax = i + radius >= height-1 ? height-1 : i + radius;
                int jMax = j + radius >= width-1 ? width-1 :j + radius;
                int iMin = i - radius <= 1 ? 1 : i - radius;
                int jMin = j - radius <= 1 ? 1 : j - radius;

                int squar = (iMax - iMin + 1)*(jMax - jMin + 1);
                int i4 = (iMax*width+jMax)*3;
                int i3 = ((iMin-1)*width+(jMin-1))*3;
                int i2 = (iMax*width+(jMin-1))*3;
                int i1 = ((iMin-1)*width+jMax)*3;

                float m = (integralPtr[i4] + integralPtr[i3] - integralPtr[i2] - integralPtr[i1]) / squar;

                float v = (integralSqrPtr[i4] + integralSqrPtr[i3] - integralSqrPtr[i2] - integralSqrPtr[i1]) / squar - m*m;

                float k = v / (v + level);

                yuvPtr[offset * 3] = ceil(m - k * m + k * yuvPtr[offset * 3]);
            }
        }
    }

    //转换成RGBA
    Conver::YCbCrToRGBA(yuvPtr,imgPtr,len);

    env->ReleasePrimitiveArrayCritical( buf, rgbData, 0);

}

JNIEXPORT void JNICALL Java_org_opencv_jni_OpencvHelper_checkSkin(
        JNIEnv *env, jclass obj, jintArray buf, int width, int height)
{
    jint *rgbData = (jint*) (env->GetPrimitiveArrayCritical( buf, JNI_FALSE));
    if (rgbData == NULL) {
        return;
    }

    Mat imgData(height, width, CV_8UC4, (unsigned char *) rgbData);
    Mat skinData(width,height,CV_8UC1,Scalar(0));
    uint8_t* skinPtr = skinData.ptr(0);
    uint8_t* imgPtr = imgData.ptr(0);

    //检测皮肤
    Utils::checkIsSkin(imgData.ptr(0),skinData.ptr(0),width,height);

    for (int ii = 0; ii < height; ++ii) {
        for (int jj = 0; jj < width; ++jj) {
            int offset = ii * width + jj;
            int offset2 =offset *4;
            if(skinPtr[offset] == 255){
                imgPtr[offset2] = 255;
                imgPtr[offset2 + 1] = 255;
                imgPtr[offset2 + 2] = 255;
                imgPtr[offset2 + 3] = 255;
            } else{
                imgPtr[offset2 ] = 0;
                imgPtr[offset2 + 1] = 0;
                imgPtr[offset2+ 2] = 0;
                imgPtr[offset2 + 3] = 255;
            }
        }
    }

    env->ReleasePrimitiveArrayCritical( buf, rgbData, 0);
}

}