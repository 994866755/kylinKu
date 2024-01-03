package com.kylin.baselibrary.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by kylin on 2024/1/3.
 * 仿卡片堆叠切换效果
 */
class CardPageTransformer : ViewPager2.PageTransformer {

    var mScaleOffset = 200f
    var mTranslationOffset = 100f

    override fun transformPage(page: View, position: Float) {
        if (position <= 0f) {
            page.translationX = 0f
        } else {
            val pageWidth: Int = page.width
            val transX = -pageWidth * position + mTranslationOffset * position
            page.translationX = transX
            val scale: Float = (pageWidth - mScaleOffset * position) / pageWidth.toFloat()
            page.scaleX = scale
            page.scaleY = scale
            page.translationZ = -position
        }
    }

}