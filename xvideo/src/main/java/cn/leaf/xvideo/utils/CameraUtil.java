package cn.leaf.xvideo.utils;

import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

import java.util.List;

import cn.leaf.xvideo.camera.CameraSize;

/**
 * Created by leaf on 2016/10/20.
 */
public class CameraUtil {
    private static final String TAG = "CameraUtil";

    /**
     * 相机数量
     * @return
     */
    public static int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    /**
     * 默认（背部）相机id
     * @return
     */
    public static int getDefaultCameraID() {
        int camerasCnt = getNumberOfCameras();
        int defaultCameraID = -1;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i=0; i <camerasCnt; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraID = i;
            }
        }
        return defaultCameraID;
    }

    /**
     * 前置相机id
     * @return
     */
    public static int getFrontCameraID() {
        int camerasCnt = getNumberOfCameras();
        int defaultCameraID = -1;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i=0; i <camerasCnt; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                defaultCameraID = i;
            }
        }
        return defaultCameraID;
    }

    /**
     * 根据相机id获取相机
     * @param cameraId
     * @return
     */
    public static Camera getCamera(int cameraId){
        Camera camera = null;
        try {
            camera = Camera.open(cameraId);
        }catch (Exception e){
            Log.e(TAG, "open camera failed: " + e.getMessage());
        }
        return camera;
    }

    public static int setCameraDisplayOrientation(Activity activity,
                                                  int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        Log.d(TAG, "camera display orientation: " + result);
        camera.setDisplayOrientation(result);

        return result;
    }

    /**
     * 获取大小
     * @param sizes
     * @param w
     * @param h
     * @return
     */
    public static CameraSize getOptimalSize(List<Camera.Size> sizes, int w, int h) {
        // Use a very small tolerance because we want an exact match.
        final double ASPECT_TOLERANCE = 0.1;
        final double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;

        // Start with max value and refine as we iterate over available preview sizes. This is the
        // minimum difference between view and camera height.
        double minDiff = Double.MAX_VALUE;

        // Target view height
        final int targetHeight = h;

        // Try to find a preview size that matches aspect ratio and the target view size.
        // Iterate over all available sizes and pick the largest size that can fit in the view and
        // still maintain the aspect ratio.
        for (final Camera.Size size : sizes) {
            final double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find preview size that matches the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (final Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return new CameraSize(optimalSize.width, optimalSize.height);
    }
}
