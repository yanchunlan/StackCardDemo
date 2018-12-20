package com.example.stack

import android.os.Bundle
import com.example.stack.base.BaseActivity
import com.example.stack.slid_up_down.SlidingDownPositiveActivity
import com.example.stack.slid_up_down.SlidingUpPositiveActivity
import com.example.stack.slid_up_down.SlidingUpReverseActivity
import com.example.stack.stack.StackActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {
    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // 卡片 任意方向
            startActivity<StackActivity>()
        }
        button1.setOnClickListener {
            // 下滑 倒序
            startActivity<SlidingUpReverseActivity>()
        }
        button2.setOnClickListener {
            // 下滑 正序
            startActivity<SlidingUpPositiveActivity>()
        }
        button3.setOnClickListener {
            // 上滑 倒序
            startActivity<SlidingDownPositiveActivity>()
        }
    }
}
