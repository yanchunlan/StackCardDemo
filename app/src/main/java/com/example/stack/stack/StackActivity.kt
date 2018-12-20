package com.example.stack.stack

import android.os.Bundle
import com.example.stack.base.BaseActivity
import com.example.stack.initStackRecycleView

/**
 * 卡片式层叠特效
 */
class StackActivity : BaseActivity() {
    override fun init(savedInstanceState: Bundle?) = initStackRecycleView()
}

