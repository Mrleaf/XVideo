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

package cn.leaf.xvideo.view;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import cn.leaf.xvideo.R;
import cn.leaf.xvideo.preview.CapturePreview;
import cn.leaf.xvideo.utils.CameraUtil;

/**
 * Created by leaf on 2016/10/19.
 */
public class VideoCaptureView extends FrameLayout implements OnClickListener {
	private Camera mCamera;
    private ImageView mRecordBtn,mThumbnail,mImgBack;
    private SurfaceView mSurfaceView;
	private CapturePreview capturePreview;
	private VideoButtonInterface mVideoButtonInterface;
	public VideoCaptureView(Context context) {
		super(context);
		initialize(context);
	}

	public VideoCaptureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	public VideoCaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize(context);
	}

	private void initialize(Context context) {
		final View videoCapture = View.inflate(context, R.layout.view_videocapture, this);
		mImgBack = (ImageView)videoCapture.findViewById(R.id.img_back);
		mRecordBtn = (ImageView) videoCapture.findViewById(R.id.record_btn);
        mThumbnail = (ImageView) videoCapture.findViewById(R.id.thumbnail);
        mSurfaceView = (SurfaceView) videoCapture.findViewById(R.id.preview_sv);
		mImgBack.setOnClickListener(this);
		mRecordBtn.setOnClickListener(this);
    }


	public SurfaceHolder getPreviewSurfaceHolder() {
		return mSurfaceView.getHolder();
	}

	public void setCamera(Camera camera,int mCameraId){
		this.mCamera = camera;
		CameraUtil.setCameraDisplayOrientation((Activity) getContext(), mCameraId, mCamera);
		capturePreview = new CapturePreview(mCamera,getPreviewSurfaceHolder());
	}

	public void setVideoButtonInterface(VideoButtonInterface videoButtonInterface){
		this.mVideoButtonInterface = videoButtonInterface;
	}

    @Override
    public void onClick(View v) {
		if(mVideoButtonInterface==null){
			return;
		}
		if(v.getId()==mImgBack.getId()){
			mVideoButtonInterface.onBackButtonClicked();
		}else if(v.getId()==mRecordBtn.getId()){
			mVideoButtonInterface.onRecordButtonClicked();
		}
    }
}
