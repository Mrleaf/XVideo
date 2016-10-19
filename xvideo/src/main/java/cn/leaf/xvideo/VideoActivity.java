package cn.leaf.xvideo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.leaf.xvideo.view.VideoButtonInterface;
import cn.leaf.xvideo.view.VideoCaptureView;

public class VideoActivity extends Activity implements VideoButtonInterface,View.OnClickListener{

    private VideoCaptureView videoCaptureView;
    private ImageView mImgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_videocapture);
        init();
        initListener();
    }

    private void init(){
        videoCaptureView = (VideoCaptureView)findViewById(R.id.videocapture);
        mImgBack = (ImageView)findViewById(R.id.image_back);
    }

    private void initListener(){
        if(mImgBack!=null)
            mImgBack.setOnClickListener(this);
        if(videoCaptureView!=null)
            videoCaptureView.setVideoButtonInterface(this);
    }

    @Override
    public void onRecordButtonClicked() {

    }

    @Override
    public void onBackButtonClicked() {
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==mImgBack.getId()){
            finish();
        }
    }
}
