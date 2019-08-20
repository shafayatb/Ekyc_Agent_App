package com.gigatech.ekyc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.core.app.ActivityCompat;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import java.util.Arrays;

public class SurfaceListener implements TextureView.SurfaceTextureListener {

    private Context context;
    private int cameraCode;
    CameraDevice cameraDevice;
    String cameraId;
    TextureView textureView;
    Size imageDimensions;
    CaptureRequest.Builder captureRequestBuilder;
    CameraCaptureSession cameraSession;
    Handler backgroundHandler;
    HandlerThread handlerThread;


    SurfaceListener(Context context, int cameraCode,TextureView textureView) {

        this.context = context;
        this.cameraCode = cameraCode;
        this.textureView = textureView;

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        try {
            cameraOpen();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cameraOpen() throws CameraAccessException {

        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        cameraId = cameraManager.getCameraIdList()[cameraCode];
        CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        imageDimensions = map.getOutputSizes(SurfaceTexture.class)[0];

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cameraManager.openCamera(cameraId, stateCallback, null);

    }

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {

            cameraDevice = camera;
            try {
                startCameraPreview();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

            cameraDevice.close();

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice=null;
        }
    };

    private void startCameraPreview() throws CameraAccessException {

        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(imageDimensions.getWidth(),imageDimensions.getHeight());

        Surface surface = new Surface(surfaceTexture);
        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        captureRequestBuilder.addTarget(surface);

        cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {

                if (cameraDevice == null){
                    return;
                }
                cameraSession = session;

                try {
                    updatePreview();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {

            }
        },null);

    }

    private void updatePreview() throws CameraAccessException {
        if (cameraDevice == null){
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        cameraSession.setRepeatingRequest(captureRequestBuilder.build(),null,backgroundHandler);
    }
}
