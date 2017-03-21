package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 07-12-2016.
 */

public class LoginActivity extends Activity {
    TextView ln_btn;
    EditText email,password;
   @Override
  public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.login_activity);
       ln_btn = (TextView) findViewById(R.id.ln_btn);
       email   = (EditText) findViewById(R.id.email);
       password = (EditText) findViewById(R.id.password);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       ln_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email_string = email.getText().toString();
               String password_string = password.getText().toString();
               if (email_string.equals("")){
                   Toast.makeText(LoginActivity.this,"please enter email",Toast.LENGTH_SHORT).show();
                   email.requestFocus();
               }else if (password_string.equals("")){
                   Toast.makeText(LoginActivity.this,"please enter password",Toast.LENGTH_SHORT).show();
                   password.requestFocus();
               }else{
                   final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                   progressDialog.setMessage("please wait..");
                   progressDialog.setCancelable(false);
                   progressDialog.show();
                   Ion.with(LoginActivity.this)
                           .load(Settings.SERVER_URL+"login.php")
                           .setBodyParameter("email",email_string)
                           .setBodyParameter("password",password_string)
                           .asJsonObject()
                           .setCallback(new FutureCallback<JsonObject>() {
                               @Override
                               public void onCompleted(Exception e, JsonObject result) {
                                   if (progressDialog!=null)
                                       progressDialog.dismiss();
                                   if (result.get("status").getAsString().equals("Success")){
                                       Settings.SetUserId(LoginActivity.this,result.get("member_id").getAsString());
                                       Toast.makeText(LoginActivity.this,result.get("name").getAsString(),Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(LoginActivity.this,InstaFragment.class);
                                       startActivity(intent);
                                   }else {
                                       Toast.makeText(LoginActivity.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });

               }
           }
       });


   }
}
