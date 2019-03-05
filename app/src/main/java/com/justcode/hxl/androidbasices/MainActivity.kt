package com.justcode.hxl.androidbasices

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidbasices.thread.ThreadDemoActivity
import com.justcode.hxl.androidbasices.utils.jump
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * 本项目 目的是写一些基础，但重要的demo 在demo中 阐述一些要点，难点
         * 具体有哪些，会在项目中，有具体表现，我会用一个个的模块去阐述。
         */

        /**
         * 线程相关
         */
        btn_thread.setOnClickListener {
            jump<ThreadDemoActivity>()
        }
    }
}
