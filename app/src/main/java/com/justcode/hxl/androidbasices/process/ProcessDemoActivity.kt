package com.justcode.hxl.androidbasices.process

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import com.justcode.hxl.androidbasices.IMyAidl
import com.justcode.hxl.androidbasices.R
import com.justcode.hxl.androidbasices.process.service.ConfigHelper
import com.justcode.hxl.androidbasices.process.service.MyAidlService
import kotlinx.android.synthetic.main.activity_process_demo.*
import java.lang.Exception
import kotlin.random.Random
import android.util.Log
import com.justcode.hxl.androidbasices.process.service.MessengerService


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

    val clientMesenger = Messenger(
        @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_SERVER) {
                    if (msg.getData() == null) {
                        return
                    }
                    val content = msg.data.get(ConfigHelper.MSG_CONTENT) as String
                    Log.d("MessengerService", "Message from server: $content")
                    tv_Messenger_result.text = content
                }
            }
        })

    var serviceMesenger: Messenger? = null
    val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            serviceMesenger = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceMesenger = Messenger(service)
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

        tv_Binder.text = "Binder 继承自 IBinder\n" +
                "IBinder 是一个接口，它代表了一种跨进程传输的能力。只要实现了这个接口，就能将这个对象进行跨进程传递。\n" +
                "IBinder 是高性能、轻量级远程调用机制的核心部分，它定义了远程操作对象的基本接口。\n" +
                "这些方法中最关键的一个是 transact(),与它对应的是 Binder.onTransact()\n" +
                "①经常的场景是，我们调用 IBinder.transact() 给一个 IBinder 对象发送请求，然后经过 Binder Binder.onTransact() 得到调用，接着远程操作的目标得到对应的调用。\n" +
                "②通过 IBinder.transact() 方法传输的数据被保存为一个 Parcel 对象，Parcel 中保存了数据以及描述数据的元数据，元数据在缓存区中保持了 IBinder 对象的引用，这样不同进程都可以访问同一个数据。\n" +
                "重点：在跨进程传输后引用没有改变，这是非常关键的一点，这就使得 IBinder/Binder 对象在跨进程通信时可以作为唯一的标识（比如作为 token 什么的）\n" +
                "③系统在每个进程中都有一个处理事物的线程池，这些线程用于调度其他进程对当前进程的跨进程访问。\n" +
                "④Binder 机制还支持进程间的递归调用\n" +
                "⑤在跨进程通信时，我们常常想要知道另外进程是否可用，IBinder 提供了三个检查的方法：\n" +
                "1.transact() ," +
                "当你调用的 IBinder 所在进程不存在时，会抛出 RemoteException 异常\n" +
                "2.pingBinder() " +
                "当远程进程不存在时该方法返回 false\n" +
                "3.linkToDeath() " +
                "这个方法可以向 IBinder 中注册一个 IBinder.DeathRecipient，它将在 IBinder 所在的进程退出时被调用\n\n" +
                "日常开发中一般不需要我们再实现 IBinder，直接使用系统提供的 Binder 即可。\n" +
                "Binder 实现了 IBinder 定义的操作，它是 Android IPC 的基础，平常接触到的各种 Manager（ActivityManager, ServiceManager 等），以及绑定 Service 时都在使用它进行跨进程操作。\n" +
                "它的存在不会影响一个应用的生命周期，只要创建它的进程在运行它就一直可用。\n" +
                "通常我们需要在顶级的组件（Service, Activity, ContentProvider）中使用它，这样系统才知道你的进程应该一直被保留。\n" +
                "在 Android 系统的 Binder 机制中，由四个组件组成，分别是：\n" +
                "Client\n" +
                "Server\n" +
                "ServiceManager：提供辅助管理 Server 的功能\n" +
                "Binder 驱动程序：整个机制的核心\n" +
                "Binder 驱动程序:驱动程序一般指的是设备驱动程序（Device Driver），" +
                "是一种可以使计算机和设备通信的特殊程序。相当于硬件的接口，操作系统只有通过这个接口，才能控制硬件设备的工作。\n" +
                " Linux 系统中，内存空间分为两部分：\n" +
                "用户空间：运行着应用程序\n" +
                "内核空间：运行着系统内核和驱动\n" +
                "在 Binder 机制中，由 Binder 驱动负责完成这个中转操作，主要过程如下：\n" +
                "1.当 Client 向 Server 发起 IPC 请求时，Client 会先将请求数据从用户空间拷贝到内核空间\n" +
                "2.数据被拷贝到内核空间之后，驱动程序将内核空间中的数据拷贝到 Server 位于用户空间的缓存中\n" +
                "\n\n" +
                "Service Manager:ServiceManager 运行在用户空间，它负责管理 Service 注册与查询。\n" +
                "\n\n" +
                "Binder 机制跨进程通信流程:在 Binder 机制的四个部分中， Client、Server 和 ServiceManager 运行在用户空间，Binder 驱动程序运行内核空间。" +
                "Binder 就是一种把这四个组件粘合在一起的粘结剂。\n" +
                "Binder 跨进程通讯流程主要为如下 4 步：\n" +
                "1.ServiceManager 初始化: \n" +
                "1.1当该应用程序启动时，ServiceManager 会和 Binder 驱动进行通信，告诉 Binder 驱动它是服务管理者\n" +
                "1.2.Binder 驱动新建 ServiceManager 对应的 Binder 实体\n" +
                "2.Server 向 ServiceManager 注册自己\n" +
                "2.1 Server 向 Binder 驱动发起注册请求，Binder 为它创建 Binder 实体\n" +
                "2.2 然后如果 ServiceManager 中没有这个 Server 时就添加 Server 名称与 Binder 引用到它的 Binder 引用表 \n" +
                "3.Client 获取远程服务 \n" +
                "3.1 Client 首先会向 Binder 驱动发起获取服务的请求，传递要获取的服务名称\n" +
                "3.2 Binder 驱动将该请求转发给 ServiceManager 进程\n" +
                "3.3 ServiceManager 查找到 Client 需要的 Server 对应的 Binder 实体的 Binder 引用信息，然后通过 Binder 驱动反馈给 Client\n" +
                "3.4 Client 收到 Server 对应的 Binder 引用后，会创建一个 Server 对应的远程服务（即 Server 在当前进程的代理）\n" +
                "4.Client 通过代理调用 Server \n" +
                "4.1 Client 调用远程服务，远程服务收到 Client 请求之后，会和 Binder 驱动通信\n" +
                "4.2 因为远程服务中有 Server 的 Binder 引用信息，因此驱动就能轻易的找到对应的 Server，进而将Client 的请求内容发送 Server\n" +
                "\n" +
                "Binder 机制的优点:\n" +
                "1.高效简单.通过驱动在内核空间拷贝数据，不需要额外的同步处理" +
                "对比 Socket 等传输效率高\n" +
                "2.安全.Binder 机制为每个进程分配了 UID/PID 来作为鉴别身份的标示，并且在 Binder 通信时会根据UID/PID 进行有效性检测\n" +
                "3.Client/Server 架构.这种架构使得通讯更为简单"

        tv_Messenger.text = "Messenger “信使”，顾名思义，它的作用就是传递信息\n" +
                "Messenger 其实就是 AIDL 的简化版，它把接口都封装好，我们只需在一个进程创建一个 Handler 传递给 Messenger，Messenger 帮我们把消息跨进程传递到另一个进程，我们在另一个进程的 Handler 在处理消息就可以了。\n" +
                ""
        btn_bind_messenger.setOnClickListener {
            val intent = Intent(this, MessengerService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        btn_test_messenger.setOnClickListener {
            val msgContent = "你好呀"
            val message = Message.obtain()
            message.arg1 = ConfigHelper.MSG_ID_CLIENT
            val bundle = Bundle()
            bundle.putString(ConfigHelper.MSG_CONTENT, msgContent)
            message.setData(bundle)
            message.replyTo = clientMesenger

            serviceMesenger?.send(message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}
