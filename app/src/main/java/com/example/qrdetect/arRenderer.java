package com.example.qrdetect;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class arRenderer implements GLSurfaceView.Renderer {
    private int mWidth;
    private int mHeight;

    private float mTransY=0;
    private float mTransX=0;

    private static final float Z_NEAR = 1f;
    private static final float Z_FAR = 40f;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    public Czajnik mCzajnik;
    private float angleVX=0, angleVY=0, angleVZ=0, elevation=0;


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

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] points = MainActivity.Companion.getResultPoints();
        float dist = calcDist(points[2],points[6], points[3], points[7]);
        float QRcenterX = (1/((points[3] + points[5])/2f))*1000;
        float QRcenterY = (1/((points[0] + points[2])/2f))*1000;
        elevation = (1/dist)*2000;

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        // Set the camera position=4
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f,  elevation, -QRcenterX+3, -QRcenterY+2, 0f, 0f, 1.0f, 0.0f);
        // Create a rotation and translation for the cube
        Matrix.setIdentityM(mRotationMatrix, 0);

        //move the cube up/down and left/right
        Matrix.translateM(mRotationMatrix, 0, 0, 0, 0);
        //rotate x
        Matrix.rotateM(mRotationMatrix, 0, angleVX, 1.0f, 0,0);
        //rotate y
        Matrix.rotateM(mRotationMatrix, 0, angleVY, 0, 1.0f, 0);
        //rotate z
        Matrix.rotateM(mRotationMatrix, 0, angleVZ, 0, 0, 1.0f);

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
