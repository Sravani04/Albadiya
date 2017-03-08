package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 18-11-2016.
 */

public class LawyerSettings extends Activity {
    ImageView item_image;
    ImageView loading;
    ImageView profile;
    ImageView books;
    ImageView chat;
    RelativeLayout changepw;
    // LinearLayout pop_up;
    //TextView close_btn;
    TextView logout;

    TextView user_name;
    TextView user_email;
    TextView user_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_settings);
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);
        user_phone = (TextView) findViewById(R.id.user_phone);
        get_member_details();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        chat = (ImageView) findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chats = new Intent(LawyerSettings.this,LawyerChats.class);
                startActivity(chats);
            }
        });

//        passclick  = (RelativeLayout) findViewById(R.id.changepw);
//        passclick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pop_up.setVisibility(View.VISIBLE);
//            }
//        });

//        pop_up = (LinearLayout) findViewById(R.id.pop_up_view);
//        close_btn = (TextView) findViewById(R.id.close_btn);
//        close_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pop_up.setVisibility(View.GONE);
//            }
//        });

        changepw = (RelativeLayout) findViewById(R.id.changepw);
        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepw =  new Intent(LawyerSettings.this,ChangePassword.class);
                startActivity(changepw);
            }
        });


        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public  static  final String mem_name="mem_name";
            public  static  final String mem_password="mem_password";
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("member_id",-1);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name" , mem_name);
                editor.putString("password" , mem_password);
                Toast.makeText(LawyerSettings.this,"Your logout",Toast.LENGTH_SHORT).show();
                Intent logout = new Intent(LawyerSettings.this,LawyerLogin.class);
                startActivity(logout);
            }
        });

        loading = (ImageView) findViewById(R.id.loading);
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent load = new Intent(LawyerSettings.this,LawyerTimeline.class);
                startActivity(load);
            }
        });

        profile = (ImageView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prof = new Intent(LawyerSettings.this,LawyerStatus.class);
                startActivity(prof);
            }
        });

        books = (ImageView) findViewById(R.id.books);
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book = new Intent(LawyerSettings.this,LawyerBooks.class);
                startActivity(book);
            }
        });

        item_image = (ImageView) findViewById(R.id.add_image);
        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(LawyerSettings.this,lawyerEditProfile.class);
                startActivity(image);
            }
        });


    }


    public void get_member_details(){
        final ProgressDialog progressDialog = new ProgressDialog(LawyerSettings.this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "http://clients.yellowsoft.in/lawyers/api/members.php";
        Ion.with(LawyerSettings.this)
                .load(url)
                .setBodyParameter("member_id",Settings.GetUserId(LawyerSettings.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        user_name.setText(jsonObject.get("username").getAsString());
                        user_email.setText(jsonObject.get("email").getAsString());
                        user_phone.setText(jsonObject.get("phone").getAsString());
                        Ion.with(LawyerSettings.this)
                                .load(jsonObject.get("image").getAsString())
                                .withBitmap()
                                .placeholder(R.drawable.upload_icon)
                                .intoImageView(item_image);
                    }
                });
    }




}
