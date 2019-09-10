package com.example.qrdetect

import android.content.Context
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kroegerama.kaiteki.bcode.BarcodeResultListener
import com.kroegerama.kaiteki.bcode.ui.BarcodeFragment
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), BarcodeResultListener, SensorEventListener{

    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_NORMAL)

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
//        resultPoints = setResult(result,1280,720,0)
//        val layout : FrameLayout
//        layout = findViewById(R.id.fragment_container)
        resultPoints[0] = result.resultPoints[0].x //*layout.width/720
        resultPoints[1] = result.resultPoints[0].y //*layout.height/1280
        resultPoints[2] = result.resultPoints[1].x //*layout.width/720
        resultPoints[3] = result.resultPoints[1].y //*layout.height/1280
        resultPoints[4] = result.resultPoints[2].x //*layout.width/720
        resultPoints[5] = result.resultPoints[2].y //*layout.height/1280
        resultPoints[6] = result.resultPoints[3].x //*layout.width/720
        resultPoints[7] = result.resultPoints[3].y //*layout.height/1280

        return false
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(p0: SensorEvent?) {
        when (p0?.sensor?.type){
            Sensor.TYPE_ACCELEROMETER -> {
                var angleX = p0.values[0]
                var angleY = p0.values[1]
                var angleZ = p0.values[2]

                val normOfG = sqrt(angleX * angleX + angleY * angleY + angleZ * angleZ)

                angleZ /= normOfG

                inclinationZ = Math.toDegrees(acos(angleZ).toDouble()).roundToInt()
            }
        }
    }


    companion object{
        var resultPoints = FloatArray(8)
        var inclinationZ = 0
    }


    fun setResult(result: Result, imageWidth: Int, imageHeight: Int, imageRotation: Int):FloatArray {
        val localMatrix = createMatrix(imageWidth.toFloat(), imageHeight.toFloat(), imageRotation)

        val resultPoints1 = result.resultPoints.flatMap { listOf(it.x, it.y) }.toFloatArray()
        localMatrix.mapPoints(resultPoints1)

        return resultPoints1
    }

    private fun createMatrix(imageWidth: Float, imageHeight: Float, imageRotation: Int) = Matrix().apply {

        val layout : FrameLayout
        layout = findViewById(R.id.fragment_container)
        preTranslate((layout.width - imageWidth) / 2f, (layout.height - imageHeight) / 2f)
        preRotate(imageRotation.toFloat(), imageWidth / 2f, imageHeight / 2f)

        val wScale: Float
        val hScale: Float

        if (imageRotation % 180 == 0) {
            wScale = layout.width.toFloat() / imageWidth
            hScale = layout.height.toFloat() / imageHeight
        } else {
            wScale = layout.height.toFloat() / imageWidth
            hScale = layout.width.toFloat() / imageHeight
        }

        val scale = max(wScale, hScale)
        preScale(scale, scale, imageWidth / 2f, imageHeight / 2f)
    }

}