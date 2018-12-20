package com.example.stack.stack

import android.content.Context
import android.util.TypedValue

/**
 * Created by yanchunlan on 2017/9/5.
 */
object StackConfig {

    //屏幕上最多显示几个item
    val MAX_SHOW_COUNT: Int = 4

    //每一级Scale相差0.05f，translation相差7dp左右
    val SCALR_GAP: Float = 0.05f

    var TRANS_Y_GAP: Int = 0

    fun initConfig(cxt: Context) {
        TRANS_Y_GAP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                15f, cxt.resources.displayMetrics).toInt()
    }

}