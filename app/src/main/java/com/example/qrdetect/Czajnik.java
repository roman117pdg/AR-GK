package com.example.qrdetect;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Czajnik {

    float [] vertPositions = new float[]{
            //GŁOWA
            //przód
            -2,-8,2.5f, -2,-10,2.5f, -4,-10,2.5f,
            -2,-8,2.5f, -4,-10,2.5f, -4,-8,2.5f,
            //tył
            -2,-8,0.5f, -2,-10,0.5f, -4,-10,0.5f,
            -2,-8,0.5f, -4,-10,0.5f, -4,-8,0.5f,
            //lewo
            -2,-8,0.5f, -2,-10,0.5f, -2,-10,2.5f,
            -2,-8,0.5f, -2,-10,2.5f, -2,-8,2.5f,
            //prawo
            -4,-8,0.5f, -4,-10,0.5f, -4,-10,2.5f,
            -4,-8,0.5f, -4,-10,2.5f, -4,-8,2.5f,
            //góra
            -2,-10,2.5f, -2,-10,0.5f, -4,-10,0.5f,
            -2,-10,2.5f, -4,-10,0.5f, -4,-10,2.5f,
            //BRZUCH
            //przód
            -1,-4,3, -1,-8,3, -5,-8,3,
            -1,-4,3, -5,-8,3, -5,-4,3,
            //plecy
            -1,-4,0, -1,-8,0, -5,-8,0,
            -1,-4,0, -5,-8,0, -5,-4,0,
            //lewo
            -1,-4,0, -1,-8,0, -1,-8,3,
            -1,-4,0, -1,-8,3, -1,-4,3,
            //prawo
            -5,-4,0, -5,-8,0, -5,-8,3,
            -5,-4,0, -5,-8,3, -5,-4,3,
            //góra
            -1,-8,3, -1,-8,0, -5,-8,0,
            -1,-8,3, -5,-8,0, -5,-8,3,
            //dół
            -1,-4,3, -1,-4,0, -5,-4,0,
            -1,-4,3, -5,-4,0, -5,-4,3,
            //REKA LEWA
            //przód
            0,-4,2, 0,-8,2, -1,-8,2,
            0,-4,2, -1,-8,2, -1,-4,2,
            //tył
            0,-4,1, 0,-8,1, -1,-8,1,
            0,-4,1, -1,-8,1, -1,-4,1,
            //bok
            0,-4,1, 0,-8,1, 0,-8,2,
            0,-4,1, 0,-8,2, 0,-4,2,
            //góra
            0,-8,2, 0,-8,1, -1,-8,1,
            0,-8,2, -1,-8,1, -1,-8,2,
            //dół
            0,-4,2, 0,-4,1, -1,-4,1,
            0,-4,2, -1,-4,1, -1,-4,2,
            //REKA PRAWA
            //przód
            -5,-4,2, -5,-8,2, -6,-8,2,
            -5,-4,2, -6,-8,2, -6,-4,2,
            //tył
            -5,-4,1, -5,-8,1, -6,-8,1,
            -5,-4,1, -6,-8,1, -6,-4,1,
            //bok
            -6,-4,1, -6,-8,1, -6,-8,2,
            -6,-4,1, -6,-8,2, -6,-4,2,
            //góra
            -5,-8,2, -5,-8,1, -6,-8,1,
            -5,-8,2, -6,-8,1, -6,-8,2,
            //dół
            -5,-4,2, -5,-4,1, -6,-4,1,
            -5,-4,2, -6,-4,1, -6,-4,2,
            //NOGA LEWA
            //przód
            -1,0,2.25f, -1,-4,2.25f, -2.5f,-4,2.25f,
            -1,0,2.25f, -2.5f,-4,2.25f, -2.5f,0,2.25f,
            //tył
            -1,0,0.75f, -1,-4,0.75f, -2.5f,-4,0.75f,
            -1,0,0.75f, -2.5f,-4,0.75f, -2.5f,0,0.75f,
            //lewo
            -1,0,0.75f, -1,-4,0.75f, -1,-4,2.25f,
            -1,0,0.75f, -1,-4,2.25f, -1,0,2.25f,
            //prawo
            -2.5f,0,0.75f, -2.5f,-4,0.75f, -2.5f,-4,2.25f,
            -2.5f,0,0.75f, -2.5f,-4,2.25f, -2.5f,0,2.25f,
            //dół
            -1,0,2.25f, -1,0,0.75f, -2.5f,0,0.75f,
            -1,0,2.25f, -2.5f,0,0.75f, -2.5f,0,2.25f,
            //NOGA PRAWA
            //przód
            -3.5f,0,2.25f, -3.5f,-4,2.25f, -5,-4,2.25f,
            -3.5f,0,2.25f, -5,-4,2.25f, -5,0,2.25f,
            //tył
            -3.5f,0,0.75f, -3.5f,-4,0.75f, -5,-4,0.75f,
            -3.5f,0,0.75f, -5,-4,0.75f, -5,0,0.75f,
            //lewo
            -3.5f,0,0.75f, -3.5f,-4,0.75f, -3.5f,-4,2.25f,
            -3.5f,0,0.75f, -3.5f,-4,2.25f, -3.5f,0,2.25f,
            //prawo
            -5,0,0.75f, -5,-4,0.75f, -5,-4,2.25f,
            -5,0,0.75f, -5,-4,2.25f, -5,0,2.25f,
            //dół
            -3.5f,0,2.25f, -3.5f,0,0.75f, -5,0,0.75f,
            -3.5f,0,2.25f, -5,0,0.75f, -5,0,2.25f

    };


    String vertexShaderCode =
            "#version 300 es 			  \n"
                    + "uniform mat4 uMVPMatrix;     \n"
                    + "in vec4 vPosition;           \n"
                    + "void main()                  \n"
                    + "{                            \n"
                    + "   gl_Position = uMVPMatrix * vPosition;  \n"
                    + "}                            \n";

    String fragmentShaderCode =
            "#version 300 es		 			          	\n"
                    + "precision mediump float;					  	\n"
                    + "uniform vec4 vColor;	 			 		  	\n"
                    + "out vec4 fragColor;	 			 		  	\n"
                    + "void main()                                  \n"
                    + "{                                            \n"
                    + "  fragColor = vColor;                    	\n"
                    + "}                                            \n";

    private int progObj;
    private int mvpMatrix;
    private int uvMatrix;
    private FloatBuffer vertBuf;

    public Czajnik()
    {

        for (int i=0; i<vertPositions.length; i++){
            vertPositions[i]/=10f;
            if (i%3==1)
            {
                vertPositions[i]*=-1;
            }
        }
        //wierzchołki
        vertBuf = ByteBuffer.allocateDirect(vertPositions.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertPositions);
        vertBuf.position(0);

        //shadery
        int vertShader = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
        GLES30.glShaderSource(vertShader, vertexShaderCode);
        GLES30.glCompileShader(vertShader);

        int fragShader = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(fragShader, fragmentShaderCode);
        GLES30.glCompileShader(fragShader);

        //stworzenie programu
        progObj = GLES30.glCreateProgram();

        //dołączneie shaderów do niego
        GLES30.glAttachShader(progObj, vertShader);
        GLES30.glAttachShader(progObj, fragShader);

        //linknięcie programu
        GLES30.glLinkProgram(progObj);
    }

    public void draw(float[] _mvpMatrix)
    {
        float [] whiteClr = new float[]{0.5f,0.5f,0.5f,0.9f};

        GLES30.glUseProgram(progObj);

        int color = GLES30.glGetUniformLocation(progObj, "vColor");
        mvpMatrix = GLES30.glGetUniformLocation(progObj, "uMVPMatrix");

        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, _mvpMatrix, 0);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glUniform4fv(color, 1, whiteClr, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertPositions.length/3);
    }
}


/*

WSPÓŁRZĘDNE OF CZAJNIK
      0.2000f,  0.0000f, 2.70000f,  0.2000f, -0.1120f, 2.70000f ,
      0.1120f, -0.2000f, 2.70000f,  0.0000f, -0.2000f, 2.70000f ,
      1.3375f,  0.0000f, 2.53125f,  1.3375f, -0.7490f, 2.53125f ,
      0.7490f, -1.3375f, 2.53125f,  0.0000f, -1.3375f, 2.53125f ,
      1.4375f,  0.0000f, 2.53125f,  1.4375f, -0.8050f, 2.53125f ,
      0.8050f, -1.4375f, 2.53125f,  0.0000f, -1.4375f, 2.53125f ,
      1.5000f,  0.0000f, 2.40000f,  1.5000f, -0.8400f, 2.40000f ,
      0.8400f, -1.5000f, 2.40000f,  0.0000f, -1.5000f, 2.40000f ,
      1.7500f,  0.0000f, 1.87500f,  1.7500f, -0.9800f, 1.87500f ,
      0.9800f, -1.7500f, 1.87500f,  0.0000f, -1.7500f, 1.87500f ,
      2.0000f,  0.0000f, 1.35000f,  2.0000f, -1.1200f, 1.35000f ,
      1.1200f, -2.0000f, 1.35000f,  0.0000f, -2.0000f, 1.35000f ,
      2.0000f,  0.0000f, 0.90000f,  2.0000f, -1.1200f, 0.90000f ,
      1.1200f, -2.0000f, 0.90000f,  0.0000f, -2.0000f, 0.90000f ,
     -2.0000f,  0.0000f, 0.90000f,  2.0000f,  0.0000f, 0.45000f ,
      2.0000f, -1.1200f, 0.45000f,  1.1200f, -2.0000f, 0.45000f ,
      0.0000f, -2.0000f, 0.45000f,  1.5000f,  0.0000f, 0.22500f ,
      1.5000f, -0.8400f, 0.22500f,  0.8400f, -1.5000f, 0.22500f ,
      0.0000f, -1.5000f, 0.22500f,  1.5000f,  0.0000f, 0.15000f ,
      1.5000f, -0.8400f, 0.15000f,  0.8400f, -1.5000f, 0.15000f ,
      0.0000f, -1.5000f, 0.15000f, -1.6000f,  0.0000f, 2.02500f ,
     -1.6000f, -0.3000f, 2.02500f, -1.5000f, -0.3000f, 2.25000f ,
     -1.5000f,  0.0000f, 2.25000f, -2.3000f,  0.0000f, 2.02500f ,
     -2.3000f, -0.3000f, 2.02500f, -2.5000f, -0.3000f, 2.25000f ,
     -2.5000f,  0.0000f, 2.25000f, -2.7000f,  0.0000f, 2.02500f ,
     -2.7000f, -0.3000f, 2.02500f, -3.0000f, -0.3000f, 2.25000f ,
     -3.0000f,  0.0000f, 2.25000f, -2.7000f,  0.0000f, 1.80000f ,
     -2.7000f, -0.3000f, 1.80000f, -3.0000f, -0.3000f, 1.80000f ,
     -3.0000f,  0.0000f, 1.80000f, -2.7000f,  0.0000f, 1.57500f ,
     -2.7000f, -0.3000f, 1.57500f, -3.0000f, -0.3000f, 1.35000f ,
     -3.0000f,  0.0000f, 1.35000f, -2.5000f,  0.0000f, 1.12500f ,
     -2.5000f, -0.3000f, 1.12500f, -2.6500f, -0.3000f, 0.93750f ,
     -2.6500f,  0.0000f, 0.93750f, -2.0000f, -0.3000f, 0.90000f ,
     -1.9000f, -0.3000f, 0.60000f, -1.9000f,  0.0000f, 0.60000f ,
      1.7000f,  0.0000f, 1.42500f,  1.7000f, -0.6600f, 1.42500f ,
      1.7000f, -0.6600f, 0.60000f,  1.7000f,  0.0000f, 0.60000f ,
      2.6000f,  0.0000f, 1.42500f,  2.6000f, -0.6600f, 1.42500f ,
      3.1000f, -0.6600f, 0.82500f,  3.1000f,  0.0000f, 0.82500f ,
      2.3000f,  0.0000f, 2.10000f,  2.3000f, -0.2500f, 2.10000f ,
      2.4000f, -0.2500f, 2.02500f,  2.4000f,  0.0000f, 2.02500f ,
      2.7000f,  0.0000f, 2.40000f,  2.7000f, -0.2500f, 2.40000f ,
      3.3000f, -0.2500f, 2.40000f,  3.3000f,  0.0000f, 2.40000f ,
      2.8000f,  0.0000f, 2.47500f,  2.8000f, -0.2500f, 2.47500f ,
      3.5250f, -0.2500f, 2.49375f,  3.5250f,  0.0000f, 2.49375f ,
      2.9000f,  0.0000f, 2.47500f,  2.9000f, -0.1500f, 2.47500f ,
      3.4500f, -0.1500f, 2.51250f,  3.4500f,  0.0000f, 2.51250f ,
      2.8000f,  0.0000f, 2.40000f,  2.8000f, -0.1500f, 2.40000f ,
      3.2000f, -0.1500f, 2.40000f,  3.2000f,  0.0000f, 2.40000f ,
      0.0000f,  0.0000f, 3.15000f,  0.8000f,  0.0000f, 3.15000f ,
      0.8000f, -0.4500f, 3.15000f,  0.4500f, -0.8000f, 3.15000f ,
      0.0000f, -0.8000f, 3.15000f,  0.0000f,  0.0000f, 2.85000f ,
      1.4000f,  0.0000f, 2.40000f,  1.4000f, -0.7840f, 2.40000f ,
      0.7840f, -1.4000f, 2.40000f,  0.0000f, -1.4000f, 2.40000f ,
      0.4000f,  0.0000f, 2.55000f,  0.4000f, -0.2240f, 2.55000f ,
      0.2240f, -0.4000f, 2.55000f,  0.0000f, -0.4000f, 2.55000f ,
      1.3000f,  0.0000f, 2.55000f,  1.3000f, -0.7280f, 2.55000f ,
      0.7280f, -1.3000f, 2.55000f,  0.0000f, -1.3000f, 2.55000f ,
      1.3000f,  0.0000f, 2.40000f,  1.3000f, -0.7280f, 2.40000f ,
      0.7280f, -1.3000f, 2.40000f,  0.0000f, -1.3000f, 2.40000f


WSPÓŁRZĘDNE OF LUDZIK
 //GŁOWA
            //przód
            -2,-8,2.5f, -2,-10,2.5f, -4,-10,2.5f,
            -2,-8,2.5f, -4,-10,2.5f, -4,-8,2.5f,
            //tył
            -2,-8,0.5f, -2,-10,0.5f, -4,-10,0.5f,
            -2,-8,0.5f, -4,-10,0.5f, -4,-8,0.5f,
            //lewo
            -2,-8,0.5f, -2,-10,0.5f, -2,-10,2.5f,
            -2,-8,0.5f, -2,-10,2.5f, -2,-8,2.5f,
            //prawo
            -4,-8,0.5f, -4,-10,0.5f, -4,-10,2.5f,
            -4,-8,0.5f, -4,-10,2.5f, -4,-8,2.5f,
            //góra
            -2,-10,2.5f, -2,-10,0.5f, -4,-10,0.5f,
            -2,-10,2.5f, -4,-10,0.5f, -4,-10,2.5f,
            //BRZUCH
            //przód
            -1,-4,3, -1,-8,3, -5,-8,3,
            -1,-4,3, -5,-8,3, -5,-4,3,
            //plecy
            -1,-4,0, -1,-8,0, -5,-8,0,
            -1,-4,0, -5,-8,0, -5,-4,0,
            //lewo
            -1,-4,0, -1,-8,0, -1,-8,3,
            -1,-4,0, -1,-8,3, -1,-4,3,
            //prawo
            -5,-4,0, -5,-8,0, -5,-8,3,
            -5,-4,0, -5,-8,3, -5,-4,3,
            //góra
            -1,-8,3, -1,-8,0, -5,-8,0,
            -1,-8,3, -5,-8,0, -5,-8,3,
            //dół
            -1,-4,3, -1,-4,0, -5,-4,0,
            -1,-4,3, -5,-4,0, -5,-4,3,
            //REKA LEWA
            //przód
            0,-4,2, 0,-8,2, -1,-8,2,
            0,-4,2, -1,-8,2, -1,-4,2,
            //tył
            0,-4,1, 0,-8,1, -1,-8,1,
            0,-4,1, -1,-8,1, -1,-4,1,
            //bok
            0,-4,1, 0,-8,1, 0,-8,2,
            0,-4,1, 0,-8,2, 0,-4,2,
            //góra
            0,-8,2, 0,-8,1, -1,-8,1,
            0,-8,2, -1,-8,1, -1,-8,2,
            //dół
            0,-4,2, 0,-4,1, -1,-4,1,
            0,-4,2, -1,-4,1, -1,-4,2,
            //REKA PRAWA
            //przód
            -5,-4,2, -5,-8,2, -6,-8,2,
            -5,-4,2, -6,-8,2, -6,-4,2,
            //tył
            -5,-4,1, -5,-8,1, -6,-8,1,
            -5,-4,1, -6,-8,1, -6,-4,1,
            //bok
            -6,-4,1, -6,-8,1, -6,-8,2,
            -6,-4,1, -6,-8,2, -6,-4,2,
            //góra
            -5,-8,2, -5,-8,1, -6,-8,1,
            -5,-8,2, -6,-8,1, -6,-8,2,
            //dół
            -5,-4,2, -5,-4,1, -6,-4,1,
            -5,-4,2, -6,-4,1, -6,-4,2,
            //NOGA LEWA
            //przód
            -1,0,2.25f, -1,-4,2.25f, -2.5f,-4,2.25f,
            -1,0,2.25f, -2.5f,-4,2.25f, -2.5f,0,2.25f,
            //tył
            -1,0,0.75f, -1,-4,0.75f, -2.5f,-4,0.75f,
            -1,0,0.75f, -2.5f,-4,0.75f, -2.5f,0,0.75f,
            //lewo
            -1,0,0.75f, -1,-4,0.75f, -1,-4,2.25f,
            -1,0,0.75f, -1,-4,2.25f, -1,0,2.25f,
            //prawo
            -2.5f,0,0.75f, -2.5f,-4,0.75f, -2.5f,-4,2.25f,
            -2.5f,0,0.75f, -2.5f,-4,2.25f, -2.5f,0,2.25f,
            //dół
            -1,0,2.25f, -1,0,0.75f, -2.5f,0,0.75f,
            -1,0,2.25f, -2.5f,0,0.75f, -2.5f,0,2.25f,
            //NOGA PRAWA
            //przód
            -3.5f,0,2.25f, -3.5f,-4,2.25f, -5,-4,2.25f,
            -3.5f,0,2.25f, -5,-4,2.25f, -5,0,2.25f,
            //tył
            -3.5f,0,0.75f, -3.5f,-4,0.75f, -5,-4,0.75f,
            -3.5f,0,0.75f, -5,-4,0.75f, -5,0,0.75f,
            //lewo
            -3.5f,0,0.75f, -3.5f,-4,0.75f, -3.5f,-4,2.25f,
            -3.5f,0,0.75f, -3.5f,-4,2.25f, -3.5f,0,2.25f,
            //prawo
            -5,0,0.75f, -5,-4,0.75f, -5,-4,2.25f,
            -5,0,0.75f, -5,-4,2.25f, -5,0,2.25f,
            //dół
            -3.5f,0,2.25f, -3.5f,0,0.75f, -5,0,0.75f,
            -3.5f,0,2.25f, -5,0,0.75f, -5,0,2.25f
 */