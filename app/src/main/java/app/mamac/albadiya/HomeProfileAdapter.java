package app.mamac.albadiya;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import junit.framework.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.bitmap;
import static android.R.attr.color;
import static android.R.attr.description;
import static android.R.attr.id;
import static android.R.attr.insetTop;
import static android.R.attr.lines;
import static android.R.attr.pointerIcon;
import static android.R.attr.popupAnimationStyle;
import static android.R.attr.resource;
import static android.R.attr.targetActivity;
import static android.R.attr.theme;
import static app.mamac.albadiya.R.id.followers;
import static app.mamac.albadiya.R.id.home;
import static app.mamac.albadiya.R.id.ifRoom;
import static app.mamac.albadiya.R.id.image;
import static app.mamac.albadiya.R.id.imageView;
import static app.mamac.albadiya.R.id.images_post;
import static app.mamac.albadiya.R.id.item_image;
import static app.mamac.albadiya.R.id.posts;
import static app.mamac.albadiya.R.id.user_image;
import static app.mamac.albadiya.R.id.user_name;

/**
 * Created by T on 08-12-2016.
 */

public class HomeProfileAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Posts> posts;
    HashMap<Integer,Boolean> flags;
    HashMap<Integer,Integer> likes;
    HomeProfile homeProfile;

    private GestureDetector gestureDetector;
    private GestureDetector detector;

//    ArrayList<String> mnames;
//    ArrayList<Integer> mimages;


   // public HomeProfileAdapter(Context context,ArrayList<Integer> profile_image,ArrayList<String> names,ArrayList<Integer> images,ArrayList<Integer> bg_images){
     public HomeProfileAdapter(Context context,ArrayList<Posts> posts,HomeProfile homeProfile){
        this.context = context;
         this.posts = posts;
         this.homeProfile = homeProfile;
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
        final  View item_view = inflater.inflate(R.layout.home_fragment,null);
        TextView  item_title  = (TextView) item_view.findViewById(R.id.item_title);
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

        final TextView time = (TextView) item_view.findViewById(R.id.time);
        time.setText(posts.get(position).time);


        final ImageView download = (ImageView) item_view.findViewById(R.id.download);
        final ProgressBar progressBar = new ProgressBar(context);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posts.get(position).video.equals("")) {
                    //Picasso.with(context).load(posts.get(position).image).into(target);
                    progressDialog.setMessage("Please wait image is downloading..");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Ion.with(context)

                            .load(posts.get(position).image)
                            .progressBar(progressBar)
                            .progressDialog(progressDialog)
                            // .uploadProgressBar(progressBar)
                            .progress(new ProgressCallback() {
                                @Override
                                public void onProgress(long downloaded, long total) {
                                    progressDialog.setMax((int) total);
                                    progressDialog.setProgress((int) downloaded);
                                    System.out.println("" + (int) downloaded + " / " + (int) total);
                                }
                            })
                            .write(new File("/sdcard/Albadiya" + posts.get(position).id + ".jpg"))
                            .setCallback(new FutureCallback<File>() {
                                @Override
                                public void onCompleted(Exception e, File file) {
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
                                    Toast.makeText(context, "image saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {


                    progressDialog.setMessage("Please wait video is downloading..");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Ion.with(context)
                            .load(posts.get(position).video)
                            .progressBar(progressBar)
                            .progressDialog(progressDialog)
                            // .uploadProgressBar(progressBar)
                            .progress(new ProgressCallback() {
                                @Override
                                public void onProgress(long downloaded, long total) {
                                    progressDialog.setMax((int) total);
                                    progressDialog.setProgress((int) downloaded);
                                    System.out.println("" + (int) downloaded + " / " + (int) total);
                                }
                            })
                            .write(new File("/sdcard/Albadiya" + posts.get(position).id + ".mp4"))
                            .setCallback(new FutureCallback<File>() {
                                @Override
                                public void onCompleted(Exception e, File result) {
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
                                    Toast.makeText(context, "video saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                }


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

        heartImageView = (ImageView) item_view.findViewById(R.id.heart);
        circleBackground = item_view.findViewById(R.id.circleBg);

        item_image.setOnTouchListener(new View.OnTouchListener() {
           private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
               @Override
               public boolean onDoubleTap(MotionEvent e) {
                   Log.d("TEST", "onDoubleTap");
                   user_like.performClick();
                   heart();
                   return super.onDoubleTap(e);
               }
               // implement here other callback methods like onFling, onScroll as necessary
           });

           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
               gestureDetector.onTouchEvent(event);
               return true;
           }
       });

//       item_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 user_like.performClick();
//            }
//        });

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
            homeProfile.go_to_user_profile(posts.get(position).user_id);
           }
        });

        item_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            homeProfile.go_to_user_profile(posts.get(position).user_id);
          }
        });

        return item_view;
    }
    public class GestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e){
            Log.e("heart","onDown");
            return true;
        }
    }

    //Like animation

    private ImageView heartImageView;
    private View circleBackground;

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    private void heart() {

        circleBackground.setVisibility(View.VISIBLE);
        heartImageView.setVisibility(View.VISIBLE);

        circleBackground.setScaleY(0.1f);
        circleBackground.setScaleX(0.1f);
        circleBackground.setAlpha(1f);
        heartImageView.setScaleY(0.1f);
        heartImageView.setScaleX(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(circleBackground, "scaleY", 0.1f, 1f);
        bgScaleYAnim.setDuration(200);
        bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(circleBackground, "scaleX", 0.1f, 1f);
        bgScaleXAnim.setDuration(200);
        bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(circleBackground, "alpha", 1f, 0f);
        bgAlphaAnim.setDuration(200);
        bgAlphaAnim.setStartDelay(150);
        bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(heartImageView, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(heartImageView, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(heartImageView, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(heartImageView, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);


        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset();
            }
        });

        animatorSet.start();
    }

    public void reset() {
        circleBackground.setVisibility(View.GONE);
        heartImageView.setVisibility(View.GONE);
    }

    //Like animation ends here


    //    private Target target = new Target() {
//        @Override
//        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//
//            File file = new File(Environment.getExternalStorageDirectory().getPath()  + "/" + item_image  + ".jpg");
//            Log.e("image_path",file.getAbsolutePath());
//            try {
//                file.createNewFile();
//                FileOutputStream ostream = new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,ostream);
//                ostream.close();
//                Toast.makeText(context,"image saved successfully",Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {}
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {}
//
//    };

}


