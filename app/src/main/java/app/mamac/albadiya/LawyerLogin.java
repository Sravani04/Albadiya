package app.mamac.albadiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class LawyerLogin extends Activity {
    TextView login_btn;
    TextView lawyer_btn;
    TextView user_btn;
    TextView skip;
    ImageView web_image;

    RadioButton lawyr,usr;

    TextView fpwd;

    EditText email;
    EditText password;

    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lawyers_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        web_image = (ImageView) findViewById(R.id.web_image);
        Ion.with(this)
                .load("https://www.yellowsoft.in/wp-content/uploads/2014/05/woman-ceo.png")
                .withBitmap()
                .placeholder(R.drawable.snapdeal)
                .error(R.drawable.amazon)
                .intoImageView(web_image);
        login_btn = (TextView) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                email_string = email_string.trim();


                if(email_string.equals("")){
                    Toast.makeText(LawyerLogin.this,"Please enter email",Toast.LENGTH_SHORT).show();

                }else if(password_string.equals("")){
                    Toast.makeText(LawyerLogin.this,"please enter password",Toast.LENGTH_SHORT).show();

                }   else{
                    final ProgressDialog progressDialog = new ProgressDialog(LawyerLogin.this);
                    progressDialog.setMessage("please wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(LawyerLogin.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/login.php")
                            .setBodyParameter("email", email_string)
                            .setBodyParameter("password", password_string)
                            .setBodyParameter("type","1")

                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if(progressDialog!=null)
                                        progressDialog.dismiss();
                                    // do stuff with the result or error
                                    //{"status":"Failure","message":"Please Enter Your Type"}
                                    if(result.get("status").getAsString().equals("Success")) {
                                        Settings.SetUserId(LawyerLogin.this,result.get("member_id").getAsString(),result.get("name").getAsString());
                                        Toast.makeText(LawyerLogin.this, result.get("name").getAsString(), Toast.LENGTH_SHORT).show();
                                        Intent skip = new  Intent(LawyerLogin.this,LawyerTimeline.class);
                                        startActivity(skip);

                                    }
                                    else {
                                        Toast.makeText(LawyerLogin.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }


            }
        });

        lawyer_btn = (TextView) findViewById(R.id.lawyer_btn);
        lawyer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Clicked on Lawyer Sign up", Toast.LENGTH_SHORT).show();
                Intent lawyer = new Intent(LawyerLogin.this,SignUpAsLawyer.class);
                startActivity(lawyer);
            }
        });


        user_btn = (TextView) findViewById(R.id.user_btn);
        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LawyerLogin.this,"Clicked on Users Sign up",Toast.LENGTH_SHORT).show();
                 Intent users_activity = new Intent(LawyerLogin.this,LawyerSignup.class);
                 startActivity(users_activity);
            }
        });

        skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new  Intent(LawyerLogin.this,LawyerTimeline.class);
                startActivity(skip);
            }
        });

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        lawyr = (RadioButton) findViewById(R.id.lawyr);
        usr = (RadioButton) findViewById(R.id.usr);

        fpwd = (TextView) findViewById(R.id.fpwd);
        fpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fpwd = new Intent(LawyerLogin.this,ForgetPassword.class);
                startActivity(fpwd);
            }
        });



    }

}
