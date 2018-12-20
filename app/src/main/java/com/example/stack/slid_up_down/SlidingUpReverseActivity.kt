package com.example.stack.slid_up_down

import android.os.Bundle
import com.example.stack.base.BaseActivity
import com.example.stack.initSlidingUpReverseRecycleView

/**
 *  下滑 倒序
 */
class SlidingUpReverseActivity : BaseActivity() {
    override fun init(savedInstanceState: Bundle?) = initSlidingUpReverseRecycleView()
}
