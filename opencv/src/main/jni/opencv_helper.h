#include <jni.h>

#ifndef _OpenCVHelper_H
#define _OpenCVHelper_H
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jintArray JNICALL Java_org_opencv_jni_OpencvHelper_gray
        (JNIEnv *, jclass, jintArray, jint, jint);

JNIEXPORT void JNICALL Java_org_opencv_jni_OpencvHelper_checkSkin(
        JNIEnv *env, jclass obj, jintArray, jint, jint);

JNIEXPORT void JNICALL Java_org_opencv_jni_OpencvHelper_dermabrasion(
        JNIEnv *env, jclass obj, jintArray, jint, jint,jint);


#ifdef __cplusplus
}
#endif
#endif