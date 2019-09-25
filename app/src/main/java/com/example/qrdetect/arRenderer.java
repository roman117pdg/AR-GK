package com.example.qrdetect;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.widget.FrameLayout;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class arRenderer implements GLSurfaceView.Renderer {
    private int mWidth;
    private int mHeight;

    private static final float Z_NEAR = 1f;
    private static final float Z_FAR = 40f;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    public Czajnik mCzajnik;
    public Ludzik mLudzik;


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES30.glClearColor(0f, 0f, 0f, 0f);
        mCzajnik = new Czajnik();
        mLudzik = new Ludzik();
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

        //PUNKTY NAROŻNIKÓW QRA
        float[] points = MainActivity.Companion.getResultPoints();
        String val_str = MainActivity.Companion.getString_val();
        Log.e("string",val_str);

        //ODLEGŁOŚĆ DO KAMERKI
        float dist;
        try {
            dist = calcDist(points[2],points[3], points[6], points[7]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Result Points from QR detector are out of range!");
            dist = 500;
        }
        float elevation = (1 / dist) * 2000;

        //WSPÓŁRZĘDNE ŚRODKA QRA
        float QRmaxY = Math.max(Math.max(points[4], points[6]),(Math.max(points[0], points[2])));
        float QRmaxX = Math.max(Math.max(points[1], points[3]),(Math.max(points[5], points[7])));
        float QRminY = Math.min(Math.min(points[4], points[6]),(Math.min(points[0], points[2])));
        float QRminX = Math.min(Math.min(points[1], points[3]),(Math.min(points[5], points[7])));

        float QRcenterY = (QRmaxY+QRminY)/2f;
        float normQRcenterY = QRcenterY/1280;
        normQRcenterY = (normQRcenterY<=0.5f) ? (normQRcenterY*2)-1 : (normQRcenterY - 0.5f)*2;
        float QRcenterX = (QRmaxX+QRminX)/2f;
        float normQRcenterX = QRcenterX/720;
        normQRcenterX = (normQRcenterX<=0.5f) ? (normQRcenterX*2)-1 : (normQRcenterX - 0.5f)*2;

        float vec1x = 0;
        float vec1y = -QRcenterY;

        float vec2x = points[3]-QRcenterX;
        float vec2y = QRcenterY-points[2];

        //ZAŁĄCZENIE GLA
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        //KAMERKA
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, elevation, normQRcenterX*elevation/3.6f, normQRcenterY*elevation/1.9f, 0f, 0f, 1.0f, 0.0f);

        //PIERDY
        Matrix.setIdentityM(mRotationMatrix, 0);
        Matrix.translateM(mRotationMatrix, 0, 0, 0, 0);

        //OBRÓT KAMERKI
        float angleVX = MainActivity.Companion.getInclinationZ();
        Matrix.rotateM(mRotationMatrix, 0, 90-angleVX, 1.0f, 0,0);

        float angleVY = calcAngleY(vec1x, vec1y, vec2x, vec2y);
        if(QRcenterX<points[7])
        {
            angleVY=360-angleVY;
        }
        Matrix.rotateM(mRotationMatrix, 0, 360-angleVY, 0, 1.0f, 0);

        //PIERDY
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        //RYSOWANKO
        if(!val_str.equals("Witaj w Wikipedii!")){
            mCzajnik.draw(mMVPMatrix);
        }else{
        mLudzik.draw(mMVPMatrix);}
    }

    private float calcDist(float x1, float y1, float x2, float y2)
    {
        return (float)Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
    }

    private float calcAngleY(float x1, float y1, float x2, float y2)
    {
        float AoB = x1*x2 + y1*y2;
        float lenA = (float)Math.sqrt(x1*x1+y1*y1);
        float lenB = (float)Math.sqrt(x2*x2+y2*y2);
        float cosa = AoB/(lenA*lenB);
        float res = (float) Math.toDegrees(Math.acos(cosa));

        return res;
    }
}
