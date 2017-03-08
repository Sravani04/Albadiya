package app.mamac.albadiya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import org.w3c.dom.Text;

/**
 * Created by T on 08-11-2016.
 */

public class ThirdActivity extends AppCompatActivity {
    String name;
    Integer image;
    String description;
    String foot;
    Integer logos;
    String contactus;

    TextView name_txt;
    ImageView image_view;
    TextView desc;
    TextView footer;
    ImageView logo;
    ImageView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        name_txt = (TextView) findViewById(R.id.name_txt);
        if(getIntent()!=null){
            name = getIntent().getStringExtra("abc");
            name_txt.setText(name);
        }

        image_view = (ImageView) findViewById(R.id.image_view);
        desc  = (TextView) findViewById(R.id.desc);
        footer = (TextView) findViewById(R.id.footer);
        logo = (ImageView) findViewById(R.id.logo);

        if(getIntent()!=null){
            image = getIntent().getIntExtra("abcd",R.drawable.amazon);
            image_view.setImageResource(image);
            description = getIntent().getStringExtra("abcdefgh");
            desc.setText(description);
            foot = getIntent().getStringExtra("abcdef");
            footer.setText(foot);
            logos = getIntent().getIntExtra("mainlogo",R.drawable.amazon);
            logo.setImageResource(logos);

        }

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
}
