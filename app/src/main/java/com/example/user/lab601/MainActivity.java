package com.example.user.lab601;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected Button start;
    protected SeekBar rabbitseekbar,tortoiseseekbar;
    int rabbitcount=0,tortoisecount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start =(Button)findViewById(R.id.start);
        rabbitseekbar=(SeekBar)findViewById(R.id.rabbitseekbar);
        tortoiseseekbar=(SeekBar)findViewById(R.id.tortoiseseekbar);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"開始",Toast.LENGTH_LONG).show();
                runThread();
                runAsyncTask();
            }
        });
    }

    private  void runThread(){
        rabbitcount=0;
        new Thread(){
            public void run(){
                do{
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    rabbitcount+=(int)(Math.random()*3);
                    Message msg=new Message();
                    msg.what=1;
                    mHandler.sendMessage(msg);
                }while (rabbitcount<=100);
            }
        }.start();
    }

    private Handler mHandler=new Handler(){
        @Override
        public  void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    rabbitseekbar.setProgress(rabbitcount);
                    break;
            }
            if(rabbitcount>=100)
                if(tortoisecount<100)
                    Toast.makeText(MainActivity.this,"兔子完成",Toast.LENGTH_LONG).show();
        }
    };


    private  void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            @Override
            protected  void onPreExecute(){
                super.onPreExecute();
                tortoisecount=0;
            }
            @Override
            protected Boolean doInBackground(Void...voids){
                do {
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    tortoisecount+=(int)(Math.random()*3);
                    publishProgress(tortoisecount);
                }while(tortoisecount<=100);
                return true;
            }
            @Override
            protected void onProgressUpdate(Integer...values){
                super.onProgressUpdate(values);
                tortoiseseekbar.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean status){
                if(rabbitcount<100)
                    Toast.makeText(MainActivity.this,"烏龜獲勝",Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}
