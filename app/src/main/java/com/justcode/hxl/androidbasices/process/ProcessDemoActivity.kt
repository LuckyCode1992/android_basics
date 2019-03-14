package com.justcode.hxl.androidbasices.process

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.justcode.hxl.androidbasices.IMyAidl
import com.justcode.hxl.androidbasices.R
import com.justcode.hxl.androidbasices.process.service.MyAidlService
import kotlinx.android.synthetic.main.activity_process_demo.*
import java.lang.Exception
import kotlin.random.Random

class ProcessDemoActivity : AppCompatActivity() {


    private var aidl: IMyAidl? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            aidl = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidl = IMyAidl.Stub.asInterface(service)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_demo)

        tv_RPC.text =
            "RPC :\n Remote Procedure Call (远程过程调用)\n 是一种计算机通讯协议，它为我们定义了计算机 C 中的程序如何调用另外一台计算机 S 的程序，让程序员不需要操心底层网络协议，使得开发包括网络分布式多程序在内的应用程序更加容易。" +
                    "RPC 是典型的 Client/Server 模式，由客户端对服务器发出若干请求，服务器收到后根据客户端提供的参数进行操作，然后将执行结果返回给客户端。" +
                    "RPC 位于 OSI 模型中的会话层,在面向对象编程中，它也被叫做 “远程方法调用”。"
        tv_IDL.text = "IDL :\n Interface Description Language (接口定义语言)。\n" +
                "它通过一种中立的方式来描述接口，使得在不同平台上运行的对象和用不同语言编写的程序可以相互通信交流。比如，一个组件用 C++ 写成，另一个组件用 Java 写，仍然可以通信。"
        tv_IPC.text = "IPC:\n" +
                "Inter-Process Communication (进程间通信)。\n" +
                "Android 基于 Linux，而 Linux 出于安全考虑，不同进程间不能之间操作对方的数据，这叫做“进程隔离”。" +
                "在 Linux 系统中，虚拟内存机制为每个进程分配了线性连续的内存空间，操作系统将这种虚拟内存空间映射到物理内存空间，每个进程有自己的虚拟内存空间，进而不能操作其他进程的内存空间，只有操作系统才有权限操作物理内存空间。" +
                "进程隔离保证了每个进程的内存安全。" +
                "但是在大多数情形下，不同进程间的数据通讯是不可避免的，因此操作系统必须提供跨进程通信机制。"
        tv_Android.text = "Android 几种进程通信方式:\n" +
                "跨进程通信要求把方法调用及其数据分解至操作系统可以识别的程度，并将其从本地进程和地址空间传输至远程进程和地址空间，然后在远程进程中重新组装并执行该调用。" +
                "然后，返回值将沿相反方向传输回来。\n" +
                "Android 为我们提供了以下几种进程通信机制\n" +
                "文件:外部sdcard 文件，所有进程都能访问\n" +
                "AIDL （基于 Binder）:见demo\n" +
                "Binder:见demo\n" +
                "Messenger （基于 Binder）:见demo\n" +
                "ContentProvider （基于 Binder）:见demo\n" +
                "Socket：见demo\n "

        tv_AIDL.text = "AIDL（Android 接口定义语言） 是 Android 提供的一种进程间通信 (IPC) 机制。\n" +
                "我们可以利用它定义客户端与服务使用进程间通信 (IPC) 进行相互通信时都认可的编程接口。\n" +
                "在 Android 上，一个进程通常无法访问另一个进程的内存。 尽管如此，进程需要将其对象分解成操作系统能够识别的原语，并将对象编组成跨越边界的对象。\n" +
                "编写执行这一编组操作的代码是一项繁琐的工作，因此 Android 会使用 AIDL 来处理。\n" +
                "通过这种机制，我们只需要写好 aidl 接口文件，编译时系统会帮我们生成 Binder 接口。\n" +
                "AIDL 支持的数据类型：\n" +
                "1.Java 的基本数据类型\n" +
                "2.List 和 Map \n" +
                "2.1 元素必须是 AIDL 支持的数据类型\n" +
                "2.2 Server 端具体的类里则必须是 ArrayList 或者 HashMap\n" +
                "3.其他 AIDL 生成的接口\n" +
                "4.实现 Parcelable 的实体"
        tv_AIDL_code.text = "AIDL 的编写主要为以下三部分：\n" +
                "一创建 AIDL :\n" +
                "1.创建要操作的实体类，实现 Parcelable 接口，以便序列化/反序列化\n" +
                "2.新建 aidl 文件夹，在其中创建接口 aidl 文件以及实体类的映射 aidl 文件\n" +
                "3.Make project ，生成 Binder 的 Java 文件\n" +
                "二服务端:\n" +
                "1.创建 Service，在其中创建上面生成的 Binder 对象实例，实现接口定义的方法\n" +
                "2.在 onBind() 中返回\n" +
                "三客户端：\n" +
                "1.实现 ServiceConnection 接口，在其中拿到 AIDL 类\n" +
                "2.bindService()\n" +
                "3.调用 AIDL 类中定义好的操作请求  "
        btn_bind_service.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MyAidlService::class.java)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
        var i = 0
        btn_test_aidl.setOnClickListener {
            i++
            val person = Person()
            person.name = "张" + i * 10
            person.age = i
            try {
                aidl?.addPerson(person)
                var personList: MutableList<Person>? = aidl?.getPersonList()?.toMutableList()
                tv_AIDL_result.text = ("aidl:" + personList.toString() + "\n")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
