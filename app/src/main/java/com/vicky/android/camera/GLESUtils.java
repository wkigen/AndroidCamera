package com.vicky.android.camera;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by vicky on 2017/4/5.
 */
public class GLESUtils {

    public static int createTexture()
    {
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        return texture[0];
    }

    public static int loadShader(final String strSource, final int iType) {
        int[] compiled = new int[1];
        int shader = GLES20.glCreateShader(iType);
        GLES20.glShaderSource(shader, strSource);
        GLES20.glCompileShader(shader);
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if( GLES20.glGetError()!= GLES20.GL_NO_ERROR) {
            Log.e("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(shader));
        }
        return shader;
    }

    public static int loadProgram(final String strVSource, final String strFSource) {
        int vShader;
        int fShader;
        int programId;
        int[] link = new int[1];
        vShader = loadShader(strVSource, GLES20.GL_VERTEX_SHADER);
        if (vShader == 0) {
            Log.d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        fShader = loadShader(strFSource, GLES20.GL_FRAGMENT_SHADER);
        if (fShader == 0) {
            Log.d("Load Program", "Fragment Shader Failed");
            return 0;
        }

        programId = GLES20.glCreateProgram();

        GLES20.glAttachShader(programId, vShader);
        GLES20.glAttachShader(programId, fShader);

        GLES20.glLinkProgram(programId);

        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, link, 0);
        if( GLES20.glGetError()!= GLES20.GL_NO_ERROR) {
            Log.e("Load Program Failed", "Linking Failed\n" + GLES20.glGetProgramInfoLog(programId));
        }
        GLES20.glDeleteShader(vShader);
        GLES20.glDeleteShader(fShader);
        return programId;
    }
}
