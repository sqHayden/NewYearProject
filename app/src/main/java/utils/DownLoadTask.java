package utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import listener.DownLoadListener;

/**
 * Created by hayden on 18-2-27.
 */

public class DownLoadTask extends AsyncTask<Void,Integer,Void>{
    private static final String TAG = "DownLoadTask";
    private int progress = 0;
    private DownLoadListener downLoadListener;
    private Context context;

    public DownLoadTask(DownLoadListener down,Context con){
        this.context = con;
        this.downLoadListener = down;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.i(TAG, "doInBackground: 进入下载执行方法");
        //执行两秒耗时操作
        try {
            while(progress<100) {
                Thread.currentThread().sleep(2000);
                progress += 8;
                if(progress>100){
                    progress = 100;
                }
                publishProgress(progress);
            }
            Looper.prepare();
            Toast.makeText(context,"下载完成",Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i(TAG, "onProgressUpdate: 进入下载中UI更新方法");
        super.onProgressUpdate(values);
        downLoadListener.onProgress(values[0]);
    }
}
