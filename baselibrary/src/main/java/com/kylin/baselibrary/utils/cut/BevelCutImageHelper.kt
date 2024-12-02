package com.kylin.baselibrary.utils.cut

import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapePath


/**
 * Created by kylin on 2024/11/28.
 * 斜切ImageView
 */

class BevelCutImageHelper {

    fun cut(type: Int, view : ShapeableImageView){
        val shapePathModel = ShapeAppearanceModel
            .Builder()
            .setTopEdge(object : EdgeTreatment() {

                override fun getEdgePath(
                    length: Float,
                    center: Float,
                    interpolation: Float,
                    shapePath: ShapePath
                ) {
                    if (type == 0) {
                        shapePath.lineTo((length/4), 0f)
                        shapePath.lineTo(0f, length)
                        shapePath.lineTo(0f, 0f)
                        shapePath.lineTo(length, 0f)
                    }else {
                        shapePath.lineTo(length, 0f)
                        shapePath.lineTo((length/4 * 3), length)
                        shapePath.lineTo(length, length)
                        shapePath.lineTo(length, 0f)
                    }
                }

            })

        view?.let {
            it.shapeAppearanceModel = shapePathModel.build()
        }
    }

}