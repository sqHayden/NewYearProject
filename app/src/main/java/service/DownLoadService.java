package service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import listener.DownLoadListener;
import shaoqi.newyearproject.R;
import utils.DownLoadTask;

/**
 * Created by hayden on 18-2-27.
 */

public class DownLoadService extends Service{

    private static final String TAG = "DownLoadService";
    //通知消息管理类
    private NotificationManager notificationManager;
    //通知消息类对象
    private Notification notification;
    //标识ID
    private static final int NOTIFICATION_ID = 0x123;
    //交互对象
    private MyBinder binder = new MyBinder();
    //下载对象
    private DownLoadTask downLoadTask;
    //builder对象
    private NotificationCompat.Builder builder;
    //监听器
    private DownLoadListener downLoadListener = new DownLoadListener() {
        @Override
        public void onProgress(int progress) {
            //通知发送
            notificationManager.notify(NOTIFICATION_ID, getNotification(progress));
        }
    };

    //必须实现的方法,通过IBinder对象建立通信
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //Service第一次被创建后立即执行
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 服务被创建");
    }
    

    //调用startService(Intent)方法时都会执行
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }
    

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //自定义的Binder对象
    public class MyBinder extends Binder{
        //这里就是交互方法的定义了
        //下载的相关操作
        public void downLoad() {
            Log.i(TAG, "downLoad: 服务方法被执行");
            //建立notification
            notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(getApplicationContext());
            //设置打开该通知时通知自动消失
            builder.setAutoCancel(true);
            //设置显示在状态栏的通知提示信息
            builder.setTicker("有新消息");
            //设置通知的图标
            builder.setSmallIcon(R.mipmap.ic_launcher);
            //设置通知内容的标题
            builder.setContentTitle("一条新通知");
            if(downLoadTask==null){
                downLoadTask = new DownLoadTask(downLoadListener,getApplicationContext());
                downLoadTask.execute();
            }else{
                downLoadTask.execute();
            }
        }
    }

    //创建同一对象
    private Notification getNotification(int pro){
        //设置通知内容
        builder.setContentText("当前完成度： "+pro+" %");
        //设置进度条(最大值，初始值，条纹滚动效果)
        builder.setProgress(100,pro,false);
        return builder.build();
    }
}
