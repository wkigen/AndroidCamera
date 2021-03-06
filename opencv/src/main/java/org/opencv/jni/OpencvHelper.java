package org.opencv.jni;

/**
 * Created by vicky on 2017/4/11.
 */
public class OpencvHelper {

    static {
        System.loadLibrary("opencv");
    }

    public static native int[] gray(int[] buf, int w, int h);
    public static native void checkSkin(int[] buf, int w, int h,int lv);
    public static native void dermabrasion(int[] buf, int w, int h,int lv);
    public static native void dermabrasionRGBA(int[] buf, int w, int h,int lv);
}
