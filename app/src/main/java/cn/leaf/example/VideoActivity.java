package cn.leaf.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.leaf.xvideo.camera.CameraWrapper;
import cn.leaf.xvideo.camera.NativeCamera;
import cn.leaf.xvideo.recorder.VideoRecorder;
import cn.leaf.xvideo.view.VideoButtonInterface;
import cn.leaf.xvideo.view.VideoCaptureView;

public class VideoActivity extends Activity implements VideoButtonInterface,View.OnClickListener{
    private VideoCaptureView videoCaptureView;
    private ImageView mImgBack;
    private VideoRecorder mVideoRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(cn.leaf.xvideo.R.layout.activity_videocapture);
        init();
        initListener();

    }

    private void init(){
        videoCaptureView = (VideoCaptureView)findViewById(cn.leaf.xvideo.R.id.videocapture);
        mImgBack = (ImageView)findViewById(cn.leaf.xvideo.R.id.image_back);
        if(videoCaptureView!=null){
            initRecorder();
        }
    }

    private void initRecorder(){
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        mVideoRecorder = new VideoRecorder(new CameraWrapper(new NativeCamera(),display.getRotation()),videoCaptureView.getPreviewSurfaceHolder());
    }
    private void initListener(){
        if(mImgBack!=null)
            mImgBack.setOnClickListener(this);
        if(videoCaptureView!=null){
            videoCaptureView.setVideoButtonInterface(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause(){
        super.onPause();


    }

    /**
     * 录像
     */
    @Override
    public void onRecordButtonClicked() {

    }
    /**
     * 返回
     */
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
