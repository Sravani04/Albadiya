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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by T on 23-12-2016.
 */

public class ChatScreen extends Activity {
    ListView listView;
    ChatScreenAdapter chatScreenAdapter;
    Posts posts;
    ArrayList<Chats> chatsfrom_api;
    String member_id;
    String receiver_id;
    ImageView send_btn;
    EditText text_message;
    CircleImageView image;
    TextView name;
    ImageView select_files;



    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist_screen);
        listView = (ListView) findViewById(R.id.chat_list);

        chatsfrom_api = new ArrayList<>();
        send_btn = (ImageView) findViewById(R.id.send_btn);
        member_id = Settings.GetUserId(this);
        if(getIntent()!=null && getIntent().hasExtra("receiver_id")){
            receiver_id=getIntent().getStringExtra("receiver_id");
        }else{
            finish();
        }

        text_message = (EditText) findViewById(R.id.text_message);
        select_files = (ImageView) findViewById(R.id.select_files);
        select_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_images();
            }
        });


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =  text_message.getText().toString();
                if (message.equals("")){
                    if(selected_image_path.equals("")){
                        Toast.makeText(ChatScreen.this, "empty_msg", Toast.LENGTH_SHORT).show();
                    }else{
                        upload_image();
                    }
                }else {
                    Ion.with(ChatScreen.this)
                            .load(Settings.SERVER_URL + "chat.php")
                            .setBodyParameter("member_id", member_id)
                            .setBodyParameter("receiver_id", receiver_id)
                            .setBodyParameter("msg_type", "text")
                            .setBodyParameter("description",message)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                           chat_success();
                                    }else {
                                        Toast.makeText(ChatScreen.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        ImageView back_btn;
        back_btn = (ImageView) findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chatsfrom_api = new ArrayList<>();
        get_chats();
        postsfrom_api = new ArrayList<>();


        chatScreenAdapter = new ChatScreenAdapter(this,chatsfrom_api);
        listView.setAdapter(chatScreenAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        image = (CircleImageView) findViewById(R.id.image);
        name  = (TextView) findViewById(R.id.name);

        get_member_details();


    }


    public void get_chats(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Settings.SERVER_URL+"chats.php";
        Ion.with(this)
                .load(url)
                .setBodyParameter("member_id",member_id)
                .setBodyParameter("receiver_id",receiver_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                            Log.e("response", String.valueOf(result.size()));
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            for (int i = 0; i < result.size(); i++) {
                                Chats chats = new Chats(result.get(i).getAsJsonObject(),ChatScreen.this);
                                chatsfrom_api.add(chats);

                            }
                            chatScreenAdapter.notifyDataSetChanged();
                    }
                });
    }



    ArrayList<Posts> postsfrom_api;

    public void get_member_details(){
        String url = Settings.SERVER_URL+"member-details.php";
        Ion.with(ChatScreen.this)
                .load(url)
                .setBodyParameter("member_id",receiver_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            name.setText(jsonObject.get("name").getAsString());
                            chatScreenAdapter.notifyDataSetChanged();
                            Ion.with(ChatScreen.this)
                                    .load(jsonObject.get("image").getAsString())
                                    .withBitmap()
                                    .placeholder(R.drawable.ic_profile)
                                    .intoImageView(image);

                        }catch (Exception ex){
                            ex.printStackTrace();
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
    protected void onActivityResult(int requestCode,int resultCode,Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    select_files.setImageURI(selectedImage);
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);

                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    select_files.setImageURI(selectedImage);
                    File new_file = new File(selectedImage.getPath());
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);

                }
                break;
        }
    }

    private void upload_image(){
        String message =  text_message.getText().toString();
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
                .load(Settings.SERVER_URL+"chat.php")
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
                .setMultipartParameter("member_id",member_id)
                .setMultipartParameter("receiver_id",receiver_id)
                .setMultipartParameter("msg_type","file")
                .setMultipartFile("file", "image/png", new File(selected_image_path))
                .setMultipartParameter("description",text_message.getText().toString())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        if(e!=null)
                            e.printStackTrace();
                        else {
//                            if(result.isJsonNull())
//                                Log.e("json_null", "null");
//                            else {
//                                Log.e("image_upload_res", result.toString());
//                                // progressBar.setVisibility(View.GONE);
//                                chat_success();
//                            }
                            Log.e("image_response",result.toString());
                            if (result.get("status").getAsString().equals("Success")){
                                chat_success();
                            }else {
                                Toast.makeText(ChatScreen.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
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

    public  void chat_success(){
        Toast.makeText(ChatScreen.this, "message added successfully", Toast.LENGTH_SHORT).show();
        text_message.setText("");
        get_chats();
    }




}