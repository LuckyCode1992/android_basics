package com.justcode.hxl.androidbasices.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.jump() {
    val intent = Intent(this, T::class.java)
    this.startActivity(intent)

}

inline fun <reified T : Activity> Activity.jump() {
    val intent = Intent(this, T::class.java)
    this.startActivity(intent)
}
