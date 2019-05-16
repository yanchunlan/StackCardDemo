package com.example.stack.videocall;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.stack.R;

public class VideoCallActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoCallLayout mVideoCallLayout;
    private Button mAdd;
    private Button mRemove;
    private int[] colors = new int[]{
        R.color.color_F44336,
        R.color.color_E91E63,
        R.color.color_9C27B0,
        R.color.color_3F51B5,
        R.color.color_2196F3,
        R.color.color_00BCD4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        initView();
    }

    private void initView() {
        mVideoCallLayout = (VideoCallLayout) findViewById(R.id.video_call_layout);
        mAdd = (Button) findViewById(R.id.add);
        mRemove = (Button) findViewById(R.id.remove);

        mAdd.setOnClickListener(this);
        mRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                if (mVideoCallLayout.getChildCount() < 6) {
                    ImageView imageView = new ImageView(this);
                    imageView.setBackgroundColor(ContextCompat.getColor(this,
                            colors[(int) (Math.random()*colors.length)]));
                    mVideoCallLayout.addView(imageView);
                }
                break;
            case R.id.remove:
                if (mVideoCallLayout.getChildCount() > 0) {
                    mVideoCallLayout.removeViewAt(0);
                }
                break;
        }
    }
}
