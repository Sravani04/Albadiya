package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by T on 10-12-2016.
 */

public class HomeActivityScreen extends Activity {
    ImageView login_btn;
    ImageView register_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        login_btn = (ImageView) findViewById(R.id.login_btn);
        register_btn = (ImageView) findViewById(R.id.register_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityScreen.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen = new Intent(HomeActivityScreen.this,RegisterActivity.class);
                startActivity(screen);
            }
        });
    }
}
