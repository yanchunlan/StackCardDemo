package com.example.stack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 卡片式层叠特效
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStackRecycleView()
    }
}
