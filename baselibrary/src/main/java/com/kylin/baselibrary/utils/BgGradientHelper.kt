package com.kylin.baselibrary.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View

/**
 * 背景颜色变化帮助类
 */

class BgGradientHelper(colorStart: String, colorEnd: String, duration: Long) {

    val colors = intArrayOf(-1, -1)
    val offsets = floatArrayOf(0f, 0f)
    var aminState = 0

    var x = 0f
    private var mValueAnimator1: ValueAnimator? = null
    private var mView: View? = null

    init {
        colors[0] = Color.parseColor(colorStart)
        colors[1] = Color.parseColor(colorEnd)
    }

    fun start(view: View?) {
        if (aminState == 1) {
            return
        }

        mView = view
        aminState = 1
        setBg()
        startAmin()
    }

    fun setBg() {
        offsets[0] = x - 0.1f
        offsets[1] = x
        val drawable = GradientDrawable()
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColors(colors, offsets)
        }
        mView?.background = drawable
    }

    private fun startAmin() {
        if (mValueAnimator1 == null) {
            mValueAnimator1 = ValueAnimator.ofFloat(0f, 1.1f)
            mValueAnimator1?.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    mView = null
                    aminState = 0
                }

                override fun onAnimationCancel(animation: Animator) {
                    super.onAnimationCancel(animation)
                    mView = null
                    aminState = 0
                }

            })
            mValueAnimator1?.addUpdateListener {
                x = it.animatedValue as Float
                setBg()
            }
        }
        mValueAnimator1?.setDuration(2000)?.start()
    }

}