package app.mamac.albadiya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

/**
 * Created by T on 12-12-2016.
 */

public class UserEditProfile extends Activity {
    EditText name;
    EditText email;
    EditText phone;
    TextView update_btn;
    ImageView item_image;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        item_image = (ImageView) findViewById(R.id.item_image);
        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_images();
            }
        });
        get_user_details();
        update_btn = (TextView) findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_string = name.getText().toString();
                String email_string =  email.getText().toString();
                String phone_string = phone.getText().toString();
                if (name_string.equals("")){
                    Toast.makeText(UserEditProfile.this,"please enter name",Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }else if (email_string.equals("")){
                    Toast.makeText(UserEditProfile.this,"please enter email",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else {
                    Ion.with(UserEditProfile.this)
                            .load(Settings.SERVER_URL+"edit-member.php")
                            .setBodyParameter("member_id",Settings.GetUserId(UserEditProfile.this))
                            .setBodyParameter("name",name_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("phone",phone_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        //   Toast.makeText(lawyerEditProfile.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        if(selected_image_path.equals("")){
                                            edit_success();
                                        }else {
                                            upload_image();
                                        }
                                    }else {
                                        Toast.makeText(UserEditProfile.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });



    }

    private void get_user_details(){
        String url = Settings.SERVER_URL+"members.php";
        //url = "http://clients.yellowsoft.in/expo/api/advertisemen.php";

        final ProgressDialog progressDialog = new ProgressDialog(UserEditProfile.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(UserEditProfile.this)
                .load(url)
                .setBodyParameter("member_id",Settings.GetUserId(UserEditProfile.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

//                        if(e!=null){
//                            e.printStackTrace();
//                            Log.e("error",e.getMessage());
//                        }else{
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        name.setText(jsonObject.get("name").getAsString());
                        email.setText(jsonObject.get("email").getAsString());
                        phone.setText(jsonObject.get("phone").getAsString());

                        Ion.with(UserEditProfile.this)
                                .load(jsonObject.get("image").getAsString())
                                .withBitmap()
                                .placeholder(R.drawable.ic_profile)
                                .intoImageView(item_image);


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
    protected void onActivityResult(int requestCode,int resultCode,Intent imageReturnedIntent){
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
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    item_image.setImageURI(selectedImage);
                    File new_file = new File(selectedImage.getPath());
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);

                }
                break;
        }
    }

    private void upload_image(){
        final ProgressBar progressBar = new ProgressBar(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait image is loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);
        progressDialog.show();
        // progressBar.setVisibility(View.VISIBLE);
        Ion.with(this)
                .load(Settings.SERVER_URL+"add-member-image-ios.php")
                .uploadProgressBar(progressBar)
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e(String.valueOf(downloaded),String.valueOf(total));
                        progressDialog.setMax((int)total);
                        //progressBar.setProgress((int)downloaded);
                        progressDialog.setProgress((int) downloaded);
                    }
                })
                .setMultipartParameter("member_id", Settings.GetUserId(UserEditProfile.this))
                .setMultipartFile("file", "image/png", new File(selected_image_path))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        if(e!=null)
                            e.printStackTrace();
                        else {
                            if(result.isJsonNull())
                                Log.e("json_null", "null");
                            else {
                                Log.e("image_upload_res", result.toString());
                                // progressBar.setVisibility(View.GONE);
                                edit_success();
                            }

                        }

                    }
                });
    }


    private String getRealPathFromURI(Uri contentURI){
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void edit_success(){
        Toast.makeText(UserEditProfile.this,"profile edit success",Toast.LENGTH_SHORT).show();
    }


}
