package com.example.writenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class OneActivity extends Activity {

    private ImageView iv_frame;
    int i = 1;
    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;
    int igvx;
    int igvy;
    int type = 0;
    int widthPixels;
    int heightPixels;
    float scaleWidth;
    float scaleHeight;
    Timer touchTimer = null;
    Bitmap arrdown;
    boolean typedialog = true;
    private LinearLayout linearLayout = null;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        if (MainActivity.isPlay)
            PlayMusic();

        initView();
    }

    private void PlayMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initView() {
        iv_frame = findViewById(R.id.iv_frame);
        linearLayout = findViewById(R.id.LinearLayout1);
        LinearLayout writeLayout = findViewById(R.id.LinearLayout_number);

        writeLayout.setBackgroundResource(R.drawable.bg1);
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;

        scaleWidth = ((float) widthPixels / 720);
        scaleHeight = ((float) heightPixels / 1280);

        try {
            InputStream is = getResources().getAssets().open("on1_1.png");
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_frame.getLayoutParams();
        layoutParams.width = (int) (arrdown.getWidth() * scaleHeight);
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);

        iv_frame.setLayoutParams(layoutParams);
        lodimagep(1);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        x2 = event.getY();
                        igvx = iv_frame.getLeft();
                        igvy = iv_frame.getTop();

                        if (x1 >= igvx && x1 <= igvx + (int) (arrdown.getWidth() * scaleWidth)
                                        && y1 >= igvy & y1 <= igvy + (int) (arrdown.getWidth() * scaleWidth)) {
                            type = 1;
                        } else {
                            type = 0;
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        igvx = iv_frame.getLeft();
                        igvy = iv_frame.getTop();
                        x2 = event.getX();
                        y2 = event.getY();

                        if (type == 1) {
                            // TODO: write number here ......
                        }

                    case MotionEvent.ACTION_UP:
                        type = 0;
                        if (touchTimer != null) {
                            touchTimer.cancel();
                            touchTimer = null;
                        }

                        touchTimer = new Timer();
                        touchTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message = new Message();
                                        message.what = 2;
                                        mHandler.sendMessage(message);
                                    }
                                });
                            }
                        }, 300, 200);
                }

                return true;
            }
        });
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    jlodimage();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void jlodimage() {
        if (i == 25) {

        } else if (i < 25) {
            if (i > 1) {
                i--;
            } else if (i == 1) {
                i = 1;
                if (touchTimer != null) {
                    touchTimer.cancel();
                    touchTimer = null;
                }
            }
            String name = "on1_" + i;
            int imgid = getResources().getIdentifier(name, "drawable", "com.example.writenumber");
            iv_frame.setBackgroundResource(imgid);
        }
    }

    private synchronized void lodimagep(int j) {
        i = j;
        if (i < 25) {
            String name = "on1_" + i;
            int imgid = getResources().getIdentifier(name, "drawable", "com.example.writenumber");
            iv_frame.setBackgroundResource(imgid);
            i++;
        }
        if (i == 24) {
            if (typedialog)
                dialog();
        }
    }

    protected void dialog() {
        typedialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(OneActivity.this);
        builder.setMessage("太棒了！书写完成！");
        builder.setTitle("提示");

        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();   // dialog消失
                typedialog = true;
                finish();
            }
        });

        builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                typedialog = true;
                i = 1;
                lodimagep(i);
            }
        });
        builder.create().show();
    }
}
