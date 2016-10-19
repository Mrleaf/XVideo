package cn.leaf.xvideo.recorder;

import android.hardware.Camera;

import cn.leaf.xvideo.view.VideoCaptureView;

/**
 * Created by leaf on 2016/10/19.
 */
public class VideoRecorder implements Camera.PreviewCallback{

    private VideoCaptureView videoCaptureView;
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
