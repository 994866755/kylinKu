package com.kylin.kylinproject

import android.content.Context
import android.content.res.Resources
import android.graphics.Matrix
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapePath

class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    ConstraintLayout(context, attrs) {

    private var shapeView: ShapeableImageView? = null

    // 个点参数用于裁剪多边形
    val paX1 = dip2px(27.3f).toFloat()
    val paY1 = dip2px(9.2f).toFloat()
    val paX2 = dip2px(35.8f).toFloat()
    val paY2 = dip2px(9.2f).toFloat()

    val pbX1 = dip2px(51f).toFloat()
    val pbY1 = dip2px(20.3f).toFloat()
    val pbX2 = dip2px(53.9f).toFloat()
    val pbY2 = dip2px(29.1f).toFloat()

    val pcX1 = dip2px(48f).toFloat()
    val pcY1 = dip2px(47f).toFloat()
    val pcX2 = dip2px(41f).toFloat()
    val pcY2 = dip2px(52f).toFloat()

    val pdX1 = dip2px(22f).toFloat()
    val pdY1 = dip2px(52f).toFloat()
    val pdX2 = dip2px(15f).toFloat()
    val pdY2 = dip2px(47f).toFloat()

    val peX1 = dip2px(9.1f).toFloat()
    val peY1 = dip2px(29f).toFloat()
    val peX2 = dip2px(12f).toFloat()
    val peY2 = dip2px(20.2f).toFloat()

    val rdX1 = dip2px(31.5f).toFloat()
    val rdY1 = dip2px(7.5f).toFloat()
    val rdX2 = dip2px(54f).toFloat()
    val rdY2 = dip2px(24f).toFloat()
    val rdX3 = dip2px(45.5f).toFloat()
    val rdY3 = dip2px(51f).toFloat()
    val rdX4 = dip2px(17.5f).toFloat()
    val rdY4 = dip2px(51f).toFloat()
    val rdX5 = dip2px(9f).toFloat()
    val rdY5 = dip2px(24f).toFloat()

    init {
        inflate(context, R.layout.test_view, this)
        shapeView = findViewById(R.id.shape_view)

        val shapePathModel = ShapeAppearanceModel
            .Builder()
            .setTopEdge(object : EdgeTreatment() {

                override fun getEdgePath(
                    length: Float,
                    center: Float,
                    interpolation: Float,
                    shapePath: ShapePath
                ) {
                    shapePath.lineTo(paX1, 0f)
                    shapePath.lineTo(paX1, paY1)
                    shapePath.quadToPoint(
                        rdX1,
                        rdY1,
                        paX2,
                        paY2
                    )
                    shapePath.lineTo(pbX1, pbY1)
                    shapePath.quadToPoint(
                        rdX2,
                        rdY2,
                        pbX2,
                        pbY2
                    )
                    shapePath.lineTo(pcX1, pcY1)
                    shapePath.quadToPoint(
                        rdX3,
                        rdY3,
                        pcX2,
                        pcY2
                    )
                    shapePath.lineTo(pdX1, pdY1)
                    shapePath.quadToPoint(
                        rdX4,
                        rdY4,
                        pdX2,
                        pdY2
                    )
                    shapePath.lineTo(peX1, peY1)
                    shapePath.quadToPoint(
                        rdX5,
                        rdY5,
                        peX2,
                        peY2
                    )
                    shapePath.lineTo(paX1, paY1)
                    shapePath.lineTo(paX1, 0f)
                    shapePath.lineTo(0f, 0f)
                    shapePath.lineTo(0f, length)
                    shapePath.lineTo(length, length)
                    shapePath.lineTo(length, 0f)
                }

            })


//        val shapePathModel = ShapeAppearanceModel
//            .Builder()
//            .setTopEdge(object : EdgeTreatment() {
//
//                override fun getEdgePath(
//                    length: Float,
//                    center: Float,
//                    interpolation: Float,
//                    shapePath: ShapePath
//                ) {
//                    shapePath.lineTo(center, dip2px(10f).toFloat())
//                    shapePath.lineTo(length, 0f)
//                }
//            }).setLeftEdge(object : EdgeTreatment() {
//
//                override fun getEdgePath(
//                    length: Float,
//                    center: Float,
//                    interpolation: Float,
//                    shapePath: ShapePath
//                ) {
//                    shapePath.lineTo(center, dip2px(10f).toFloat())
//                    shapePath.lineTo(length, 0f)
//                }
//            }).setBottomEdge(object : EdgeTreatment() {
//
//                override fun getEdgePath(
//                    length: Float,
//                    center: Float,
//                    interpolation: Float,
//                    shapePath: ShapePath
//                ) {
//                    shapePath.lineTo(center, dip2px(10f).toFloat())
//                    shapePath.lineTo(length, 0f)
//                }
//            }).setRightEdge(object : EdgeTreatment() {
//
//                override fun getEdgePath(
//                    length: Float,
//                    center: Float,
//                    interpolation: Float,
//                    shapePath: ShapePath
//                ) {
//                    shapePath.lineTo(center, dip2px(10f).toFloat())
//                    shapePath.lineTo(length, 0f)
//                }
//            })

        shapeView?.shapeAppearanceModel = shapePathModel.build()
        shapeView?.scaleType = ImageView.ScaleType.MATRIX

        changeHeight(0.3f)
    }

    private fun changeHeight(currentValue: Float) {
        post {
            shapeView?.let { view ->
                view.imageMatrix = Matrix().also {
                    it.postTranslate(0f, view.height * (1 - currentValue))
                }
            }
        }
    }


    fun dip2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}