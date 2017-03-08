package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

/**
 * Created by T on 17-12-2016.
 */

public class PostDetailPageFragment extends Fragment{
    ImageView back_btn;
    ImageView user_image;
    TextView  item_title;
    ImageView item_image;
    TextView  no_of_likes;
    TextView  no_of_views;
    TextView  description;
    Posts posts;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.post_detail_page,container,false);
        back_btn    = (ImageView) view.findViewById(R.id.back_btn);
        user_image  = (ImageView) view.findViewById(R.id.user_image);
        item_title  = (TextView)  view.findViewById(R.id.item_title);
        item_image  = (ImageView) view.findViewById(R.id.item_image);
        no_of_likes = (TextView)  view.findViewById(R.id.no_of_likes);
        no_of_views = (TextView)  view.findViewById(R.id.no_of_views);
        description = (TextView)  view.findViewById(R.id.description);

        posts = (Posts) getArguments().getSerializable("post");
        item_title.setText(posts.title);
        Picasso.with(getActivity()).load(posts.user_image).into(user_image);
        Ion.with(getActivity())
                .load(posts.image)
                .withBitmap()
                .placeholder(R.drawable.placeholder)
                .intoImageView(item_image);
        description.setText(posts.description);
        no_of_likes.setText(posts.total_likes);
        no_of_views.setText(posts.total_views);

        Ion.with(getActivity())
                .load(Settings.SERVER_URL + "view.php")
                .setBodyParameter("post_id",posts.id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("status").getAsString().equals("Success"))
                            Toast.makeText(getActivity(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();

                    }
                });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeProfile homeProfile = new HomeProfile();
                getFragmentManager().beginTransaction().replace(R.id.fragment,homeProfile).commit();
            }
        });

        return view;
    }

}
