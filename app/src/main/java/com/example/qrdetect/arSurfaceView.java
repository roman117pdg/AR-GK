package com.example.qrdetect;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class arSurfaceView extends GLSurfaceView {

    public arSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);

        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);

        arRenderer render = new arRenderer();
        setRenderer(render);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
