package com.example.qrdetect;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class arRenderer implements GLSurfaceView.Renderer {
    private int mWidth;
    private int mHeight;

    private float mAngle =0;
    private float mTransY=0;
    private float mTransX=0;

    private static final float Z_NEAR = 1f;
    private static final float Z_FAR = 40f;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    public Czajnik mCzajnik;
    private float angleVX, angleVY, angleVZ, elevation=0;


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES30.glClearColor(0f, 0f, 0f, 0f);
        mCzajnik = new Czajnik();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        mWidth = i;
        mHeight = i1;

        GLES30.glViewport(0, 0, mWidth, mHeight);
        float aspect = (float) mWidth / mHeight;

        Matrix.perspectiveM(mProjectionMatrix, 0, 53.13f, aspect, Z_NEAR, Z_FAR);
    }

    private float[] rotCameraX = {
            1, 0, 0, 0,
            0, (float)Math.cos(angleVX * Math.PI / 180.0),(float) Math.sin(angleVX * Math.PI / 180.0), 0,
            0, (float)-Math.sin(angleVX * Math.PI / 180.0), (float)Math.cos(angleVX * Math.PI / 180.0), 0,
            0, 0, 0, 1};

    private float[] rotCameraY = {
            (float)Math.cos(angleVY * Math.PI / 180.0), 0, (float)-Math.sin(angleVY * Math.PI / 180.0), 0,
            0, 1, 0, 0,
            (float)Math.sin(angleVY * Math.PI / 180.0), 0, (float)Math.cos(angleVY * Math.PI / 180.0), 0,
            0, 0, 0, 1
    };

    private float[] rotCameraZ = {
            (float)Math.cos(angleVZ * Math.PI / 180.0), (float)Math.sin(angleVZ * Math.PI / 180.0), 0, 0,
            (float)-Math.sin(angleVZ * Math.PI / 180.0), (float)Math.cos(angleVZ * Math.PI / 180.0), 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };

    @Override
    public void onDrawFrame(GL10 gl10) {

        elevation = calcDist(MainActivity.Companion.getResultPoints()[2], MainActivity.Companion.getResultPoints()[6], MainActivity.Companion.getResultPoints()[3], MainActivity.Companion.getResultPoints()[7])/200;
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        // Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, elevation, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Create a rotation and translation for the cube
        Matrix.setIdentityM(mRotationMatrix, 0);
        //move the cube up/down and left/right
        Matrix.translateM(mRotationMatrix, 0, mTransX, mTransY, 0);

        //mangle is how fast, x,y,z which directions it rotates.
        Matrix.rotateM(mRotationMatrix, 0, mAngle, 0.4f, 1.0f, 0.6f);

        // combine the model with the view matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);

        // combine the model-view with the projection matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        mCzajnik.draw(mMVPMatrix);
    }

    private float calcDist(float x1, float y1, float x2, float y2)
    {
        return (float)Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
    }
}
