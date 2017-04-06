package com.vicky.android.camera;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vicky on 2017/4/5.
 */
public class MousikaEngine {

    private static MousikaEngine instance;

    private MousikaEngine() {
    }

    public static synchronized MousikaEngine getInstance() {
        if(instance == null) {
            instance = new MousikaEngine();
            instance.init();
        }
        return instance;
    }

    private void init() {

    }

}
