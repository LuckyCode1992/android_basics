package com.justcode.hxl.androidbasices.thread

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.justcode.hxl.androidbasices.R
import com.justcode.hxl.androidbasices.utils.logThread
import kotlinx.android.synthetic.main.activity_thread_demo.*

class ThreadDemoActivity : AppCompatActivity() {
    var tvUIchange: TextView? = null
    var context: Context? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_demo)
        tvUIchange = tv_ui_change
        context = this
        tv_foreword.text = "在Android中，几乎完全采用了Java中的线程机制。线程是最小的调度单位，在很多情况下为了使APP更加流程地运行，" +
                "我们不可能将很多事情都放在主线程上执行，这样会造成严重卡顿（ANR），" +
                "那么这些事情应该交给子线程去做，但对于一个系统而言，创建、销毁、调度线程的过程是需要开销的，" +
                "所以我们并不能无限量地开启线程，那么对线程的了解就变得尤为重要了。"
        tv_foreword.movementMethod = ScrollingMovementMethod.getInstance()

        /** 一般实现线程的方法有两种，
         * 一种是类继承Thread，
         *一种是实现接口Runnable。
         *  这两种方式的优缺点如何呢？我们知道Java是单继承但可以调用多个接口，所以看起来Runnable更加好一些。
         */

        /**
         * 继承 thread
         */
        btn_extends_thread.setOnClickListener {
            extendsThread()
        }
        /**
         * 实现Runnable接口
         */
        btn_implements_runnable.setOnClickListener {
            implementsRunnable()
        }
        /**
         * AsyncTask
         */
        btn_AsyncTask.setOnClickListener {
            asyncTask()
        }

    }

    fun extendsThread() {
        val thread = MyThread()
        /**
         * run()方法只是调用了Thread实例的run()方法而已，
         * 它仍然运行在主线程上，而start()方法会开辟一个新的线程，
         * 在新的线程上调用run()方法，此时它运行在新的线程上。
         *  这里说一个问题，网上盛传 子线程更新UI 但是，根据实验发现， 事实并非如此，
         *  比如 改变text的值，不会出现问题，但是，显示和隐藏会出现。
         *
         */
//        thread.run()
        thread.start()
    }

    fun implementsRunnable() {

        val runnable = MyRunnable()
        val thread = Thread(runnable)
        //thread.run()
        thread.start()
    }

    fun asyncTask() {
        /**
         * AsyncTask是一个轻量级的异步任务类，
         * 它可以在线程池中执行后台任务，
         * 然后把执行的进度和结果传递给主线程并且在主线程中更新UI。
         * AsyncTask是一个抽象泛型类，声明：public abstract class AsyncTask<Params, Progress, Result>
         *   参数1，Params，异步任务的入参；
         *   参数2，Progress，执行任务的进度；
         *   参数3，Result，后台任务执行的结果；
         *   方法1， onPreExecute()，在主线程中执行，任务开启前的准备工作；
         *   方法2，doInbackground(Params…params)，开启子线程执行后台任务；
         *   方法3，onProgressUpdate(Progress values)，在主线程中执行，更新UI进度；
         *   方法4，onPostExecute(Result result)，在主线程中执行，异步任务执行完成后执行，它的参数是doInbackground()的返回值。
         */
//        executeOnExecutor 是并发
//        execute是串行
        MyAsyncTask("MyAsyncTask1",tvUIchange).execute("我是参数1")
        MyAsyncTask("MyAsyncTask2",tvUIchange).execute("我是参数2")
        MyAsyncTask("MyAsyncTask3",tvUIchange).execute("我是参数3")
    }


    inner class MyThread : Thread() {
        override fun run() {
            super.run()
            val id = Thread.currentThread().id
            val name = Thread.currentThread().name
            "MyThread id:$id".logThread()
            "MyThread name:$name".logThread()

            checkThreadUpdateUI()


        }


    }

    inner class MyRunnable : Runnable {
        override fun run() {
            val id = Thread.currentThread().id
            val name = Thread.currentThread().name
            "MyRunnable id:$id".logThread()
            "MyRunnable name:$name".logThread()
            checkThreadUpdateUI()
        }

    }

    fun checkThreadUpdateUI() {
        try {
            //start会抛出异常  java.lang.RuntimeException: Can't toast on a thread that has not called Looper.prepare()
            // run 一切正常
            Toast.makeText(context, "ss", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            // start ui可以正常更新  run ui 可以更新
            tvUIchange?.text = "fd"
            //但是  下面这种会报错
            tvUIchange?.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
