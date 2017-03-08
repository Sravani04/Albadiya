package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by mac on 12/10/16.
 */

public class LanguageActivity extends Activity {
    ImageView eng_btn,arb_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        eng_btn = (ImageView) findViewById(R.id.eng_btn);
        arb_btn = (ImageView) findViewById(R.id.arb_btn);


        eng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LanguageActivity.this,InstaFragment.class);
                startActivity(intent);
                finish();

            }
        });
        arb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LanguageActivity.this,InstaFragment.class);
                startActivity(intent);
                finish();

            }
        });


    }




}
