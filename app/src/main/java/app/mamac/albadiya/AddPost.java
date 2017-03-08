package app.mamac.albadiya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by T on 12-12-2016.
 */

public class AddPost extends Activity {
    EditText title;
    EditText descript;
    ImageView item_image;
    ImageView submit_btn;
    VideoView videoView;

    @Override
   public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_addpost);
        videoView = (VideoView) findViewById(R.id.videoView2);
        title = (EditText) findViewById(R.id.title);
        descript = (EditText) findViewById(R.id.descript);
        item_image = (ImageView) findViewById(R.id.item_image);
        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_images();
            }
        });
        submit_btn = (ImageView) findViewById(R.id.submit_btn);
        item_image = (ImageView) findViewById(R.id.item_image);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_string = title.getText().toString();
                String description_string = descript.getText().toString();
                if (title_string.equals("")){
                    Toast.makeText(AddPost.this,"please enter title",Toast.LENGTH_SHORT).show();
                }else if (description_string.equals("")){
                    Toast.makeText(AddPost.this,"Please enter description",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(AddPost.this)
                            .load(Settings.SERVER_URL+"add-post.php")
                            .setBodyParameter("member_id",Settings.GetUserId(AddPost.this))
                            .setBodyParameter("title",title_string)
                            .setBodyParameter("description",description_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if (result.get("status").getAsString().equals("Success")){
                                        //   Toast.makeText(lawyerEditProfile.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        if(selected_image_path.equals("")){
                                            addpost_success();
                                        } else {
                                            upload_images(result.get("post_id").getAsString());

                                        }
                                    }else {
                                        Toast.makeText(AddPost.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        if(getIntent().hasExtra("image")){
            Log.e("image_in_activity",getIntent().getStringExtra("image"));
            Picasso.with(this).load(Uri.parse(getIntent().getStringExtra("image"))).into(item_image);
            selected_image_path = getRealPathFromURI(Uri.parse(getIntent().getStringExtra("image")));

        }else if(getIntent().hasExtra("video")){
            //   Picasso.with(this).load(Uri.parse(getIntent().getStringExtra("image"))).into(item_image);
            selected_vide_path  = getRealPathFromURI(Uri.parse(getIntent().getStringExtra("video")));
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(selected_vide_path, MediaStore.Images.Thumbnails.MINI_KIND);
            item_image.setImageBitmap(thumb);
            File video_thunbnail = getOutputMediaFile();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(video_thunbnail);
                thumb.compress(Bitmap.CompressFormat.PNG, 100, out);
                selected_image_path = getRealPathFromURI(Uri.fromFile(video_thunbnail));
                // bmp is your Bitmap instance // PNG is a lossless format, the compression factor (100) is ignored
                 } catch (Exception e) {
                e.printStackTrace();
            } finally
            { try
            {
                if (out != null) { out.close();
                }
            } catch (IOException e)
            { e.printStackTrace();
            }
            }

        }

        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_image.setVisibility(View.GONE);
                if(!selected_vide_path.equals("")){

                    videoView.setVideoPath(selected_vide_path);
                    MediaController mediaController = new MediaController(AddPost.this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                  //  videoView.resolveAdjustedSize()
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.i("TAG","Duration = " + videoView.getDuration());
                        }
                    });
                    videoView.start();

                }
            }
        });

    }

    public void show_images(){
        final CharSequence[] items = {"camera","gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select_image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(items[item].equals("camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);

                }else if(items[item].equals("gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto,1);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    String selected_image_path = "";
    String selected_vide_path = "";

    protected void onActivityResult(int requestCode,int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    item_image.setImageURI(selectedImage);
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    item_image.setImageURI(selectedImage);
                    File new_file = new File(selectedImage.getPath());
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);
                }
                break;
        }
    }

    public void upload_images(final String post_id){
        final ProgressBar progressBar = new ProgressBar(this);
        final  ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait image is loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL+"add-post-image-ios.php")
                .uploadProgressBar(progressBar)
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        progressDialog.setMax((int) total);
                        progressDialog.setProgress((int) downloaded);
                    }
                })
                .setMultipartParameter("post_id",post_id)
                .setMultipartFile("file","image/png",new File(selected_image_path))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        if (e!=null){
                            e.printStackTrace();
                        }else if (result.isJsonNull()){
                            Log.e("json_null",null);
                        }else {
                            Log.e("image_path_response",result.toString());
                            if (selected_vide_path.equals("")) {
                                addpost_success();
                            }else{
                                upload_videos(post_id);
                            }
                        }
                    }
                });
    }

    public void  upload_videos(final String post_id){
        final ProgressBar progressBar = new ProgressBar(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait video is uploading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL + "add-post-video-ios.php")
                .uploadProgressBar(progressBar)
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        progressDialog.setMax((int) total);
                        progressDialog.setProgress((int) downloaded);
                    }
                })
                .setMultipartParameter("post_id",post_id)
                .setMultipartFile("video","video/mp4",new File(selected_vide_path))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        if (e!=null){
                            e.printStackTrace();
                        }else if (result.isJsonNull()){
                            Log.e("json_null",null);
                        }else {
                            Log.e("video_path_response",result.toString());
                            addpost_success();

                        }
                    }
                });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String demo;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;

    }


    public void addpost_success(){
        Toast.makeText(AddPost.this,"post added succesfully",Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(AddPost.this,HomeProfile.class);
        //startActivity(intent);
        finish();

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

}
