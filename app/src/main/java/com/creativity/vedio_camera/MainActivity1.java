package com.creativity.vedio_camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.creativity.vedio_camera.alphamovie.AlphaMovieView;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

import java.io.File;

public class MainActivity1 extends AppCompatActivity {

    public static final String FILENAME = "ball.mp4";
    public static String FILENAME_uri = "/storage/emulated/0/data/rose1.mp4";

    private AlphaMovieView alphaMovieView;

    Button btnChoose;
    private LibVLC libVlc;
    private MediaPlayer mediaPlayer;
    private VLCVideoLayout videoLayout;


    String cameraID;
    CameraDevice cameraDevice;
    CameraCaptureSession cameraCaptureSessions;
    CaptureRequest.Builder captureRequestBuilder;
    Size imageDimension;
//    ImageReader imageReader;

    //Save to File
    File file;
    static final int REQUEST_CAMERA_PERMISSION = 200;

    static final int REQUEST_TAKE_GALLERY_VIDEO = 100;
    //    boolean mFlashSupported;
    Handler mBackgroundHandler;
    HandlerThread mBackgroundThread;

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera_Device) {
            cameraDevice = camera_Device;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
//            cameraDevice=null;
        }
    };
    //
//    TextureView textureVideoView;
//    private MediaPlayer mediaPlayer;
//    AssetFileDescriptor fileDescriptor;
    private static final String url = "rtsp://admin:a12345678@192.168.1.64/Streaming/channels/001/?transportmode=multicast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main1);
        libVlc = new LibVLC(this);
        mediaPlayer = new MediaPlayer(libVlc);
        videoLayout = findViewById(R.id.videoLayout);



        // textureView.setSurfaceTextureListener(textureListener);

        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alphaMovieView.setVideoFromAssets("rose1.mp4");
//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.heart1);
//                alphaMovieView.setVideoFromUri(MainActivity1.this,uri);

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);
            }
        });

        alphaMovieView = findViewById(R.id.video_player);
        alphaMovieView.setVideoFromAssets(FILENAME);
        alphaMovieView.setLooping(true);

        // initiate a video view
//        VideoView simpleVideoView =  findViewById(R.id.videoView); // initiate a video view
////        Uri uri = Uri.parse("/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4");
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.heart1);
//        simpleVideoView.setVideoURI(uri);
//
////        String path="/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4";
////        simpleVideoView.setVideoPath(path);
//
//        // create an object of media controller
//        MediaController mediaController = new MediaController(this);
//
//        // set media controller object for a video view
//        simpleVideoView.setMediaController(mediaController);
//
//        simpleVideoView.start();
//        simpleVideoView.setOnCompletionListener(mediaPlayer1 -> simpleVideoView.start());

//        textureVideoView = findViewById(R.id.textureVideoView);
//        textureVideoView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
//            @Override
//            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
//                Surface surface = new Surface(surfaceTexture);
//                try {
//                    mediaPlayer.setSurface(surface);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        mediaPlayer.setDataSource(fileDescriptor);
//                        mediaPlayer.prepareAsync();
//                        mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
//                        mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.start());
//                    }
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {}
//            @Override
//            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {return false;}
//            @Override
//            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {}
//        });
//        mediaPlayer = new MediaPlayer();
//        try {
//            fileDescriptor = getAssets().openFd("rose1.mp4");
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void play(View view) {
        alphaMovieView.start();
    }

    public void pause(View view) {
        alphaMovieView.pause();
    }

    public void stop(View view) {
        alphaMovieView.stop();
    }


    //        btnCapture= findViewById(R.id.btnCapture);
//        btnCapture.setOnClickListener(view -> takePicture());
//    }
//
//    private void takePicture() {
//        if(cameraDevice == null)
//            return;
//        CameraManager manager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
//            Size[] jpegSizes = null;
//            if (characteristics != null) {
//                jpegSizes=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
//            }
//            // Capture image with custom size
//            int width=640;
//            int height=480;
//            if(jpegSizes!=null && jpegSizes.length>0){
//                width=jpegSizes[0].getWidth();
//                height=jpegSizes[0].getHeight();
//            }
//            ImageReader reader = ImageReader.newInstance(width,height, ImageFormat.JPEG,1);
//            List<Surface> outputSurface = new ArrayList<>(2);
//            outputSurface.add(reader.getSurface());
//            outputSurface.add(new Surface(textureView.getSurfaceTexture()));
//
//            CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
//            captureBuilder.addTarget(reader.getSurface());
//            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//
//            //check orientaion base on window
//            int rotation = getWindowManager().getDefaultDisplay().getRotation();
//            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));
//
//            file=new File(Environment.getExternalStorageDirectory()+"/"+ UUID.randomUUID().toString()+".jpg");
//            ImageReader.OnImageAvailableListener readerListner= new ImageReader.OnImageAvailableListener(){
//                @Override
//                public void onImageAvailable(ImageReader imageReader) {
//                    try (Image image = reader.acquireLatestImage()) {
//                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
//                        byte[] bytes = new byte[buffer.capacity()];
//                        buffer.get(bytes);
//                        save(bytes);
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//
//                private void save(byte[] bytes) throws IOException {
//                    try (OutputStream outputStream = new FileOutputStream(file)) {
//                        outputStream.write(bytes);
//                    }
//                }
//            };
//            reader.setOnImageAvailableListener(readerListner,mBackgroundHandler);
//            CameraCaptureSession.CaptureCallback captureListener=new CameraCaptureSession.CaptureCallback() {
//                @Override
//                public void onCaptureCompleted(@NonNull CameraCaptureSession session,
//                                               @NonNull CaptureRequest request,
//                                               @NonNull TotalCaptureResult result) {
//                    super.onCaptureCompleted(session, request, result);
//                    Toast.makeText(MainActivity.this,"Saved"+file,Toast.LENGTH_LONG).show();
//                    createCameraPreview();
//                }
//            };
//
//            cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
//                @Override
//                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
//                    try{
//                        cameraCaptureSession.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
//                    }catch (CameraAccessException ex){
//                        ex.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
//
//                }
//            },mBackgroundHandler);
//
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedVideoUri = data.getData();

                // OI FILE Manager
                String filemanagerstring = selectedVideoUri.toString();
                FILENAME_uri = filemanagerstring;
//                Toast.makeText(this,filemanagerstring,Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse(FILENAME_uri);
                alphaMovieView.setVideoFromUri(MainActivity1.this,uri);
                alphaMovieView.setLooping(true);
            }
        }
    }

    private void createCameraPreview() {
        /*try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            if (cameraDevice == null)
                                return;
                            cameraCaptureSessions = cameraCaptureSession;
                            updatePreview();
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                            Toast.makeText(MainActivity1.this, "Changed", Toast.LENGTH_LONG).show();
                        }
                    }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }*/
    }

    private void updatePreview() {
        if (cameraDevice == null)
            Toast.makeText(MainActivity1.this, "Error", Toast.LENGTH_SHORT).show();
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(),
                    null, mBackgroundHandler);
        } catch (CameraAccessException ex) {
            ex.printStackTrace();
        }
    }


    private void openCamera() {

    /*    try {
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraID = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraID);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // check realtime permition if run higher API 23
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                }, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraID, stateCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }*/
    }

    // Ctrl + o

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
     /*   if (textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(textureListener);
*/
        alphaMovieView.onResume();
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
        alphaMovieView.onPause();
    }

    private void stopBackgroundThread() {
        try {
            mBackgroundThread.quitSafely();
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }


    @Override
    protected void onStart() {
        super.onStart();

        mediaPlayer.attachViews(videoLayout, null, false, false);

        Media media = new Media(libVlc, Uri.parse(url));
        media.setHWDecoderEnabled(true, false);
        media.addOption(":network-caching=600");

        mediaPlayer.setMedia(media);
        media.release();
        mediaPlayer.play();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mediaPlayer.stop();
        mediaPlayer.detachViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediaPlayer.release();
        libVlc.release();
    }
}