package com.example.qrdetect;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Ludzik {


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

    public Ludzik()
    {

        for (int i=0; i<vertPositions.length; i++){
            vertPositions[i]/=2f;
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
        float [] pinkClr = new float[]{1.0f,0.0f,1.0f,0.9f};

        GLES30.glUseProgram(progObj);

        int color = GLES30.glGetUniformLocation(progObj, "vColor");
        mvpMatrix = GLES30.glGetUniformLocation(progObj, "uMVPMatrix");

        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, _mvpMatrix, 0);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glUniform4fv(color, 1, pinkClr, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertPositions.length/3);
    }
}

