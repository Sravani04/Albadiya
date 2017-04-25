package im.ene.toro.sample.feature.facebook.timeline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.exoplayer2.ExoVideoView;
import im.ene.toro.exoplayer2.ExoVideoViewHolder;
import im.ene.toro.sample.R;
import im.ene.toro.sample.feature.facebook.AlbadiyaTimelineFragment;
import im.ene.toro.sample.feature.facebook.PostsTimlineFragment;
import im.ene.toro.sample.util.Util;

/**
 * Created by T on 17-03-2017.
 */

public class PostsVideoViewHolder extends ExoVideoViewHolder {

    static final int LAYOUT_RES = R.layout.post_video;

    private TimelineItem.VideoItem videoItem;
    private ImageView mThumbnail;
    private TextView mInfo;
    private CircleImageView mThumbnailUser;
    private TextView mInfoUser;
    private TextView description;
    private TextView time;
    private ImageView user_like;
    private ImageView download;
    private ImageView share_it;
    private TextView no_of_likes;
    private TextView no_of_views;
    private TextView seconds;
    private PostsTimlineFragment timeline;
    private ImageView delete_btn;
    String post_id;
    private ImageView sound_btn;
    boolean isvolume = true;


    public PostsVideoViewHolder(View itemView) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        description = (TextView) itemView.findViewById(R.id.description);
        time = (TextView) itemView.findViewById(R.id.time);
        user_like = (ImageView) itemView.findViewById(R.id.user_like);
        download = (ImageView) itemView.findViewById(R.id.download);
        // share_it = (ImageView) itemView.findViewById(R.id.share_it);
        no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
        no_of_views = (TextView) itemView.findViewById(R.id.no_of_views);
        seconds = (TextView) itemView.findViewById(R.id.seconds);
        delete_btn = (ImageView) itemView.findViewById(R.id.delete_btn);
        sound_btn = (ImageView) itemView.findViewById(R.id.sound_btn);

    }
    public PostsVideoViewHolder(View itemView, AlbadiyaTimelineFragment fragment,PostsTimlineFragment timeline) {
        super(itemView);
        mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        mInfo = (TextView) itemView.findViewById(R.id.info);
        mThumbnailUser = (CircleImageView) itemView.findViewById(R.id.tumbnailuser);
        mInfoUser = (TextView) itemView.findViewById(R.id.infouser);
        description = (TextView) itemView.findViewById(R.id.description);
        time = (TextView) itemView.findViewById(R.id.time);
        user_like = (ImageView) itemView.findViewById(R.id.user_like);
        download = (ImageView) itemView.findViewById(R.id.download);
        // share_it = (ImageView) itemView.findViewById(R.id.share_it);
        no_of_likes = (TextView) itemView.findViewById(R.id.no_of_likes);
        no_of_views = (TextView) itemView.findViewById(R.id.no_of_views);
        seconds = (TextView) itemView.findViewById(R.id.seconds);
        delete_btn = (ImageView) itemView.findViewById(R.id.delete_btn);
        this.timeline = timeline;
        sound_btn = (ImageView) itemView.findViewById(R.id.sound_btn);

    }


    @Override protected ExoVideoView findVideoView(View itemView) {
        return (ExoVideoView) itemView.findViewById(R.id.video);
    }


    @Override public void bind(RecyclerView.Adapter adapter, @Nullable final Object object) {
        if (!(object instanceof TimelineItem)
                || !(((TimelineItem) object).getEmbedItem() instanceof TimelineItem.VideoItem)) {
            throw new IllegalArgumentException("Only VideoItem is accepted");
        }

        this.videoItem = (TimelineItem.VideoItem) ((TimelineItem) object).getEmbedItem();
        this.videoView.setMedia(Uri.parse(videoItem.getVideoUrl()));
        this.mInfoUser.setText((((TimelineItem) object).getAuthor().getUserName()));
        this.description.setText(((TimelineItem) object).getAuthor().getUserDescription());
        Picasso.with(itemView.getContext()).load(((TimelineItem) object).getAuthor().getUserUrl()).into(mThumbnailUser);
        this.time.setText(((TimelineItem) object).getAuthor().getUserTime());
        this.no_of_likes.setText(((TimelineItem) object).getAuthor().getUserLikes());
        this.no_of_views.setText(((TimelineItem) object).getAuthor().getUserViews());

//    share_it.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Intent  i = new Intent(Intent.ACTION_SEND);
//        i.setType("text/plain");
//        i.putExtra(Intent.EXTRA_SUBJECT,((TimelineItem) object).getAuthor().getUserName());
//        i.putExtra(Intent.EXTRA_TEXT,((TimelineItem) object).getAuthor().getUserUrl());
//        i.putExtra(Intent.EXTRA_TEXT,((TimelineItem) object).getAuthor().getUserDescription());
//        itemView.getContext().startActivity(Intent.createChooser(i,"Share via"));
//      }
//    });


        videoView.setVolume(0f);
        sound_btn.setImageResource(R.drawable.mute_volume);
        Log.e("sound","sound muted");
        isvolume = true;

        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvolume) {
                    videoView.setVolume(0.9f);
                    sound_btn.setImageResource(R.drawable.voulme);
                    isvolume = false;
                    Log.e("sound", "sound on");
                }else {
                    videoView.setVolume(0f);
                    sound_btn.setImageResource(R.drawable.mute_volume);
                    Log.e("sound","sound muted");
                    isvolume = true;
                }
            }
        });

        Ion.with(itemView.getContext())
                .load("http://naqshapp.com/albadiya/api/view.php")
                .setBodyParameter("post_id",((TimelineItem) object).getAuthor().getUserId())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            Log.e("views_response",result.get("status").getAsString());
                            if (result.get("status").getAsString().equals("Success")){
                                //Toast.makeText(itemView.getContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                            }else {
                                //Toast.makeText(itemView.getContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception ex){
                            e.printStackTrace();
                        }

                    }
                });

        if (((TimelineItem) object).getAuthor().getPersonId().equals(Settings.GetUserId(itemView.getContext()))){
            delete_btn.setVisibility(View.VISIBLE);
        }else {
            delete_btn.setVisibility(View.GONE);
        }

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_popup();
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressBar progressBar = new ProgressBar(itemView.getContext());
                final ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());
                progressDialog.setMessage("Please wait video is downloading..");
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                Ion.with(itemView.getContext())
                        .load(((TimelineItem.VideoItem) ((TimelineItem) object).getEmbedItem()).getVideoUrl())
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
                        .write(new File("/sdcard/Albadiya" + ((TimelineItem) object).getAuthor().getUserId() + ".mp4"))
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File result) {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Toast.makeText(itemView.getContext(), "video saved successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        post_id = ((TimelineItem) object).getAuthor().getUserId();

        if (timeline.get_like_id(((TimelineItem)object).getAuthor().getUserId())) {
            user_like.setBackgroundResource(R.drawable.with);
        }
        else {
            user_like.setBackgroundResource(R.drawable.without);
        }


        user_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(itemView.getContext())
                        .load(Settings.SERVER_URL+"like.php")
                        .setBodyParameter("member_id",Settings.GetUserId(itemView.getContext()))
                        .setBodyParameter("post_id",((TimelineItem) object).getAuthor().getUserId())
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                timeline.set_like_id(((TimelineItem)object).getAuthor().getUserId());
                                timeline.set_like_count(((TimelineItem)object).getAuthor().getUserId());

                                if (timeline.get_like_id(((TimelineItem)object).getAuthor().getUserId())) {
                                    user_like.setBackgroundResource(R.drawable.with);
                                    no_of_likes.setText( timeline.get_like_count(((TimelineItem)object).getAuthor().getUserId()));

                                }
                                else {
                                    user_like.setBackgroundResource(R.drawable.without);
                                    no_of_likes.setText(((TimelineItem) object).getAuthor().getUserLikes());
                                    no_of_likes.setText( timeline.get_like_count(((TimelineItem)object).getAuthor().getUserId()));

                                }

                            }
                        });
            }
        });

        mThumbnailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeline.go_to_user_profile(((TimelineItem) object).getAuthor().getPersonId());
            }
        });

        mInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeline.go_to_user_profile(((TimelineItem) object).getAuthor().getPersonId());
            }
        });


    }

    @Override public void setOnItemClickListener(View.OnClickListener listener) {
        super.setOnItemClickListener(listener);
        mInfo.setOnClickListener(listener);
        this.videoView.setOnClickListener(listener);
    }


    @Nullable @Override public String getMediaId() {
        return Util.genVideoId(this.videoItem.getVideoUrl(), getAdapterPosition());
    }

    @Override public void onVideoPreparing() {
        super.onVideoPreparing();
        mInfo.setText("Preparing");
    }

  @Override public void onVideoPrepared() {
    super.onVideoPrepared();
    //mInfo.setText("Prepared");
  }

    @Override public void onViewHolderBound() {
        super.onViewHolderBound();
        Picasso.with(itemView.getContext())
                .load(R.drawable.video_play_button)
                .resize(400, 300 )
                .into(mThumbnail);
        //mInfo.setText("Bound");
    }

    @Override public void onPlaybackStarted() {
        mThumbnail.animate().alpha(0.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                PostsVideoViewHolder.super.onPlaybackStarted();
            }
        }).start();
        //mInfo.setText("Started");
    }

    @Override public void onPlaybackPaused() {
        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                PostsVideoViewHolder.super.onPlaybackPaused();
            }
        }).start();
       // mInfo.setText("Paused");
    }

    @Override public void onPlaybackCompleted() {
        mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                PostsVideoViewHolder.super.onPlaybackCompleted();
            }
        }).start();
       // mInfo.setText("Completed");
    }

    @Override public boolean onPlaybackError(Exception error) {
        mThumbnail.animate().alpha(1.f).setDuration(0).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                // Immediately finish the animation.
            }
        }).start();
        mInfo.setText("Error: videoId = " + getMediaId());
        return super.onPlaybackError(error);
    }

    public void delete_popup() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(itemView.getContext());
        builder2.setMessage("Delete post?");
        builder2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ion.with(itemView.getContext())
                        .load("http://naqshapp.com/albadiya/api/post-delete.php")
                        .setBodyParameter("member_id", Settings.GetUserId(itemView.getContext()))
                        .setBodyParameter("post_id", post_id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    if (result.get("status").getAsString().equals("Success")) {
                                        Toast.makeText(itemView.getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                        timeline.delete_post(post_id);
                                    } else {
                                        Toast.makeText(itemView.getContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//        onViewHolderBound();
                Toast.makeText(itemView.getContext(), "U Clicked Cancel ", Toast.LENGTH_LONG).show();
            }

        });

        builder2.show();
    }
}
