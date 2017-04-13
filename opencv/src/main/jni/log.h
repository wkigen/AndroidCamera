#ifndef _LOG_H_
#define _LOG_H_


#include <android/log.h>


#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, "native", __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG ,  "native", __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO  ,  "native", __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN  ,  "native", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR  , "native", __VA_ARGS__)

#endif