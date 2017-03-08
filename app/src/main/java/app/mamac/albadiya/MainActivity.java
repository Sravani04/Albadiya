package app.mamac.albadiya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity  {
    TextView login_btn;
    TextView facebook_btn;
    TextView google_btn;
    TextView linkedin_btn;
    TextView forgot_password;
    TextView register_btn;
    TextView bluetooth_btn;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        login_btn = (TextView) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("")){

                    username.setError("Please enter username");
                    Toast.makeText(MainActivity.this,"please enter username",Toast.LENGTH_SHORT).show();

                }else if(password.getText().toString().equals("")){
                    password.setError("Please enter password");
                    Toast.makeText(MainActivity.this,"please enter password",Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(MainActivity.this,"Login Succesfull!!",Toast.LENGTH_SHORT).show();
                    Intent third_activity = new Intent(MainActivity.this,ThirdActivity.class);
                    startActivity(third_activity);
                }

            }
        });

        facebook_btn = (TextView) findViewById(R.id.facebook_btn);
        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Clicked on Facebook", Toast.LENGTH_SHORT).show();
                Intent fifth_activity = new Intent(MainActivity.this,FifthActivity.class);
                startActivity(fifth_activity);
            }
        });

        google_btn = (TextView)findViewById(R.id.google_btn);
        google_btn.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Clicked on google",Toast.LENGTH_SHORT).show();
            }
        });

        linkedin_btn = (TextView) findViewById(R.id.linkedin_btn);
        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Clicked on linkedin",Toast.LENGTH_SHORT).show();
                Intent seventh = new Intent(MainActivity.this,SeventhActivity.class);
                startActivity(seventh);
            }
        });

        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"please check your email",Toast.LENGTH_SHORT).show();
                Intent fourth_page = new Intent(MainActivity.this,FouthActivity.class);
                startActivity(fourth_page);
            }
        });

        register_btn = (TextView) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Welcome to Signup",Toast.LENGTH_SHORT).show();
                Intent second_activity = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(second_activity);
            }
        });

        bluetooth_btn = (TextView) findViewById(R.id.bluetooth_btn);
        bluetooth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"clicked on bluetooth button",Toast.LENGTH_SHORT).show();
                Intent bluetooth = new Intent(MainActivity.this,BluetoothActivity.class);
                startActivity(bluetooth);
            }
        });


        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        username.setText("");
        password.setText("");

    }

}
