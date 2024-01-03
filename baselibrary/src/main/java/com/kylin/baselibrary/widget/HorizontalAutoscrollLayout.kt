package com.kylin.baselibrary.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.HorizontalScrollView

/**
 * Created by kylin on 2024/1/3.
 * 横向自动滚动控件
 */

class HorizontalAutoscrollLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    // 一些流程上的变量，可以自己去定义，变量多的情况也可以使用builder模式
    var isLoop = true        // 滚动到底后，是否循环滚动
    var loopDelay = 1000L    // 滚动的时间
    var duration = 1000L     // 每一次滚动的间隔时间

    private var offset: Int = 0
    val loopHandler = Handler(Looper.getMainLooper())
    var isAutoStart = false

    private var animator: ValueAnimator? = null

    fun autoStart() {
        // 需要计算滚动距离所以要把计算得代码写在post里面，等绘制完才拿得到宽度
        post {
            val childView = getChildAt(0)
            childView?.let {
                offset = it.measuredWidth - width
            }

            animator = ValueAnimator.ofInt(0, offset)
                .setDuration(duration)
            // 属性动画去缓慢改变scrollview的滚动位置，抽象上也可以说改变scrollview的属性
            animator?.addUpdateListener {
                val currentValue = it.animatedValue as Int
                scrollTo(currentValue, 0)
            }
            animator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    // 动画结束后判断是否要重复播放
                    if (isLoop) {
                        loopHandler.postDelayed({
                            if (isAutoStart) {
                                scrollTo(0, 0)
                                autoStart()
                            }
                        }, loopDelay)
                    }
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }

            })
            animator?.start()
            isAutoStart = true

        }
    }

    // 动画取消
    fun autoStop() {
        animator?.cancel()
        isAutoStart = false
        loopHandler.removeCallbacksAndMessages(null)
    }

}