package app.mamac.albadiya;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by T on 20-01-2017.
 */

public class PostItemsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Posts> posts;
    HashMap<Integer,Boolean> flags;
    HashMap<Integer,Integer> likes;
    PostItems postItems;

    private GestureDetector gestureDetector;
    private GestureDetector detector;
//    ArrayList<String> mnames;
//    ArrayList<Integer> mimages;


    // public HomeProfileAdapter(Context context,ArrayList<Integer> profile_image,ArrayList<String> names,ArrayList<Integer> images,ArrayList<Integer> bg_images){
    public PostItemsAdapter(Context context,ArrayList<Posts> posts,PostItems postItems){
        this.context = context;
        this.posts = posts;
        this.postItems = postItems;
        inflater = LayoutInflater.from(context);
        flags = new HashMap<>();
        likes = new HashMap<>();
        Log.e("flagvalue",String.valueOf(this.posts.size()));

        for(int i =0;i<this.posts.size();i++){
            likes.put(i,Integer.parseInt(posts.get(i).total_likes));
            if(posts.get(i).member_like.equals("0")){
                flags.put(i,Boolean.FALSE);
            } else {
                flags.put(i, Boolean.TRUE);
            }
            Log.e("flagvalue",String.valueOf(flags.get(i)));
        }


//        mnames = names;
//        mimages = images;
    }
    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final  View item_view = inflater.inflate(R.layout.post_items,null);
        TextView item_title  = (TextView) item_view.findViewById(R.id.item_title);
        TextView  description = (TextView) item_view.findViewById(R.id.description);
        final TextView  no_of_likes = (TextView) item_view.findViewById(R.id.no_of_likes);
        TextView  no_of_views = (TextView) item_view.findViewById(R.id.no_of_views);

        final ImageView item_image   = (ImageView) item_view.findViewById(R.id.item_image);
        final ImageView user_image   = (ImageView) item_view.findViewById(R.id.user_image);
        final ImageView play_btn     = (ImageView) item_view.findViewById(R.id.play_btn);
        if(posts.get(position).video.equals("")){
            play_btn.setVisibility(View.GONE);
        }else{
            play_btn.setVisibility(View.VISIBLE);
        }
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("video", posts.get(position).video);
                context.startActivity(intent);

            }
        });


//        item_image.setImageResource(mprofile_image.get(position));

        item_title.setText(posts.get(position).title);
        description.setText(posts.get(position).description);
        no_of_likes.setText(posts.get(position).total_likes);
        no_of_views.setText(posts.get(position).total_views);



        Ion.with(context)
                .load(posts.get(position).image)
                .withBitmap()
                .placeholder(R.drawable.placeholder)
                .intoImageView(item_image);

        Ion.with(context)
                .load(posts.get(position).user_image)
                .withBitmap()
                .placeholder(R.drawable.ic_profile)
                .intoImageView(user_image);

        Picasso.with(context).load(posts.get(position).user_image).into(user_image);

        no_of_views.setText(posts.get(position).total_views);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            item_title.setTransitionName("trans_text" + position);
            item_image.setTransitionName("trans_image" + position);
        }

        final ImageView user_like = (ImageView) item_view.findViewById(R.id.user_like);
        if (flags.get(position) ) {
            user_like.setBackgroundResource(R.drawable.heart);
        }
        else {
            user_like.setBackgroundResource(R.drawable.ic_likes_vi);
        }
        user_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flags.put(position,!flags.get(position));

                if (flags.get(position) ) {
                    user_like.setBackgroundResource(R.drawable.heart);
                    likes.put(position,likes.get(position)+1);
                    no_of_likes.setText(String.valueOf(likes.get(position)));
                }
                else {
                    user_like.setBackgroundResource(R.drawable.ic_likes_vi);
                    likes.put(position,likes.get(position)-1);
                    no_of_likes.setText(String.valueOf(likes.get(position)));
                }
                Ion.with(context)
                        .load(Settings.SERVER_URL+"like.php")
                        .setBodyParameter("member_id",Settings.GetUserId(context))
                        .setBodyParameter("post_id",posts.get(position).id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                            }
                        });
            }
        });


        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 user_like.performClick();
            }
        });

        ImageView share_it = (ImageView) item_view.findViewById(R.id.share_it);

        share_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,posts.get(position).title);
                i.putExtra(Intent.EXTRA_TEXT, posts.get(position).image);
                i.putExtra(Intent.EXTRA_TEXT, posts.get(position).description);
                context.startActivity(Intent.createChooser(i,"Share via"));
            }
        });

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postItems.go_to_user_profile(posts.get(position).user_id);
            }
        });

        item_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postItems.go_to_user_profile(posts.get(position).user_id);
            }
        });

        return item_view;
    }


}
