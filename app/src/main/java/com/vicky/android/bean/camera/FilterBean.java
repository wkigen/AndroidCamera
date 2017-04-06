package com.vicky.android.bean.camera;

import com.vicky.android.gpuimage.GPUImageFilter;

import java.io.Serializable;

/**
 * Created by vicky on 2017/4/6.
 */
public class FilterBean implements Serializable {

    private String name;
    private GPUImageFilter filter;

    public FilterBean(String name,GPUImageFilter filter){
        this.name = name;
        this.filter = filter;
    }

    public GPUImageFilter getFilter() {
        return filter;
    }

    public void setFilter(GPUImageFilter filter) {
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
