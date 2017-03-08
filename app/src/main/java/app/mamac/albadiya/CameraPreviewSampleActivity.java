package app.mamac.albadiya;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraPreviewSampleActivity extends Activity {
    private CameraPreview mPreview;
    private RelativeLayout mLayout;
    private Camera.PictureCallback mPicture;
    private MediaRecorder mediaRecorder;
    private boolean recording;
    ImageView camera_btn;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide status-bar
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide title-bar, must be before setContentView
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Requires RelativeLayout.

        setContentView(R.layout.custom_camera_layout);
        mLayout = (RelativeLayout)findViewById(R.id.camera_holder);
        camera_btn = (ImageView)findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicture = getPictureCallback();
                mPreview.mCamera.takePicture(null,null,mPicture);

//                if (recording) {
//                    mediaRecorder.stop();
//                    releaseMediaRecorder();
//                    Toast.makeText(CameraPreviewSampleActivity.this,"Video captured done!",Toast.LENGTH_SHORT).show();
//                    recording=false;
//
//                }
//                else{
//
//                if (!prepareMediaRecorder()){
//                    Toast.makeText(CameraPreviewSampleActivity.this,"Video recording failed!",Toast.LENGTH_SHORT).show();
//                }
//                    else {
//                        runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                try {
//                                    mediaRecorder.start();
//                                }
//                                catch (Exception ex){
//                                    ex.printStackTrace();
//                                }
//                            }
//                        });
//                        recording = true;
//
//                    }
//
//                }
         }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Set the second argument by your choice.
        // Usually, 0 for back-facing camera, 1 for front-facing camera.
        // If the OS is pre-gingerbreak, this does not have any effect.
        mPreview = new CameraPreview(this, 1, CameraPreview.LayoutMode.FitToParent);
        LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // Un-comment below lines to specify the size.
        //previewLayoutParams.height = 500;
        //previewLayoutParams.width = 500;

        // Un-comment below line to specify the position.
        //mPreview.setCenterPosition(270, 130);
        
        mLayout.addView(mPreview, 0, previewLayoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
        mLayout.removeView(mPreview); // This is necessary.
        mPreview = null;
    }




    private Camera.PictureCallback getPictureCallback() {

        Camera.PictureCallback picture = new Camera.PictureCallback() {


            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
//make a new picture file

                File pictureFile = getOutputMediaFile();
                if (pictureFile == null) {
                    return;
                }
                try {
//write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    Toast toast = Toast.makeText(getApplicationContext(), "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                    toast.show();

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }

//refresh camera to continue preview
                mPreview.mCamera.startPreview();
              //  saving_pic = false;
// files[image_count]=pictureFile.toURI().toString();
              //  image_path.add(pictureFile.getAbsolutePath());
                //image_path_id.add("-1");
// display_image(pictureFile);
//show_image_edit(pictureFile);

            }
        };
        return picture;
    }

    private static File getOutputMediaFile() {
//make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", "Albadiaya");

        if (!mediaStorageDir.exists()) {
//if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

//take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
//and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private boolean prepareMediaRecorder() {

        mediaRecorder = new MediaRecorder();

        mPreview.mCamera.unlock();
        mediaRecorder.setCamera(mPreview.mCamera);

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));

        mediaRecorder.setOutputFile("/sdcard/myvideo.mp4");
        mediaRecorder.setMaxDuration(600000); //set maximum duration 60 sec.
        mediaRecorder.setMaxFileSize(50000000); //set maximum file size 50M

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;

    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset(); // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            mPreview.mCamera.lock(); // lock camera for later use
        }
    }

}
