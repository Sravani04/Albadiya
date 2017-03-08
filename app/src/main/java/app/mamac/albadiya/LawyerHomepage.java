package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



/**
 * Created by T on 16-11-2016.
 */

public class LawyerHomepage extends Activity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_homepage);
        ImageView image = (ImageView) findViewById(R.id.add_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LawyerHomepage.this,LawyerLogin.class);
                startActivity(intent1);
            }
        });
    }
}
