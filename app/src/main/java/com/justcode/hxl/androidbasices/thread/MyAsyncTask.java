package com.justcode.hxl.androidbasices.thread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AsyncTask是一个轻量级的异步任务类，
 * 它可以在线程池中执行后台任务，
 * 然后把执行的进度和结果传递给主线程并且在主线程中更新UI。
 * AsyncTask是一个抽象泛型类，声明：public abstract class AsyncTask<Params, Progress, Result>
 * 参数1，Params，异步任务的入参；
 * 参数2，Progress，执行任务的进度；
 * 参数3，Result，后台任务执行的结果；
 * 方法1， onPreExecute()，在主线程中执行，任务开启前的准备工作；
 * 方法2，doInbackground(Params…params)，开启子线程执行后台任务；
 * 方法3，onProgressUpdate(Progress values)，在主线程中执行，更新UI进度；
 * 方法4，onPostExecute(Result result)，在主线程中执行，异步任务执行完成后执行，它的参数是doInbackground()的返回值。
 */
public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    private String name;
    private TextView textView;

    public MyAsyncTask(String name, TextView textView) {
        this.name = name;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("logThread", "onPreExecute:"+name);
        textView.setText("onPreExecute");

    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("logThread", "doInBackground:" + strings[0]);
        try {
            Thread.sleep(3000);
            publishProgress(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        return strings[0] + ":" + date;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("logThread", "onPostExecute:" + s);
        textView.setText("onPostExecute");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d("logThread", "onProgressUpdate:" + values[0]);
        textView.setText("onProgressUpdate");

    }
}
