LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)


OpenCV_INSTALL_MODULES:=on
OPENCV_CAMERA_MODULES:=off


OPENCV_LIB_TYPE:=STATIC


ifeq ("$(wildcard $(OPENCV_MK_PATH))","")
include opencv\jni\OpenCV.mk
else  
include $(OPENCV_MK_PATH)  
endif 


LOCAL_MODULE    := opencv
LOCAL_SRC_FILES :=

LOCAL_LDLIBS    += -lm -llog 
			
include $(BUILD_SHARED_LIBRARY) 