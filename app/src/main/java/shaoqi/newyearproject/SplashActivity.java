package shaoqi.newyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by hayden on 18-2-27.
 */

public class SplashActivity extends AppCompatActivity {

    private static int DELAY_TIME = 3*1000;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        setContentView(R.layout.activity_start);
        //延时三秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"准备跳转");
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //关闭splashActivity，将其回收，否则按返回键会返回此界面
//                SplashActivity.this.finish();
            }
        }, DELAY_TIME);
    }
}
