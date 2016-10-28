package cn.leaf.xvideo.recorder;


import android.view.SurfaceHolder;

import cn.leaf.xvideo.camera.CameraWrapper;
import cn.leaf.xvideo.preview.CapturePreview;
import cn.leaf.xvideo.preview.CapturePreviewInterface;

/**
 * Created by leaf on 2016/10/19.
 */
public class VideoRecorder implements CapturePreviewInterface {

    private CameraWrapper mCameraWrapper;
    private CapturePreview mCapturePreview;

    public VideoRecorder(CameraWrapper cameraWrapper, SurfaceHolder surfaceHolder){
        this.mCameraWrapper = cameraWrapper;

        initCameraAndPreview(surfaceHolder);
    }

    protected void initCameraAndPreview(SurfaceHolder previewHolder) {
        try {
            mCameraWrapper.openCamera();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        mCapturePreview = new CapturePreview(this, mCameraWrapper, previewHolder);
    }

    @Override
    public void onCapturePreviewFailed() {

    }
}
