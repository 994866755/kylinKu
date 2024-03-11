package com.kylin.baselibrary.recycler

import android.os.Handler
import android.os.Looper
import java.util.Timer
import java.util.TimerTask

/**
 * Created by kylin on 2024/3/11.
 * recyclerview的item的倒计时管理类
 * 原理是开启一个心跳，有一个map存储每个item倒计时的时间，每秒触发map的数据改变，并通知viewHolder
 * demo：com.kylin.kylinproject.demo.recyclercountdown.RcdDemoActivity
 */

object RecyclerCountDownManager {

    private var task: TimerTask? = null
    private var timer: Timer? = null

    // viewHolder观察者
    private var viewHolderObservables: ArrayList<OnItemSchedule?> = ArrayList()

    // 倒计时对象数组
    private val cdMap: HashMap<Long, Long> = HashMap()

    /**
     *  添加viewHolder观察
     */
    fun addHolderObservable(onItemSchedule: OnItemSchedule?) {
        viewHolderObservables.add(onItemSchedule)
    }

    fun removeHolderObservable(onItemSchedule: OnItemSchedule?) {
        viewHolderObservables.remove(onItemSchedule)
    }

    fun releaseHolderObservable() {
        viewHolderObservables.clear()
    }

    /**
     *  添加倒计时对象
     *  @param totalCd 总倒计时时间
     *  @param isCover 是否覆盖
     */
    fun addCountDown(id: Long, totalCd: Long, isCover: Boolean = false) {
        if (cdMap.containsKey(id)) {
            if (isCover) {
                cdMap[id] = totalCd
            }
        } else {
            cdMap[id] = totalCd
        }
    }

    /**
     * 清除倒计时
     */
    fun clearCountDown() {
        cdMap.clear()
    }

    /**
     * 根据id获取倒计时
     */
    fun getCountDownById(id: Long): Long? {
        if (cdMap.containsKey(id)) {
            return cdMap[id]
        }

        return null
    }

    /**
     *  开始心跳
     */
    fun startHeartBeat() {
        if (task == null) {
            timer = Timer()
            task = object : TimerTask() {
                override fun run() {
                    updateCdByMap()
                }
            }
            timer?.schedule(task, 1000, 1000) // 每隔 1 秒钟执行一次
        }
    }

    /**
     *  更新所有倒计时对象
     */
    private fun updateCdByMap() {
        cdMap.forEach { (t, u) ->
            if (cdMap[t]!! > 0) {
                cdMap[t] = u - 1
            }
        }
        // 更改完数据之后通知观察者
        Handler(Looper.getMainLooper()).post {
            notifyCdFinish()
        }
    }

    private fun notifyCdFinish() {
        viewHolderObservables.forEach {
            it?.onCdSchedule()
        }
    }

    /**
     *  关闭心跳
     */
    fun closeHeartBeat() {
        task?.cancel()
        task = null
        timer = null
    }

    /**
     *  调度通知，一般由ViewHolder实现该接口
     */
    interface OnItemSchedule {

        fun onCdSchedule()

    }


}