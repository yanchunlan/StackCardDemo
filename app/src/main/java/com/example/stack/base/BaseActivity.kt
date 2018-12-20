package com.example.stack.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.stack.R

/**
 * author:  ycl
 * date:  2018/12/20 9:59
 * desc:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }
    protected abstract fun init(savedInstanceState:Bundle?)
}
