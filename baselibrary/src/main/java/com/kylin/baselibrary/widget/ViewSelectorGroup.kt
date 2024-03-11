package com.kylin.baselibrary.widget

import android.view.View

class ViewSelectorGroup<T> {

    val mItems: HashMap<T, Item> = HashMap()
    var currentItem: Item? = null
    var currentTag: T? = null
    var index = 0

    fun addView(
        tag: T, view: View, callback: OnSelectCallBack? = null
    ) {
        val item = Item(view, callback, index++)
        mItems[tag] = item
    }

    fun select(tag: Any) {
        mItems.forEach { (key, item) ->
            if (key == tag) {
                item.callback?.onSelect(true)
                currentItem = item
                currentTag = key
            } else {
                item.callback?.onSelect(false)
            }
        }
    }

    fun getPosition(): Int {
        return currentItem?.position ?: -1
    }

    fun getTag(): T? {
        return currentTag
    }

    /**
     *  选择器回调
     */
    interface OnSelectCallBack {

        fun onSelect(isSelect: Boolean)

    }

    /**
     *  结构体
     */
    data class Item(
        var view: View,
        var callback: OnSelectCallBack?,
        var position: Int,
    )

}