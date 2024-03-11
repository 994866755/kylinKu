package com.kylin.kylinproject.demo.recyclercountdown

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kylin.baselibrary.recycler.RecyclerCountDownManager

class RcdAdapter(var context: Context, var list: List<RcdItemData>) :
    RecyclerView.Adapter<RcdAdapter.RcdViewHolder>() {

    init {
        // 因为模式默认选择不覆盖，需要每次添加前先清除
        RecyclerCountDownManager.clearCountDown()
        list.forEach {
            RecyclerCountDownManager.addCountDown(it.id, it.cd)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcdViewHolder {
        val text: TextView = TextView(context)
        text.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64)
        text.gravity = Gravity.CENTER
        val holder = RcdViewHolder(text)
        RecyclerCountDownManager.addHolderObservable(holder)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RcdViewHolder, position: Int) {
        holder.setData(list[position])
    }

    class RcdViewHolder(var view: TextView) : RecyclerView.ViewHolder(view),
        RecyclerCountDownManager.OnItemSchedule {

        private var mData: RcdItemData? = null

        fun setData(data: RcdItemData) {
            mData = data
        }

        override fun onCdSchedule() {
            val cd = mData?.id?.let { RecyclerCountDownManager.getCountDownById(it) }
            if (cd != null) {
                // 测试展示分秒
                view.text = "${String.format("%02d", cd / 60)}:${String.format("%02d", cd % 60)}"
            }
        }

    }

}