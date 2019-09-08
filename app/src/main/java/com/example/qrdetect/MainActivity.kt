package com.example.qrdetect

import android.app.PendingIntent.getActivity
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.RectF
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kroegerama.kaiteki.bcode.BarcodeResultListener
import com.kroegerama.kaiteki.bcode.BuildConfig
import com.kroegerama.kaiteki.bcode.ui.BarcodeFragment
import com.kroegerama.kaiteki.bcode.views.ResultPointView
import kotlin.math.max



class MainActivity : AppCompatActivity(), BarcodeResultListener{
    private var rect = RectF()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //kod z arSurfaceView
        val glView = findViewById<View>(R.id.gl_surface) as GLSurfaceView
        glView.setEGLContextClientVersion(3)
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        glView.setRenderer(arRenderer())
        glView.setZOrderOnTop(true)
        glView.getHolder().setFormat(PixelFormat.RGBA_8888)
        glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)

        val barcodeFragment = BarcodeFragment.makeInstance(
            formats = listOf(BarcodeFormat.QR_CODE),
            barcodeInverted = false
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, barcodeFragment)
            .commit()
        barcodeFragment

    }

    override fun onBarcodeResult(result: Result): Boolean {
        resultPoints = result.resultPoints.flatMap { listOf(it.x, it.y) }.toFloatArray()
        return false
    }

    companion object{
        var resultPoints = floatArrayOf(0f,0f,0f,0f,0f,0f,0f,0f)
    }

}