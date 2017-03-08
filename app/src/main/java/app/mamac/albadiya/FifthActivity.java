package app.mamac.albadiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by T on 09-11-2016.
 */

public class FifthActivity extends AppCompatActivity {
    TextView fb_login;
    EditText email_addr,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        fb_login = (TextView) findViewById(R.id.fb_login);
        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_addr.getText().toString().equals("")){
                    email_addr.setError("Please enter email address");
                    Toast.makeText(FifthActivity.this,"Please enter email address",Toast.LENGTH_SHORT).show();

                }else if(pw.getText().toString().equals("")){
                    pw.setError("Please enter password");
                    Toast.makeText(FifthActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();

                }else {
                    //Toast.makeText(FifthActivity.this,"Welcome to facebook",Toast.LENGTH_SHORT).show();
                    Intent sixth_activity = new Intent(FifthActivity.this, SixthActivity.class);
                    startActivity(sixth_activity);
                }
            }
        });

        email_addr = (EditText) findViewById(R.id.email_addr);
        pw         = (EditText) findViewById(R.id.pw);

    }
}
