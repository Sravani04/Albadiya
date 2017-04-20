package app.mamac.albadiya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by T on 20-12-2016.
 */

public class CompetitorAddPost extends Activity {
    ImageView back_btn;
    EditText item_title;
    EditText item_description;
    ImageView item_image;
    ImageView submit_btn;
    String competition_id;
    VideoView videoview;
    boolean is_video_added = true;
    Integer REQUEST_CAMERA=1,SELECT_FILE=0,REQUEST_VIDEO=2;

     @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competitor_addpost);

        back_btn          = (ImageView) findViewById(R.id.back_btn);
        item_title        = (EditText) findViewById(R.id.item_title);
        item_description  = (EditText) findViewById(R.id.item_description);
        item_image        = (ImageView)findViewById(R.id.item_image);
        item_image.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     show_images();
                 }
             });
         videoview = (VideoView) findViewById(R.id.videoView);
         submit_btn        = (ImageView) findViewById(R.id.submit_btn);
          back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompetitorAddPost.this.onBackPressed();
             }
        });

         if (getIntent()!=null){
             competition_id = getIntent().getStringExtra("id");
         }

         submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_string = item_title.getText().toString();
                String description_string = item_description.getText().toString();
                //String competiton = comp_id.getText().toString();
                if (title_string.equals("")){
                    Toast.makeText(CompetitorAddPost.this,"please add title",Toast.LENGTH_SHORT).show();
                    item_title.requestFocus();
                }else if (description_string.equals("")){
                    Toast.makeText(CompetitorAddPost.this,"please add description",Toast.LENGTH_SHORT).show();
                    item_description.requestFocus();
                } else{
                    final ProgressBar progressBar = new ProgressBar(CompetitorAddPost.this);
                    final  ProgressDialog progressDialog = new ProgressDialog(CompetitorAddPost.this);
                    progressDialog.setMessage("Please wait  image is loading..");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(CompetitorAddPost.this)
                            .load(Settings.SERVER_URL + "add-competition-image-ios.php")
                            .uploadProgressBar(progressBar)
                            .uploadProgressHandler(new ProgressCallback() {
                                @Override
                                public void onProgress(long downloaded, long total) {
                                    progressDialog.setMax((int) total);
                                    progressDialog.setProgress((int) downloaded);
                                }
                            })
                            .setMultipartParameter("member_id", Settings.GetUserId(CompetitorAddPost.this))
                            .setMultipartParameter("competition_id",competition_id)
                            .setMultipartParameter("title", title_string)
                            .setMultipartParameter("description", description_string)
                            .setMultipartFile("file", "image/png", new File(selected_image_path))
                            .asJsonArray()
                            .setCallback(new FutureCallback<JsonArray>() {
                                @Override
                                public void onCompleted(Exception e, JsonArray result) {
                                    try {
                                        if (progressDialog!=null)
                                            progressDialog.dismiss();
                                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                                        if (e != null)
                                            e.printStackTrace();
                                        if (jsonObject.get("status").getAsString().equals("Success")) {
                                            if (selected_vide_path.equals("")) {
                                                post_success();
                                                Log.e("success", selected_vide_path);
                                            } else {
                                                upload_videos(jsonObject.get("id").getAsString());
                                            }
                                        } else {
                                            Toast.makeText(CompetitorAddPost.this, "Already Posted", Toast.LENGTH_SHORT).show();
                                            CompetitorAddPost.this.onBackPressed();
                                        }
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                        Toast.makeText(CompetitorAddPost.this, "Already Posted", Toast.LENGTH_SHORT).show();
                                        CompetitorAddPost.this.onBackPressed();
                                    }

                                }
                            });
                }


            }
        });

         if(getIntent().hasExtra("image")) {
             Log.e("image_in_activity", getIntent().getStringExtra("image"));
             Picasso.with(this).load(Uri.parse(getIntent().getStringExtra("image"))).into(item_image);
             selected_image_path = getRealPathFromURI(Uri.parse(getIntent().getStringExtra("image")));
         }



     }

    public void get_thumbnail(){
        //   Picasso.with(this).load(Uri.parse(getIntent().getStringExtra("image"))).into(item_image);
        //selected_vide_path  = getRealPathFromURI(Uri.parse(getIntent().getStringExtra("video")));
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


        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_image.setVisibility(View.GONE);
                if(!selected_vide_path.equals("")){

                    videoview.setVideoPath(selected_vide_path);
                    MediaController mediaController = new MediaController(CompetitorAddPost.this);
                    mediaController.setAnchorView(videoview);
                    videoview.setMediaController(mediaController);
                    //  videoView.resolveAdjustedSize()
                    videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.i("TAG","Duration = " + videoview.getDuration());
                        }
                    });
                    videoview.start();

                }
            }
        });


    }


    public void show_images(){
        final CharSequence[] items = {"Take Photo","Take Video","gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select_image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(items[item].equals("Take Photo")){
                    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }else if(items[item].equals("Take Video")){
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent,REQUEST_VIDEO);
                } else if(items[item].equals("gallery")){
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/* video/*");
                    startActivityForResult(pickIntent, SELECT_FILE);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }




    String selected_image_path = "";
    String selected_vide_path = "";

//    protected void onActivityResult(int requestCode,int resultCode, Intent imageReturnedIntent){
//        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
//        switch (requestCode){
//            case 1:
//                if (resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    item_image.setImageURI(selectedImage);
//                    selected_image_path = getRealPathFromURI
//                            (selectedImage);
//                    Log.e("image_path",selected_image_path);
//                    if (selected_image_path.endsWith(".mp4")) {
//                        selected_vide_path = selected_image_path;
//                        get_thumbnail();
//                    }
//
//                }
//                break;
//            case 2:
//                if (resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    item_image.setImageURI(selectedImage);
//                    File new_file = new File(selectedImage.getPath());
//                    selected_image_path = getRealPathFromURI(selectedImage);
//                    Log.e("selected_image_path",selected_image_path);
//                    if (selected_image_path.endsWith(".mp4")){
//                        selected_vide_path = selected_image_path;
//                        get_thumbnail();
//                    }
//                }
//                break;
//
//            case 3:
//                if (resultCode == RESULT_OK){
//                    if(selected_image_path.endsWith(".jpg"))  {
//                        Log.e("image_path",selected_image_path);
//                        Uri selectedImage = imageReturnedIntent.getData();
//                        item_image.setImageURI(selectedImage);
//                        selected_image_path = getRealPathFromURI(selectedImage);
//                        }
//
//                }
//                break;
//
//        }
//    }
    protected void onActivityResult(int requestCode,int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && imageReturnedIntent != null) {
                Bundle bundle = imageReturnedIntent.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                item_image.setImageBitmap(bmp);
                Uri selectedImage = getImageUri(getApplicationContext(), bmp);
                selected_image_path = getRealPathFromURI(selectedImage);
                Toast.makeText(CompetitorAddPost.this, "Here " + getRealPathFromURI(selectedImage), Toast.LENGTH_LONG).show();
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImage = imageReturnedIntent.getData();
                item_image.setImageURI(selectedImage);
                File new_file = new File(selectedImage.getPath());
                selected_image_path = getRealPathFromURI(selectedImage);
                Log.e("selected_image_path", selected_image_path);
                if (selected_image_path.endsWith(".mp4")) {
                    selected_vide_path = selected_image_path;
                    get_thumbnail();
                }
            } else if (requestCode == REQUEST_VIDEO) {
                Uri selectedImage = imageReturnedIntent.getData();
                item_image.setImageURI(selectedImage);
                selected_image_path = getRealPathFromURI(selectedImage);
                Log.e("video_path", selected_image_path);
                if (selected_image_path.endsWith(".mp4")) {
                    selected_vide_path = selected_image_path;
                    get_thumbnail();
                }
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    public void  upload_videos(final String id){
        final ProgressBar progressBar = new ProgressBar(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait video is uploading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Settings.SERVER_URL + "add-competition-video-ios.php")
                .uploadProgressBar(progressBar)
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        progressDialog.setMax((int) total);
                        progressDialog.setProgress((int) downloaded);
                    }
                })
                .setMultipartParameter("competition_id",id)
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
                            post_success();
                        }
                    }
                });
    }

    public void post_success(){
        Toast.makeText(CompetitorAddPost.this,"post added successfully",Toast.LENGTH_SHORT).show();
        CompetitorAddPost.this.onBackPressed();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI,null, null, null, null);
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

//    public String getPath(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }







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
