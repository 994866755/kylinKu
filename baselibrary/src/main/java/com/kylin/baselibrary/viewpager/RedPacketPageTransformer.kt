package com.kylin.baselibrary.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by kylin on 2024/1/3.
 * 类红包切换效果
 */

class RedPacketPageTransformer : ViewPager2.PageTransformer {

    // 因为ViewPager的特性，内部控件宽度必须用填充
    // 我们将整个过程看成，原本这个控件是宽度填充的状态，中间的Item和两边的Item分别相较于原本状态进行缩放
    private val CENTER_SCAL_X = 0.68f  // 中间Item宽度缩放的宽度倍数
    private val CENTER_SCAL_Y = 0.8f   // 中间Item宽度缩放的高度倍数
    private val BOTH_SCAL_X = 0.5f   // 两边Item宽度缩放的宽度倍数
    private val BOTH_SCAL_Y = 0.6f   // 两边Item宽度缩放的高度倍数

    /**
     * （1）根据整个效果，我们可以进行拆分成几个步骤：中间Item向两边滑动时，会进行缩放和位移
     * （2）缩放（例如宽度）从 CENTER_SCAL_X 缩到 CENTER_BOTH_X ， 那就能反向推导出 scaleX = CENTER_SCAL_X + (diffX * position)
     *     怎么推导的，就是把假设从【-1，0】移动，我们把position带入-1和0 去算一个一元二次方程。position为0时，scaleX为0.68，position为1时，scaleX为0.5
     *     同理，【0，1】的区间就是scaleX = CENTER_SCAL_X - (diffX * position)
     * （3）位移，也是通过区间进行倒推，得到所有区间都是 translationX = -width * position + width * CENTER_SCAL_X * position
     * （4）【-2，-1】和【1，2】的两个区间，因为只按上边的写法会导致两边的Item进行移动的时候，比如中间的向右移，中间的会变小，两边的也会变得更小，所以使用
     *      translationX = -width * position + width * CENTER_SCAL_X * position 的时候间距会逐渐变大，不会是一个固定的间距，所以单独做适配，
     *      我们用个奇技淫巧，让两边的Item向两边移动的时候，宽度变大，而不是再次变得更小，只对宽度做放大，高度还是继续缩小，并不会影响我们的视觉效果。
     *      这时候可以开始推导公式，用【1，2】的区间去做推导，滑动前状态 position = 1时，scaleX = 0.5 ， 滑动最终状态， position = 2时， scaleX需要等于0.68，
     *      K = 0.68 + (0.18 * position) - (Z * (position + 1)) 初步这个公式根据上边的结果推导粗 Z 在  scaleX需要等于0.68时计算为 0.32，0.32就是2 * 0.18
     *      所以得出公式scaleX = CENTER_SCAL_X - (diffX * position) + (2 * diffX * (position - 1))
     *      同理【-2，-1】的公式是scaleX = CENTER_SCAL_X + (diffX * position) - (diffX * 2 * (position + 1))
     *
     */
    override fun transformPage(page: View, position: Float) {
        if (position < -2 || position > 2) {
            return
        }

        val diffX = CENTER_SCAL_X - BOTH_SCAL_X
        val diffY = CENTER_SCAL_Y - BOTH_SCAL_Y

        page.apply {
            // 如果只判断两个区间，发现【-2，-1】和【1，2】的移动的时候，
            // 两个Item之间的间距还是会变化，所以对这2个区间要单独判断，所以要判断4个区间

//            val pageWidth = width * 0.68
//            if (position <= 0f) {
//                scaleX = (0.68 + (0.32 * position)).toFloat()
//                scaleY = (0.8 + (0.2 * position)).toFloat()
//                translationX = (-width * position + 0.9 * pageWidth * position).toFloat()
//            }else {
//                scaleX = (0.68 - (0.32 * position)).toFloat()
//                scaleY = (0.8 - (0.2 * position)).toFloat()
//                translationX = (-width * position + 0.9 * pageWidth * position).toFloat()
//            }


            when {
                position < -1 -> { // [-Infinity,-1)
                    // 原始数据
//                    scaleX = (0.68 + (0.18 * position) - (0.36 *(position + 1)) ).toFloat()
//                    scaleY = (0.8 + (0.2 * position)).toFloat()
//                    translationX = (-width * position + pageWidth * position).toFloat()

                    scaleX = CENTER_SCAL_X + (diffX * position) - (diffX * 2 * (position + 1))
                    scaleY = (CENTER_SCAL_Y + (diffY * position)).toFloat()
                    translationX = -width * position + CENTER_SCAL_X * width * position
                }

                position <= 0 -> { // [-1,0]
                    // 原始数据
//                    scaleX = (0.68 + (0.18 * position)).toFloat()
//                    scaleY = (0.8 + (0.2 * position)).toFloat()
//                    translationX = (-width * position + pageWidth * position).toFloat()

                    scaleX = CENTER_SCAL_X + (diffX * position)
                    scaleY = CENTER_SCAL_Y + (diffY * position)
                    translationX = -width * position + width * CENTER_SCAL_X * position
                }

                position <= 1 -> { // (0,1]
                    // 原始数据
//                    scaleX = (0.68 - (0.18 * position)).toFloat()
//                    scaleY = (0.8 - (0.2 * position)).toFloat()
//                    translationX = (-width * position + pageWidth * position).toFloat()

                    scaleX = CENTER_SCAL_X - (diffX * position)
                    scaleY = CENTER_SCAL_Y - (diffY * position)
                    translationX = -width * position + width * CENTER_SCAL_X * position
                }

                else -> { // (1,+Infinity]
                    // 原始数据
//                    scaleX = (0.68 - (0.18 * position) + (0.36 * (position - 1))).toFloat()
//                    scaleY = (0.8 - (0.2 * position)).toFloat()
//                    translationX = (-width * position + pageWidth * position).toFloat()

                    scaleX = CENTER_SCAL_X - (diffX * position) + (2 * diffX * (position - 1))
                    scaleY = CENTER_SCAL_Y - (diffY * position)
                    translationX = -width * position + width * CENTER_SCAL_X * position
                }
            }
        }
    }

}