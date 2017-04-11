#include <jni.h>

#ifndef _OpenCVHelper_H
#define _OpenCVHelper_H
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jintArray JNICALL Java_cn_edu_zafu_opencv_OpenCVHelper_gray
        (JNIEnv *, jclass, jintArray, jint, jint);

#ifdef __cplusplus
}
#endif
#endif