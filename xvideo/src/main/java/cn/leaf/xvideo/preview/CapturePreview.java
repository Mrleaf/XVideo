/*
 *  Copyright 2016 Jeroen Mols
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.leaf.xvideo.preview;

import android.view.SurfaceHolder;


import java.io.IOException;

import cn.leaf.xvideo.camera.CameraWrapper;
import cn.leaf.xvideo.utils.CLog;

public class CapturePreview implements SurfaceHolder.Callback {

	private boolean							mPreviewRunning	= false;
	private final CapturePreviewInterface	mInterface;
	public final CameraWrapper mCameraWrapper;

	public CapturePreview(CapturePreviewInterface capturePreviewInterface, CameraWrapper cameraWrapper,
			SurfaceHolder holder) {
		mInterface = capturePreviewInterface;
		mCameraWrapper = cameraWrapper;

		initalizeSurfaceHolder(holder);
	}

	@SuppressWarnings("deprecation")
	private void initalizeSurfaceHolder(final SurfaceHolder surfaceHolder) {
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // Necessary for older API's
	}

	@Override
	public void surfaceCreated(final SurfaceHolder holder) {
		// NOP
	}

	@Override
	public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
		if (mPreviewRunning) {
			try {
				mCameraWrapper.stopPreview();
				// camera.stopPreview();
				// camera.setPreviewCallback(null);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		try {
			mCameraWrapper.configureForPreview(width, height);
			//camera.getParameters();
//			final Camera.Parameters params = mNativeCamera.getNativeCameraParameters();
//			final CameraSize previewSize = getOptimalSize(params.getSupportedPreviewSizes(), viewWidth, viewHeight);
//
//			params.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
//			params.setPreviewFormat(ImageFormat.NV21);
//			camera.setParameters(params);
//			camera.setDisplayOrientation(degrees);
			CLog.d(CLog.PREVIEW, "Configured camera for preview in surface of " + width + " by " + height);
		} catch (final RuntimeException e) {
			e.printStackTrace();
			CLog.d(CLog.PREVIEW, "Failed to show preview - invalid parameters set to camera preview");
			mInterface.onCapturePreviewFailed();
			return;
		}

		try {
			mCameraWrapper.enableAutoFocus();
//			params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
		} catch (final RuntimeException e) {
			e.printStackTrace();
			CLog.d(CLog.PREVIEW, "AutoFocus not available for preview");
		}

		try {
			mCameraWrapper.startPreview(holder);
//			camera.setPreviewDisplay(holder);
//			camera.startPreview();
			setPreviewRunning(true);
		} catch (final IOException e) {
			e.printStackTrace();
			CLog.d(CLog.PREVIEW, "Failed to show preview - unable to connect camera to preview (IOException)");
			mInterface.onCapturePreviewFailed();
		} catch (final RuntimeException e) {
			e.printStackTrace();
			CLog.d(CLog.PREVIEW, "Failed to show preview - unable to start camera preview (RuntimeException)");
			mInterface.onCapturePreviewFailed();
		}
	}

	@Override
	public void surfaceDestroyed(final SurfaceHolder holder) {
		// NOP
	}

	public void releasePreviewResources() {
		if (mPreviewRunning) {
			try {
				mCameraWrapper.stopPreview();
				setPreviewRunning(false);
			} catch (final Exception e) {
				e.printStackTrace();
				CLog.e(CLog.PREVIEW, "Failed to clean up preview resources");
			}
		}
	}

	protected void setPreviewRunning(boolean running) {
		mPreviewRunning = running;
	}

}