package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;



/**
 * Created by T on 21-11-2016.
 */

public class SignUpAsLawyer extends Activity {
    TextView submit;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplawyer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        submit = (TextView) findViewById(R.id.submit) ;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sub = new Intent(SignUpAsLawyer.this,LawyerExample.class);
                startActivity(sub);
            }
        });
    }

}
