package cn.leaf.xvideo.preview;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import cn.leaf.xvideo.camera.CameraSize;
import cn.leaf.xvideo.utils.CameraUtil;


/**
 * 捕获图像
 * Created by leaf on 2016/10/20.
 */
public class CapturePreview implements SurfaceHolder.Callback {
    private boolean	mPreviewRunning	= false;
    private Camera mCamera;
    public CapturePreview(Camera camera,SurfaceHolder holder){
        this.mCamera = camera;
        initalizeSurfaceHolder(holder);
    }

    @SuppressWarnings("deprecation")
    private void initalizeSurfaceHolder(final SurfaceHolder surfaceHolder) {
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //针对老的API
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        CameraSize previewSize = CameraUtil.getOptimalSize(parameters.getSupportedPreviewSizes(), width, height);
        parameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setPreviewFormat(ImageFormat.NV21);
//        mCamera.setDisplayOrientation(degrees);
//        mCamera.setParameters(parameters);
//        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }



    protected void setPreviewRunning(boolean running) {
        mPreviewRunning = running;
    }
}
