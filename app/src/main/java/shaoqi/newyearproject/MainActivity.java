package shaoqi.newyearproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bean.ItemBean;
import service.DownLoadService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ListView listView;
    private List<ItemBean> itemBeanList = null;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private int front = -1;
    //保持服务中的binder对象
    private DownLoadService.MyBinder myBinder;
    //定义serviceConnection对象
    private ServiceConnection conn = new ServiceConnection() {
        //连接时回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: 服务回调被执行");
            //拿到对应的binder对象
            myBinder = (DownLoadService.MyBinder)service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //启动服务相关
        startService();
        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.list);
        imageView = findViewById(R.id.img);
        relativeLayout = findViewById(R.id.relative_lay);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            //设置左上角的按钮可以点击
            actionBar.setDisplayHomeAsUpEnabled(true);
            //更改左上角按钮
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }
        itemBeanList = new ArrayList<>();
        setData(itemBeanList);
        listView.setAdapter(new MyAdapter(itemBeanList,getApplicationContext()));
        listView.setOnItemClickListener(this);
    }

    //启动服务
    private void startService(){
        Intent intent = new Intent(this,DownLoadService.class);
        //绑定并启动服务
        bindService(intent,conn,BIND_AUTO_CREATE);
    }


    //设置UI数据
    private void setData(List<ItemBean> itemBeanList) {
        for(int i=0;i<20;i++){
            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher,"图片"+i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG,"进去");
        switch (item.getItemId()){
            //HomeAsUp的Id永远都是android.R.id.home
            case android.R.id.home:
                //设置展示,传参与XML一致
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.delete:
                Toast.makeText(getApplicationContext(),"您点击了删除按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.download:
                Toast.makeText(getApplicationContext(),"正在下载，请稍后...",Toast.LENGTH_SHORT).show();
                myBinder.downLoad();
                break;
            case R.id.cloud:
                Toast.makeText(getApplicationContext(),"您点击了云",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(getApplicationContext(),"您点击了设置按钮",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position++;
        int flag = position%9;
        if(flag==1&&flag!=front){
//            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a01);
//            imageView.setImageBitmap(bitmap);
//            imageView.setImageResource(R.drawable.a01);
            imageView.setBackgroundResource(R.drawable.a01);
        }else if(flag==2&&flag!=front){
//            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a02);
//            imageView.setImageBitmap(bitmap);
            imageView.setImageResource(R.drawable.a02);
        }else if(flag==3&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a03);
            imageView.setImageBitmap(bitmap);
        }else if(flag==4&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a04);
            imageView.setImageBitmap(bitmap);
        }else if(flag==5&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a05);
            imageView.setImageBitmap(bitmap);
        }else if(flag==6&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a06);
            imageView.setImageBitmap(bitmap);
        }else if(flag==7&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a07);
            imageView.setImageBitmap(bitmap);
        }else if(flag==8&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a08);
            imageView.setImageBitmap(bitmap);
        }else if(flag==0&&flag!=front){
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.a09);
            imageView.setImageBitmap(bitmap);
        }
        front = flag;
        drawerLayout.closeDrawers();
//        Toast.makeText(getApplicationContext(),"这是第"+position+"个按钮被点击了",Toast.LENGTH_SHORT).show();
    }

    class MyAdapter extends BaseAdapter{
        //用来装载每个数据源对象
        private List<ItemBean> list = null;
        //上下文对象
        private Context context = null;
        //布局装载器对象
        private LayoutInflater inflater = null;

        public MyAdapter(List<ItemBean> list, Context context) {
            this.list = list;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.activity_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_iv);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.id_tvTitle);
                //通过setTag将ViewHolder和convertView绑定
                convertView.setTag(viewHolder);
            }  else {
                //获取，通过ViewHolder找到相应的控件
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ItemBean itemBean = list.get(position);
            viewHolder.imageView.setImageResource(itemBean.ItemImageResid);
            viewHolder.tvTitle.setText(itemBean.ItemTitle);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView tvTitle;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务
        unbindService(conn);
        listView.removeAllViews();
        listView = null;
        if(itemBeanList!=null){
            itemBeanList.clear();
            itemBeanList = null;
        }
    }

}
