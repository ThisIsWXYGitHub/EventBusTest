package com.example.lenovo.eventbustest;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {

	Button btn;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		EventBus.getDefault().register(this);

		btn = (Button) findViewById(R.id.btn_try);
		tv = (TextView)findViewById(R.id.tv);

		btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(),
                        SecondActivity.class);
                startActivity(intent);
            }
        });
	}


    /*
        ui 线程，onEventMainThread方法中不能执行耗时操作
         */
	public void onEventMainThread(FirstEvent event) {
        String name=Thread.currentThread().getName();
		String msg = "onEventMainThread" + event.getMsg();
		Log.d("harvic", msg);
		tv.setText(msg);
		Toast.makeText(this, msg+"  "+name, Toast.LENGTH_LONG).show();
	}


    /*
    如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，onEvent就会在这个线程中运行，
    也就是说发布事件和接收事件线程在同一个线程。使用这个方法时，
    在onEvent方法中不能执行耗时操作，如果执行耗时操作容易导致事件分发延迟。
     */
	public void onEvent(FirstEvent event)
    {
        String name=Thread.currentThread().getName();
        Toast.makeText(MainActivity.this, "onEvent方法"+"  "+name, Toast.LENGTH_SHORT).show();
    }

    public void onEventBackgroundThread(FirstEvent event)
    {
        String name=Thread.currentThread().getName();
        Log.d("123456789","onEventBackgroundThread"+"  "+name);
    }


     public void onEventAsync(FirstEvent event)
    {
        String name=Thread.currentThread().getName();
        Log.d("123456789","onEventAsync"+"   "+name);
    }

	@Override
	protected void onDestroy(){
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
