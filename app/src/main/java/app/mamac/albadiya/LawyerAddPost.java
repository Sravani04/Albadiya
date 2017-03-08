package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 02-12-2016.
 */

public class LawyerAddPost extends Activity {
    EditText add_title;
    EditText add_message;
    TextView submit_btn;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_addpost);
        add_title = (EditText) findViewById(R.id.add_title);
        add_message = (EditText) findViewById(R.id.add_message);


        submit_btn = (TextView) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = add_title.getText().toString();
                String message = add_message.getText().toString();
                if (title.equals("")){
                    Toast.makeText(LawyerAddPost.this,"please enter title..",Toast.LENGTH_SHORT).show();
                }else if (message.equals("")){
                    Toast.makeText(LawyerAddPost.this,"please enter message..",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(LawyerAddPost.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/add-post.php")
                            .setBodyParameter("lawyer_id",Settings.GetUserId(LawyerAddPost.this))
                            .setBodyParameter("title",title)
                            .setBodyParameter("message",message)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                            Toast.makeText(LawyerAddPost.this, result.get("post_id").getAsString(), Toast.LENGTH_SHORT).show();
                                            Intent posts = new Intent(LawyerAddPost.this, LawyerPosts.class);
                                            startActivity(posts);

                                    }else{
                                        Toast.makeText(LawyerAddPost.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }
            }
        });


    }

   

}
