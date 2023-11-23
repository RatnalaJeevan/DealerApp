package com.wisedrive.dealerapp1;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;

    Camera camera;
    public CameraPreview(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Open the camera and set the preview display
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes, if needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Release the camera resources
        camera.stopPreview();
        camera.release();
    }
}

