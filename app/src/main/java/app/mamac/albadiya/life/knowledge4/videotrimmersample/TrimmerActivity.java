package app.mamac.albadiya.life.knowledge4.videotrimmersample;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import app.mamac.albadiya.AddPost;
import app.mamac.albadiya.R;
import app.mamac.albadiya.TakeGalleryFragment;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class TrimmerActivity extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {

    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;
    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);

        Intent extraIntent = getIntent();


        if (extraIntent != null) {
            path = extraIntent.getStringExtra(TakeGalleryFragment.EXTRA_VIDEO_PATH);
        }

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.trimming_progress));

        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        if (mVideoTrimmer != null) {
            Log.e("videopath",Uri.parse(path).toString());
            mVideoTrimmer.setMaxDuration(300);
             mVideoTrimmer.setOnTrimVideoListener(TrimmerActivity.this);
             mVideoTrimmer.setOnK4LVideoListener(this);
             //mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
           mVideoTrimmer.setVideoURI(Uri.parse(path));
           mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(TrimmerActivity.this,AddPost.class);
        File file = new File(uri.getPath());
        intent.putExtra("video", file.toURI().toString());
        startActivity(intent);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setDataAndType(uri, "video/mp4");
//        startActivity(intent);

        finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }


    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }

}